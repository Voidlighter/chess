package chess.movesets;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class BishopMoves extends Moveset {
    public boolean[][] getMoves(ChessBoard board, ChessPosition piecePosition) {
        boolean[][] moves = new boolean[8][8];
        int row = piecePosition.getRow() - 1;
        int col = piecePosition.getColumn() - 1;

        boolean isWhite = board.getPiece(piecePosition).getTeamColor() == ChessGame.TeamColor.WHITE;

        // Check if the pawn can take a piece
        for (int x = -1; x <= 1; x += 2) { // i = -1, 1; checks left and right each once
            for (int y = -1; y <= 1; y += 2) { // y = -1, 1; checks down and up each once
                for (int i = 1; i < 8; i++) { // i = 1–7; checks until edge of board
                    int tryY = row + (i * y);
                    int tryX = col + (i * x);
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
        }

        // TODO: Implement en passant

        boolean onLastRow = isWhite ? row == 7 : row == 0;

        // TODO: Implement pawn promotion

        return moves;
    }
}
