package chess.moveset;

import chess.ChessBoard;
import chess.ChessPosition;

public class BishopMoves extends Moveset {

    @Override
    protected void calcMoves() {
        int[][] directions = {{1,1},{-1,1},{-1,-1},{1,-1}};
        lineMoves(board, fromPosition, directions);
    }
}
