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
        switch (getPieceType()) {
            case KING:
                moveset = new KingMoves();
            case QUEEN:
                moveset = new QueenMoves();
            case BISHOP:
                moveset = new BishopMoves();
            case KNIGHT:
                moveset = new KnightMoves();
            case ROOK:
                moveset = new RookMoves();
            case PAWN:
                moveset = new PawnMoves();
        }
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
