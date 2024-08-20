package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard gameBoard = new ChessBoard();
    private TeamColor teamTurn = TeamColor.WHITE;

    public ChessGame() {
        gameBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    public void nextTurn() {
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
        gameBoard.nextTurn();
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE, BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (gameBoard.getPiece(startPosition) == null) {
            return new ArrayList<>();
        }
        Collection<ChessMove> moves = gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessBoard tempBoard;
        for (ChessMove move : moves) {
            tempBoard = new ChessBoard(gameBoard);
            tempBoard.movePiece(move);
            ChessPosition kingPos = gameBoard.getPiece(startPosition).getTeamColor() == TeamColor.WHITE ?
                    tempBoard.whiteKingPos : tempBoard.blackKingPos;
            if (MoveCalculator.canBeAttacked(tempBoard, kingPos)) {
                continue;
            }
            validMoves.add(move);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPos = move.getStartPosition();
        ChessPiece startPiece = gameBoard.getPiece(startPos);
        if (startPiece == null) throw new InvalidMoveException("No starting piece there");
        TeamColor team = startPiece.getTeamColor();
        if (team != teamTurn) {
            throw new InvalidMoveException("Can't move " + move.getStartPosition() + " to " +
                    move.getEndPosition() + " because it is not " + team + "'s turn");
        }
        if (validMoves(startPos).contains(move)) {
            gameBoard.movePiece(move);
            nextTurn();
        } else {
            throw new InvalidMoveException("Can't move" + move.getStartPosition() + " to " +
                    move.getEndPosition() + " because it is not a valid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = teamColor == TeamColor.WHITE ? gameBoard.whiteKingPos : gameBoard.blackKingPos;
        return MoveCalculator.canBeAttacked(gameBoard, kingPos);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && noValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && noValidMoves(teamColor);
    }

    public boolean noValidMoves(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                if (gameBoard.isPiece(position) && gameBoard.getPiece(position).getTeamColor() == teamColor) {
                    if (!validMoves(position).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;

        return Objects.deepEquals(gameBoard, chessGame.gameBoard);
    }

    @Override
    public int hashCode() {
        return gameBoard != null ? gameBoard.hashCode() : 0;
    }
}
