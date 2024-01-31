package chess.moveset;

import chess.ChessBoard;
import chess.ChessPosition;

public class KnightMoves extends Moveset {

    @Override
    protected void calcMoves(ChessBoard board, ChessPosition position) {
        int distance = 1;
        int[][] directions = {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};
        moves = lineMoves(board, position, directions, distance);
    }
}
