package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = null;
    }

    public ChessMove(ChessPosition startPosition, int row, int col) {
        this.startPosition = startPosition;
        this.endPosition = new ChessPosition(row, col);
        this.promotionPiece = null;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public String toString() {
        if (promotionPiece != null) {
            return startPosition + "->" + endPosition + "=" + promotionPiece;
        }
        return startPosition + "->" + endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;

        if (getStartPosition() != null ? !getStartPosition().equals(chessMove.getStartPosition()) : chessMove.getStartPosition() != null)
            return false;
        if (getEndPosition() != null ? !getEndPosition().equals(chessMove.getEndPosition()) : chessMove.getEndPosition() != null)
            return false;
        return getPromotionPiece() == chessMove.getPromotionPiece();
    }

    @Override
    public int hashCode() {
        int result = getStartPosition() != null ? getStartPosition().hashCode() : 0;
        result = 31 * result + (getEndPosition() != null ? getEndPosition().hashCode() : 0);
        result = 31 * result + (getPromotionPiece() != null ? getPromotionPiece().hashCode() : 0);
        return result;
    }
}
