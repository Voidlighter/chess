package chess.movesets;

import chess.ChessBoard;
import chess.ChessPosition;

public class RookMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        return lineMoves(board, piecePosition, directions);
    }
}