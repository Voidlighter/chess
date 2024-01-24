package chess;

import chess.movesets.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;
    public Moveset moveset;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
        PAWN
    }
    private String typeToString(PieceType type) {
        return switch (type) {
            case KING -> "King";
            case QUEEN -> "Queen";
            case BISHOP -> "Bishop";
            case KNIGHT -> "Knight";
            case ROOK -> "Rook";
            case PAWN -> "Pawn";
            case null -> " ";
            default -> throw new IllegalStateException("Unexpected PieceType: " + type);
        };
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return moveset.getChessMoves(board, myPosition);
    }

    @Override
    public String toString() {
        String str = typeToString(type);
        if (getTeamColor() == ChessGame.TeamColor.BLACK) {
            str = str.toLowerCase();
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;

        if (pieceColor != that.pieceColor) return false;
        if (type != that.type) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = pieceColor != null ? pieceColor.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
