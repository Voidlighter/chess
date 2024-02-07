package chess;

import chess.ChessPiece.PieceType;
import chess.ChessGame.TeamColor;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    public ChessPosition whiteKingPos = new ChessPosition(1, 5);
    public ChessPosition blackKingPos = new ChessPosition(8, 5);
    private final ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard other) {
        for (int i = 0; i < 8; i++) {
            this.board[i] = Arrays.copyOf(other.board[i], 8);
        }
        this.whiteKingPos = other.whiteKingPos;
        this.blackKingPos = other.blackKingPos;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
        if (piece.getPieceType() == PieceType.KING) {
            if (piece.getTeamColor() == TeamColor.WHITE) {
                whiteKingPos = position;
            } else {
                blackKingPos = position;
            }
        }
    }

    public void movePiece(ChessPosition from, ChessPosition to, PieceType promotionType) {
        board[to.getRow() - 1][to.getColumn() - 1] = board[from.getRow() - 1][from.getColumn() - 1];
        board[from.getRow() - 1][from.getColumn() - 1] = null;
        if (promotionType != null) {
            board[to.getRow() - 1][to.getColumn() - 1] =
                    new ChessPiece(board[to.getRow() - 1][to.getColumn() - 1].getTeamColor(), promotionType);
        }
        if (board[to.getRow() - 1][to.getColumn() - 1].getPieceType() == PieceType.KING) {
            if (board[to.getRow() - 1][to.getColumn() - 1].getTeamColor() == TeamColor.WHITE) {
                whiteKingPos = to;
            } else {
                blackKingPos = to;
            }
        }
    }

    public void movePiece(ChessMove move) {
        movePiece(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    public ChessPiece getPiece(int row, int col) {
        return board[row - 1][col - 1];
    }

    public boolean isPiece(int row, int col) {
        return board[row][col] != null;
    }

    public boolean isPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1] != null;
    }

    public boolean isEnemy(boolean isWhite, int row, int col) {
        if (!isPiece(row, col)) return false;
        return board[row][col].getTeamColor() != (isWhite ? TeamColor.WHITE : TeamColor.BLACK);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // This will run for white and black
        for (int i = 0; i < 2; i++) {
            boolean isWhite = i == 0;
            TeamColor color = isWhite ? TeamColor.WHITE : TeamColor.BLACK;
            int row = isWhite ? 1 : 8; // as a ChessPosition, not as an array index
            int row2 = isWhite ? 2 : 7; // as a ChessPosition, not as an array index
            addPiece(new ChessPosition(row, 1), new ChessPiece(color, PieceType.ROOK));
            addPiece(new ChessPosition(row, 2), new ChessPiece(color, PieceType.KNIGHT));
            addPiece(new ChessPosition(row, 3), new ChessPiece(color, PieceType.BISHOP));
            addPiece(new ChessPosition(row, 4), new ChessPiece(color, PieceType.QUEEN));
            addPiece(new ChessPosition(row, 5), new ChessPiece(color, PieceType.KING));
            addPiece(new ChessPosition(row, 6), new ChessPiece(color, PieceType.BISHOP));
            addPiece(new ChessPosition(row, 7), new ChessPiece(color, PieceType.KNIGHT));
            addPiece(new ChessPosition(row, 8), new ChessPiece(color, PieceType.ROOK));
            for (int col = 1; col <= 8; col++) {
                addPiece(new ChessPosition(row2, col), new ChessPiece(color, PieceType.PAWN));
            }
        }
    }
    /*

    |r|n|b|q|k|b|n|r| 8
    |p|p|p|p|p|p|p|p| 7
    | | | | | | | | | 6
    | | | | | | | | | 5
    | | | | | | | | | 4
    | | | | | | | | | 3
    |P|P|P|P|P|P|P|P| 2
    |R|N|B|Q|K|B|N|R| 1

     1 2 3 4 5 6 7 8

     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                sb.append("|");
                sb.append(board[i][j] == null ? " " : board[i][j]);
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;

        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
