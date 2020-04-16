package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public abstract class ChessPiece {

    protected PieceColour colour;
    protected Bitmap pieceImage;
    protected SquareBounds location;
    //ChessPieceId id;

    //public abstract void drawPiece();
    //public abstract SquareBounds getLocation();
    public abstract List<ChessSquare> getLegalMoves();
    //public abstract void movePiece();
    public abstract void setPieceImage(Context context);
    public abstract ChessPieceId getId();
    public Bitmap getPieceImage() {
        return this.pieceImage;
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

    public PieceColour getColour() {
        return colour;
    }
}
