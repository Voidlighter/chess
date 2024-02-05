package chess;

import chess.moveset.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final PieceType type;
    private final ChessGame.TeamColor color;
    private final Moveset moveset;

    private final List<ChessMove> moves = new ArrayList<>();

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.color = pieceColor;
        moveset = getMoveset(type);
    }

    private Moveset getMoveset(PieceType type) {
        return switch (type) {
            case KING -> new KingMoves();
            case QUEEN -> new QueenMoves();
            case BISHOP -> new BishopMoves();
            case KNIGHT -> new KnightMoves();
            case ROOK -> new RookMoves();
            case PAWN -> new PawnMoves();
        };
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
        return moveset.getMoves(board, position);
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
        return type != null ? type.toString() : " ";
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
