package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class MoveCalculator {

    public static List<ChessMove> run(ChessBoard board, ChessPosition position) {
        if (!board.isPiece(position))
            return new ArrayList<>();

        List<ChessMove> moves = pieceMoves(board, position);
        ChessPiece.PieceType type = board.getPiece(position).getPieceType();
        int[][] directions;
        int distance = 7;

        return moves;
    }

    protected static List<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessPiece.PieceType type = board.getPiece(position).getPieceType();
        return switch (type) {
            case KING -> lineMoves(board, position,
                    new int[][]{{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}}, 1);
            case QUEEN -> lineMoves(board, position,
                    new int[][]{{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}}, 7);
            case BISHOP -> lineMoves(board, position,
                    new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}}, 7);
            case KNIGHT -> lineMoves(board, position,
                    new int[][]{{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}}, 1);
            case ROOK -> lineMoves(board, position,
                    new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}, 7);
            case PAWN -> pawnMoves(board, position);
        };

    }


    protected static List<ChessMove> lineMoves(ChessBoard board, ChessPosition position, int[][] directions, int distance) {
        List<ChessMove> moves = new ArrayList<>();
        BiConsumer<Integer, Integer> addMove = (row, col) -> {
            moves.add(new ChessMove(position, row + 1, col + 1));
        };
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        boolean isWhite = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE;

        // We are going to iterate in the direction given until we hit a piece (where true if enemy then stop)
        for (int[] direction : directions) {
            for (int i = 1; i <= distance; i++) { // iterate in that direction (as far as the piece would)
                // we pick where we are going to test as a valid move
                int tryY = row + (i * direction[0]);
                int tryX = col + (i * direction[1]);
                if (isInBounds(tryY, tryX)) {
                    if (board.isPiece(tryY, tryX)) {
                        // if we found an enemy, we still add that as a spot we can move
                        if (board.isEnemy(isWhite, tryY, tryX)) {
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

    protected static List<ChessMove> pawnMoves(ChessBoard board, ChessPosition position) {
        List<ChessMove> moves = new ArrayList<>();

        BiConsumer<Integer, Integer> addMove = (row, col) -> {
            if (row == 0 || row == 7) {
                ChessPiece.PieceType[] pieces = {
                        ChessPiece.PieceType.QUEEN,
                        ChessPiece.PieceType.BISHOP,
                        ChessPiece.PieceType.KNIGHT,
                        ChessPiece.PieceType.ROOK,
                };
                for (ChessPiece.PieceType piece : pieces) {
                    ChessPosition newPosition = new ChessPosition(row + 1, col + 1);
                    moves.add(new ChessMove(position, newPosition, piece));
                }
            } else {
                moves.add(new ChessMove(position, row + 1, col + 1));
            }
        };

        boolean isWhite = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE;
        int upOne = isWhite ? 1 : -1;
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;

        // Move forward
        int tryY = row + upOne;
        int tryX = col;

        if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX)) {
            addMove.accept(tryY, tryX);

            tryY = row + (2 * upOne);

            // Move two forward
            boolean hasMoved = isWhite ? row != 1 : row != 6;
            if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX) && !hasMoved) {
                addMove.accept(tryY, tryX);
            }
        }

        // Attack Diagonal
        tryY = row + upOne;
        for (int i = -1; i <= 1; i += 2) { // This will just try on both sides
            tryX = col + i;
            if (isInBounds(tryY, tryX) && board.isEnemy(isWhite, tryY, tryX)) {
                addMove.accept(tryY, tryX);
            }
        }
        return moves;
    }

    protected static boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

}
