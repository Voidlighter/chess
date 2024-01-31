package chess.moveset;

import chess.*;
import chess.ChessPiece.PieceType;

public class PawnMoves extends Moveset {

    @Override
    protected void calcMoves(ChessBoard board, ChessPosition position) {

        // Move forward
        boolean isWhite = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE;
        int upOne = isWhite ? 1 : -1;
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;

        int tryY = row + upOne;
        int tryX = col;

        if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX)) {
            moves[tryY][tryX] = true;

            tryY = row + (2 * upOne);

            // Move two forward
            boolean hasMoved = isWhite ? row != 1 : row != 6;
            if (isInBounds(tryY, tryX) && !board.isPiece(tryY, tryX) && !hasMoved) {
                moves[tryY][tryX] = true;
            }
        }

        // Attack Diagonal
        tryY = row + upOne;
        for (int i = -1; i <= 1; i += 2) { // This will just try on both sides
            tryX = col + i;
            if (isInBounds(tryY, tryX) && board.isEnemy(isWhite, tryY, tryX)) {
                moves[tryY][tryX] = true;
            }
        }

        // Promotions!!!
        tryY = isWhite ? 7 : 0;
        for (int i = 0; i <= 7; i++) {
            // in the top row, if there is a promotion move...
            if (moves[tryY][i]) {
                moves[tryY][i] = false;
                PieceType[] pieces = {
                        PieceType.QUEEN,
                        PieceType.BISHOP,
                        PieceType.KNIGHT,
                        PieceType.ROOK,
                };
                for (PieceType piece : pieces) {
                    ChessPosition newPosition = new ChessPosition(tryY + 1, i + 1);
                    board.getPiece(position).chessMoves.add(new ChessMove(position, newPosition, piece));
                }
            }
        }
    }
}
