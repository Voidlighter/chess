package chess.movesets;

import chess.ChessBoard;
import chess.ChessPosition;

public class KnightMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        int[][] directions = new int[][]{{1,2},{2,1},{2,-1},{1,-2},{-1,2},{-2,1},{-2,-1},{-1,-2}};
        int distance = 1;
        return lineMoves(board, piecePosition, directions, distance);
    }
}
