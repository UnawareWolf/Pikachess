package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public abstract class ChessPiece {

    protected PieceColour colour;
    protected Bitmap pieceImage;
    protected SquareBounds location;

    //public abstract void drawPiece();
    public abstract SquareBounds getLocation();
    public abstract List<ChessSquare> getLegalMoves();
    public abstract void movePiece();
    public abstract void setPieceImage(Context context);
    public abstract Bitmap getPieceImage();
    public abstract ChessPieceId getId();
    public abstract void resizePieceImage(int width);

    public void drawPiece(Canvas canvas) {
        canvas.drawBitmap(this.pieceImage, location.getLeft(), location.getTop(), null);
    }

    public void setColour(PieceColour colour){
        this.colour = colour;
    }

    public PieceColour getColour() {
        return colour;
    }
}
