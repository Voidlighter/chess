package chess.movesets;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class PawnMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        boolean[][] validMoves = new boolean[8][8];
        int row = piecePosition.getRow();
        int col = piecePosition.getColumn();

        boolean isWhite = board.getPiece(piecePosition).getTeamColor() == ChessGame.TeamColor.WHITE;
        int upOne = isWhite ? 1 : -1;

        // Check if the pawn can move one space forward
        if (isInBounds(row + upOne, col) && !board.isPiece(row + upOne, col)) {
            validMoves[row + upOne][col] = true;

            // Check if the pawn can move two spaces forward
            boolean inStartingPosition = isWhite ? row == 1 : row == 6;
            if (inStartingPosition && !board.isPiece(row + (upOne * 2), col)) {

                validMoves[row + (upOne * 2)][col] = true;
            }
        }

        // Check if the pawn can take a piece
        for (int x = -1; x <= 1; x += 2) { // i = -1, 1; checks left and right
            if (isInBounds(row + upOne, col + x) &&
                    board.isEnemy(row + upOne, col + x, isWhite)) {

                validMoves[row + upOne][col + x] = true;
            }
        }

        // TODO: Implement en passant

        boolean onLastRow = isWhite ? row == 7 : row == 0;

        // TODO: Implement pawn promotion

        return validMoves;
    }
}
