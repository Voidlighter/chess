package chess;

import java.util.*;
import java.util.function.BiConsumer;

import static chess.ChessPiece.PieceType.*;
import static chess.ChessPiece.PieceType;
import static chess.ChessGame.TeamColor.*;
import static chess.ChessGame.TeamColor;

public class MoveCalculator {

    public static List<ChessMove> run(ChessBoard board, ChessPosition position) {
        if (!board.isPiece(position))
            return new ArrayList<>();

        PieceType type = board.getPiece(position).getPieceType();

        return movesByType(board, position, type);
    }

    private static List<ChessMove> movesByType(ChessBoard board, ChessPosition position, PieceType type) {
        return switch (type) {
            case QUEEN, BISHOP, KNIGHT, ROOK -> lineMoves(board, position, type);
            case KING -> kingMoves(board, position, type);
            case PAWN -> pawnMoves(board, position);
        };
    }

    private static boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    public static boolean canBeAttacked(ChessBoard board, ChessPosition position) {
        if (!board.isPiece(position))
            return false;
        return canBeAttacked(board, position, board.getPiece(position).getTeamColor());
    }

    public static boolean canBeAttacked(ChessBoard board, ChessPosition position, TeamColor color) {
        PieceType[] pieceTypes = PieceType.values();

        for (PieceType testType : pieceTypes) {
            if (testType == QUEEN) {
                continue;
            }
            List<ChessMove> moves = lineMoves(board, position, testType, color);
            for (ChessMove move : moves) {
                ChessPosition attackPos = move.getEndPosition();
                ChessPiece attackPiece = board.getPiece(attackPos);
                if (attackPiece != null && attackPiece.getTeamColor() != color) {
                    if (testType == BISHOP || testType == ROOK) {
                        if (attackPiece.getPieceType() == QUEEN) {
                            return true;
                        }
                    }
                    if (attackPiece.getPieceType() == testType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static List<ChessMove> lineMoves(ChessBoard board, ChessPosition position, int[][] directions, int distance, TeamColor color) {
        List<ChessMove> moves = new ArrayList<>();
        BiConsumer<Integer, Integer> addMove = (row, col) -> {
            moves.add(new ChessMove(position, row + 1, col + 1));
        };
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;

        // We are going to iterate in the direction given until we hit a piece (where true if enemy then stop)
        for (int[] direction : directions) {
            for (int i = 1; i <= distance; i++) { // iterate in that direction (as far as the piece would)
                // we pick where we are going to test as a valid move
                int tryY = row + (i * direction[0]);
                int tryX = col + (i * direction[1]);
                if (isInBounds(tryY, tryX)) {
                    if (board.isPiece(tryY, tryX)) {
                        // if we found an enemy, we still add that as a spot we can move
                        if (board.isEnemy(color, tryY, tryX)) {
                            addMove.accept(tryY, tryX);
                        }
                        break; // if we found a piece we are done with this direction
                    } else {
                        // if there's nothing there, we can move there! And we better keep iterating
                        addMove.accept(tryY, tryX);
                    }
                }
            }
        }
        return moves;
    }
    private static List<ChessMove> lineMoves(ChessBoard board, ChessPosition position, int[][] directions, int distance) {
        return lineMoves(board, position, directions, distance, board.getPiece(position).getTeamColor());
    }

    private static List<ChessMove> lineMoves(ChessBoard board, ChessPosition position, PieceType type, TeamColor color) {
        Moveset moveset = new Moveset(type);
        if (type == PAWN && color == BLACK) {
            moveset.flipDirections();
        }
        return lineMoves(board, position, moveset.getDirections(), moveset.getDistance(), color);
    }

    private static List<ChessMove> lineMoves(ChessBoard board, ChessPosition position, PieceType type) {
        return lineMoves(board, position, type, board.getPiece(position).getTeamColor());
    }

    private static List<ChessMove> pawnMoves(ChessBoard board, ChessPosition position) {
        List<ChessMove> moves = new ArrayList<>();

        BiConsumer<Integer, Integer> addMove = (row, col) -> {
            if (row == 0 || row == 7) {
                PieceType[] pieces = {QUEEN, BISHOP, KNIGHT, ROOK};
                for (PieceType piece : pieces) {
                    ChessPosition newPosition = new ChessPosition(row + 1, col + 1);
                    moves.add(new ChessMove(position, newPosition, piece));
                }
            } else {
                moves.add(new ChessMove(position, row + 1, col + 1));
            }
        };

        TeamColor teamColor = board.getPiece(position).getTeamColor();
        int upOne = teamColor == WHITE ? 1 : -1;
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;

        // Move forward
        int tryY = row + upOne;
        int tryX = col;

        if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX)) {
            addMove.accept(tryY, tryX);

            tryY = row + (2 * upOne);

            // Move two forward
            boolean hasMoved = teamColor == WHITE ? row != 1 : row != 6;
            if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX) && !hasMoved) {
                addMove.accept(tryY, tryX);
            }
        }

        // Attack Diagonal
        tryY = row + upOne;
        for (int i = -1; i <= 1; i += 2) { // This will just try on both sides
            tryX = col + i;
            if (isInBounds(tryY, tryX)) {
                if (board.isEnemy(teamColor, tryY, tryX))
                    addMove.accept(tryY, tryX);
                // En Passant
                if (board.isEnemy(teamColor, row, tryX) && board.getPiece(row, tryX).movedTwo == board.getTurnCount() - 1)
                    addMove.accept(tryY, tryX);
            }
        }

        return moves;
    }

    private static List<ChessMove> kingMoves(ChessBoard board, ChessPosition position, PieceType type) {
        List<ChessMove> moves = lineMoves(board, position, type);
        // Castling
        if (board.getPiece(position).hasMoved || canBeAttacked(board, position)) {
            return moves;
        }
        ChessPosition rookPos;
        ChessPiece rook;
        int[] corners = {1, 8};
        for (int corner : corners) {
            rookPos = new ChessPosition(position.getRow(), corner);
            rook = board.getPiece(rookPos);
            if (rook != null && !rook.hasMoved) {
                // Check if the path is clear
                int direction = corner == 1 ? -1 : 1;
                int[] path = {1, 2};
                for (int i : path) {
                    ChessPosition testPos = new ChessPosition(position.getRow(), position.getColumn() + (i * direction));
                    if (isInBounds(testPos) && board.isPiece(testPos) || canBeAttacked(board, testPos, board.getPiece(position).getTeamColor())) {
                        break;
                    }
                    if (isInBounds(testPos) && i == 2) {
                        moves.add(new ChessMove(position, position.getRow(), position.getColumn() + (i * direction)));
                    }
                }
            }
        }
        return moves;
    }

    private static boolean isInBounds(ChessPosition testPos) {
        return testPos.getRow() >= 1 && testPos.getRow() <= 8 && testPos.getColumn() >= 1 && testPos.getColumn() <= 8;
    }
}
