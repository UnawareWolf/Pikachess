package com.example.gravity;

import java.util.Set;

public class ChessSquare {

    private SquareBounds boundary;
    private ChessPieceId piece;
    private String boardLocation;
   // private Set<ChessSquare> diagonalSquares;

    public ChessSquare(SquareBounds boundary, ChessPieceId piece, String boardLocation){
        this.boundary = boundary;
        this.piece = piece;
        this.boardLocation = boardLocation;
    }

    public ChessPieceId getPiece() {
        return piece;
    }

    public SquareBounds getBoundary() {
        return boundary;
    }

    public String getBoardLocation() {
        return boardLocation;
    }
}
