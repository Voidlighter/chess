package chess;

import chess.ChessPiece.PieceType;
import chess.ChessGame.TeamColor;
/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];
    public ChessBoard() {
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    public ChessPiece getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isPiece(int row, int col) {
        return board[row][col] != null;
    }

    public boolean isEnemy(int row, int col, boolean isWhite) {
        return board[row][col] != null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        for (int i = 0; i < 8; i++) {
            board[1][i] = new ChessPiece(TeamColor.WHITE, PieceType.PAWN);
            board[6][i] = new ChessPiece(TeamColor.BLACK, PieceType.PAWN);
        }
        board[0][0] = new ChessPiece(TeamColor.WHITE, PieceType.ROOK);
        board[0][1] = new ChessPiece(TeamColor.WHITE, PieceType.KNIGHT);
        board[0][2] = new ChessPiece(TeamColor.WHITE, PieceType.BISHOP);
        board[0][3] = new ChessPiece(TeamColor.WHITE, PieceType.QUEEN);
        board[0][4] = new ChessPiece(TeamColor.WHITE, PieceType.KING);
        board[0][5] = new ChessPiece(TeamColor.WHITE, PieceType.BISHOP);
        board[0][6] = new ChessPiece(TeamColor.WHITE, PieceType.KNIGHT);
        board[0][7] = new ChessPiece(TeamColor.WHITE, PieceType.ROOK);

        board[7][0] = new ChessPiece(TeamColor.BLACK, PieceType.ROOK);
        board[7][1] = new ChessPiece(TeamColor.BLACK, PieceType.KNIGHT);
        board[7][2] = new ChessPiece(TeamColor.BLACK, PieceType.BISHOP);
        board[7][3] = new ChessPiece(TeamColor.BLACK, PieceType.QUEEN);
        board[7][4] = new ChessPiece(TeamColor.BLACK, PieceType.KING);
        board[7][5] = new ChessPiece(TeamColor.BLACK, PieceType.BISHOP);
        board[7][6] = new ChessPiece(TeamColor.BLACK, PieceType.KNIGHT);
        board[7][7] = new ChessPiece(TeamColor.BLACK, PieceType.ROOK);
    }
}
