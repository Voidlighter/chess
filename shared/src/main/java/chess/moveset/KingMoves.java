package chess.moveset;

import chess.ChessBoard;
import chess.ChessPosition;

public class KingMoves extends Moveset {

    @Override
    protected void calcMoves() {
        int distance = 1;
        int[][] directions = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
        lineMoves(board, fromPosition, directions, distance);
    }
}
