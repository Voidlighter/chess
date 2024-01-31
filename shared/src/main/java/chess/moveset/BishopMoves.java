package chess.moveset;

import chess.ChessBoard;
import chess.ChessPosition;

public class BishopMoves extends Moveset {

    @Override
    protected void calcMoves(ChessBoard board, ChessPosition position) {
        int[][] directions = {{1,1},{-1,1},{-1,-1},{1,-1}};
        moves = lineMoves(board, position, directions);
    }
}
