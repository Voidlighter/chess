package chess.movesets;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class KnightMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        boolean[][] moves = new boolean[8][8];
        int row = piecePosition.getRow() - 1;
        int col = piecePosition.getColumn() - 1;

        boolean isWhite = board.getPiece(piecePosition).getTeamColor() == ChessGame.TeamColor.WHITE;

        int[][] directions = new int[][]{{1,2},{2,1},{2,-1},{1,-2},{-1,2},{-2,1},{-2,-1},{-1,-2}};
        int distance = 1;
        moves = lineMoves(board, piecePosition, directions, distance);

        return moves;
    }
}
