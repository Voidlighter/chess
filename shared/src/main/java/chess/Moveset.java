package chess;

import static chess.ChessPiece.PieceType;

public class Moveset {
    int[][] directions;

    int distance;

    public Moveset(PieceType type) {
        this.directions = directionsByType(type);
        this.distance = distanceByType(type);
    }

    private int[][] directionsByType(ChessPiece.PieceType type) {
        return switch (type) {
            case KING, QUEEN -> new int[][]{{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
            case BISHOP -> new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
            case KNIGHT -> new int[][]{{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
            case ROOK -> new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            case PAWN -> new int[][]{{1, 1}, {1, -1}}; // This represents the pawns attacks
        };
    }

    private int distanceByType(ChessPiece.PieceType type) {
        return switch (type) {
            case KING, KNIGHT, PAWN -> 1;
            default -> 7;
        };
    }


    public int[][] getDirections() {
        return directions;
    }

    public int getDistance() {
        return distance;
    }

    public void flipDirections() {
        for (int[] direction : directions) {
            direction[0] *= -1; // Flip the row direction for black pawns
        }
    }
}
