package chess.moveset;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public abstract class Moveset {

    protected boolean[][] moves = new boolean[8][8];

    public boolean[][] getMoves(ChessBoard board, ChessPosition position) {
        calcMoves(board, position);
        return moves;
    }

    protected abstract void calcMoves(ChessBoard board, ChessPosition position);

    protected boolean[][] lineMoves(ChessBoard board, ChessPosition position, int[][] directions, int distance) {
        boolean[][] newMoves = new boolean[8][8];
        int row = position.getRow()-1;
        int col = position.getColumn()-1;
        boolean isWhite = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE;

        // We are going to iterate in the direction given until we hit a piece (where true if enemy then stop)
        for (int[] direction : directions) { // pick a direction
            for (int i = 1; i<=distance; i++) { // iterate in that direction (as far as the piece would)
                // we pick where we are going to test as a valid move
                int tryY = row + (i * direction[0]);
                int tryX = col + (i * direction[1]);
                // and make sure it is in bounds
                if (isInBounds(tryY, tryX)) {
                    // if there is a piece, we stop
                    if (board.isPiece(tryY,tryX)) {
                        //but if it's an enemy, we still add that as a spot we can move
                        if (board.isEnemy(isWhite, tryY, tryX)) {
                            newMoves[tryY][tryX] = true;
                        }
                        break; // again, if there is a piece, we try a different direction
                    } else {
                        // if there's nothing there, we can move there! And we better keep iterating
                        newMoves[tryY][tryX] = true;
                    }
                }
            }
        }
        return newMoves;
    }

    protected boolean[][] lineMoves(ChessBoard board, ChessPosition position, int[][] direction) {
        return lineMoves(board, position, direction, 7);
    }

    protected boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

}
