package chess;

import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final PieceType type;
    private final ChessGame.TeamColor color;

    public boolean hasMoved = false;
    public int movedTwo = 0;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.color = pieceColor;
    }

    public ChessPiece(ChessPiece piece) {
        this.type = piece.type;
        this.color = piece.color;
        this.hasMoved = piece.hasMoved;
        this.movedTwo = piece.movedTwo;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN;

        @Override
        public String toString() {
            return this == KNIGHT ? "N" : name().substring(0, 1);
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return MoveCalculator.run(board, position);
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    @Override
    public String toString() {
        return getTeamColor() == WHITE ? type.toString() : type.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
