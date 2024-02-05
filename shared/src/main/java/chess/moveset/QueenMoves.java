package chess.moveset;

import chess.ChessBoard;
import chess.ChessPosition;

public class QueenMoves extends Moveset {

    @Override
    protected void calcMoves() {
        int[][] directions = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
        lineMoves(board, fromPosition, directions);
    }
}
