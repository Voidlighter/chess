package chess.moveset;

import chess.*;
import chess.ChessPiece.PieceType;

public class PawnMoves extends Moveset {

    @Override
    protected void calcMoves() {

        // Move forward
        boolean isWhite = board.getPiece(fromPosition).getTeamColor() == ChessGame.TeamColor.WHITE;
        int upOne = isWhite ? 1 : -1;
        int row = fromPosition.getRow() - 1;
        int col = fromPosition.getColumn() - 1;

        int tryY = row + upOne;
        int tryX = col;

        if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX)) {
            addMove(tryY, tryX);

            tryY = row + (2 * upOne);

            // Move two forward
            boolean hasMoved = isWhite ? row != 1 : row != 6;
            if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX) && !hasMoved) {
                addMove(tryY, tryX);
            }
        }

        // Attack Diagonal
        tryY = row + upOne;
        for (int i = -1; i <= 1; i += 2) { // This will just try on both sides
            tryX = col + i;
            if (isInBounds(tryY, tryX) && board.isEnemy(isWhite, tryY, tryX)) {
                addMove(tryY, tryX);
            }
        }
    }

    protected void addMove(int row, int col) {
        if (row == 0 || row == 7) {
            PieceType[] pieces = {
                    PieceType.QUEEN,
                    PieceType.BISHOP,
                    PieceType.KNIGHT,
                    PieceType.ROOK,
            };
            for (PieceType piece : pieces) {
                ChessPosition newPosition = new ChessPosition(row + 1, col + 1);
                moves.add(new ChessMove(fromPosition, newPosition, piece));
            }
        } else {
            moves.add(new ChessMove(fromPosition, row + 1, col + 1));
        }
    }
}
