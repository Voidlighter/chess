package chess.movesets;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Moveset {

    private boolean[][] Moves = new boolean[8][8];

    public List<int[]> getTupledMoves() {
        List<int[]> tupledMoves = new ArrayList<>();
        for (int i = 0; i < Moves.length; i++) {
            for (int j = 0; j < Moves[i].length; j++) {
                if (Moves[i][j]) {
                    tupledMoves.add(new int[]{i, j});
                }
            }
        }
        return tupledMoves;
    }
    public List<int[]> getTupledMoves(ChessBoard board, ChessPosition piecePosition) {
        Moves = getMoves(board, piecePosition);
        return getTupledMoves();
    }

    public Collection<ChessMove> getChessMoves(ChessBoard board, ChessPosition piecePosition) {
        Moves = getMoves(board, piecePosition);
        List<ChessMove> chessMoves = new ArrayList<>();
        for (int i = 0; i < Moves.length; i++) {
            for (int j = 0; j < Moves[i].length; j++) {
                if (Moves[i][j]) {
                    chessMoves.add(new ChessMove(piecePosition, new ChessPosition(i, j)));
                }
            }
        }
        return chessMoves;
    }

    protected boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    protected boolean isInBounds(int row) {
        return row >= 0 && row < 8;
    }

    public String toString() {
        List<int[]> tupledMoves = getTupledMoves();
        StringBuilder sb = new StringBuilder();
        for (int[] tupledMove : tupledMoves) {
            sb.append(tupledMove[0]).append(", ").append(tupledMove[1]).append("\n");
        }
        return sb.toString();
    }

    public abstract boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition);


}
