package chess.movesets;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class BishopMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        int[][] directions = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        return lineMoves(board, piecePosition, directions);
    }
}
