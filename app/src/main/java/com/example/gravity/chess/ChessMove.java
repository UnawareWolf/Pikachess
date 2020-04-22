package com.example.gravity.chess;

public class ChessMove {

    private ChessSquare squareFrom;
    private ChessSquare squareTo;

    public ChessMove(ChessSquare squareFrom, ChessSquare squareTo) {
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
    }

    public ChessSquare getSquareFrom() {
        return squareFrom;
    }

    public ChessSquare getSquareTo() {
        return squareTo;
    }

    public ChessPieceId getPieceMoved() {
        return squareTo.getPiece().getId();
    }

    public int getYDistanceMoved() {
        return squareTo.getYCoordinate() - squareFrom.getYCoordinate();
    }

}
