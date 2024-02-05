package chess.moveset;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessGame;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public abstract class Moveset {

    protected List<ChessMove> moves = new ArrayList<>();
    protected ChessPosition fromPosition;
    protected ChessBoard board;

    public List<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        this.board = board;
        fromPosition = position;
        calcMoves();
        return moves;
    }

    protected abstract void calcMoves();
    
    protected void addMove(int row, int col) {
        moves.add(new ChessMove(fromPosition, row+1, col+1));
    }

    protected void lineMoves(ChessBoard board, ChessPosition position, int[][] directions, int distance) {
        int row = position.getRow()-1;
        int col = position.getColumn()-1;
        boolean isWhite = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE;

        // We are going to iterate in the direction given until we hit a piece (where true if enemy then stop)
        for (int[] direction : directions) {
            for (int i = 1; i<=distance; i++) { // iterate in that direction (as far as the piece would)
                // we pick where we are going to test as a valid move
                int tryY = row + (i * direction[0]);
                int tryX = col + (i * direction[1]);
                if (isInBounds(tryY, tryX)) {
                    if (board.isPiece(tryY,tryX)) {
                        // if we found an enemy, we still add that as a spot we can move
                        if (board.isEnemy(isWhite, tryY, tryX)) {
                            addMove(tryY, tryX);
                        }
                        break; // if we found a piece we are done with this direction
                    } else {
                        // if there's nothing there, we can move there! And we better keep iterating
                        addMove(tryY, tryX);
                    }
                }
            }
        }
    }
    
    protected void lineMoves(ChessBoard board, ChessPosition position, int[][] direction) {
        lineMoves(board, position, direction, 7);
    }

    protected boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

}
