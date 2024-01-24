package chess.movesets;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Moveset {

    protected boolean[][] Moves = new boolean[8][8];
    protected List<ChessMove> chessMoves = new ArrayList<>();

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
        for (int i = 0; i < Moves.length; i++) {
            for (int j = 0; j < Moves[i].length; j++) {
                if (Moves[i][j]) {
                    chessMoves.add(new ChessMove(piecePosition, new ChessPosition(i+1, j+1)));
                }
            }
        }
        return chessMoves;
    }

    public abstract boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition);

    protected boolean[][] lineMoves(ChessBoard board, ChessPosition piecePosition, int[][] directions, int distance) {
        boolean[][] moves = new boolean[8][8];
        int row = piecePosition.getRow() - 1;
        int col = piecePosition.getColumn() - 1;

        boolean isWhite = board.getPiece(piecePosition).getTeamColor() == chess.ChessGame.TeamColor.WHITE;

        // Check in each direction what the piece can do
        for (int[] direction : directions) {
            for (int i = 1; i <= distance; i++) { // i = 1–7; checks until edge of board
                int tryY = row + (i * direction[0]);
                int tryX = col + (i * direction[1]);
                if (isInBounds(tryY, tryX)) {
                    if (board.isPiece(tryY, tryX)) {
                        if (board.isEnemy(tryY, tryX, isWhite)) {
                            moves[tryY][tryX] = true;
                        }
                        break; // stops checking in this direction
                    } else {
                        moves[tryY][tryX] = true;
                    }
                }
            }
        }
        return moves;
    }
    protected boolean[][] lineMoves(ChessBoard board, ChessPosition piecePosition, int[][] directions) {
        return lineMoves(board, piecePosition, directions, 7);
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
}
