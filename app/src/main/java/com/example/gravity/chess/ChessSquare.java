package com.example.gravity.chess;

import java.util.LinkedList;

public class ChessSquare {

    private SquareBounds boundary;
    private ChessPiece piece;
    private String boardLocation;
    private LinkedList<ChessSquare> diagonalSquares;// make a new class so that it has attributes like blocked. Maybe. Might need something different because of knights. Actually should work.
    private LinkedList<ChessSquare> horizontalSquares; // maybe up down left right (or forwards backwards left right).
    private LinkedList<ChessSquare> verticalSquares;

    public ChessSquare() {}

    public ChessSquare(SquareBounds boundary, ChessPiece piece, String boardLocation){
        this.boundary = boundary;
        this.piece = piece;
        this.boardLocation = boardLocation;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void set(SquareBounds boundary, ChessPiece piece, String boardLocation){
        this.boundary = boundary;
        this.piece = piece;
        this.boardLocation = boardLocation;
    }

    public SquareBounds getBoundary() {
        return boundary;
    }

    public String getBoardLocation() {
        return boardLocation;
    }
}
