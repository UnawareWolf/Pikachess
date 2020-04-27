package com.example.gravity.chess;

import static java.lang.Math.abs;

public class ChessMove {

    private ChessSquare squareFrom;
    private ChessSquare squareTo;
    private int moveScore;

    public ChessMove(ChessSquare squareFrom, ChessSquare squareTo) {
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
    }

    public ChessMove() {}

    public ChessSquare getSquareFrom() {
        return squareFrom;
    }

    public ChessSquare getSquareTo() {
        return squareTo;
    }

    public ChessPieceId getPieceIdMoved() {
        return squareFrom.getPiece().getId();
    }

    public PieceColour getPieceColourMoved() {
        return squareFrom.getPiece().getColour();
    }

    public ChessPieceId getPieceIdTaken() {
        return squareTo.getPiece().getId();
    }

    public int getMoveScore() {
        return this.moveScore;
    }

    public void setMoveScore(int moveScore) {
        this.moveScore = moveScore;
    }

    public int getAbsYDistanceMoved() {
        return abs(squareTo.getYCoordinate() - squareFrom.getYCoordinate());
    }

    public int getAbsXDistanceMoved() {
        return abs(squareTo.getXCoordinate() - squareFrom.getXCoordinate());
    }

}
