package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public abstract class ChessPiece {

    protected PieceColour colour;
    protected Bitmap pieceImage;
    protected SquareBounds location;
    protected ChessSquare parentSquare;
    protected boolean hasMoved;
    //ChessPieceId id;

    //public abstract void drawPiece();
    //public abstract SquareBounds getLocation();
    public abstract List<ChessSquare> getLegalMoves();
    //public abstract void movePiece();
    public abstract void setPieceImage(Context context);
    public abstract ChessPieceId getId();
    public abstract List<ChessSquare> getLegalMoves(List<ChessSquare> allChessSquares);
    public void setParentSquare(ChessSquare chessSquare) {
        this.parentSquare = chessSquare;
    }
    public ChessSquare getParentSquare() {
        return this.parentSquare;
    }
    public Bitmap getPieceImage() {
        return this.pieceImage;
    }
    public void setHasMoved() {
        this.hasMoved = true;
    }

    //public abstract void setId(ChessPieceId id);
    void resizePieceImage(int squareSize) {
        this.pieceImage = Bitmap.createScaledBitmap(pieceImage, squareSize, squareSize, true);
    }
    /*
    public  ChessPieceId getId() {
        return this.id;
    }*/

    public SquareBounds getLocation() {
        return this.location;
    }

    public void setLocation(SquareBounds location) {
        this.location = location;
    }

    void drawPiece(Canvas canvas, SquareBounds boundary) {
        canvas.drawBitmap(this.pieceImage, boundary.getLeft(), boundary.getTop(), null);
    }

    void setColour(PieceColour colour){
        this.colour = colour;
    }
    protected char getNextChar(char letter) {
        return (char) ((int) letter + 1);
    }

    public PieceColour getColour() {
        return colour;
    }

    protected int howFarInFront(int positionNumber, int targetNumber) {
        int relativeRow;
        if (this.colour == PieceColour.White){
            relativeRow = targetNumber - positionNumber;
        }
        else {
            relativeRow = positionNumber - targetNumber;
        }
        return relativeRow;
    }

    protected int howFarToTheRight(char positionLetter, char targetLetter) {
        return positionLetter - targetLetter;
    }
}
