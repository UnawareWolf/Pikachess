package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gravity.R;

import java.util.LinkedList;

public class ChessSquare {

    private SquareBounds boundary;
    private ChessPiece piece;
    private String boardLocation;
    private LinkedList<ChessSquare> diagonalSquares;// make a new class so that it has attributes like blocked. Maybe. Might need something different because of knights. Actually should work.
    private LinkedList<ChessSquare> horizontalSquares; // maybe up down left right (or forwards backwards left right).
    private LinkedList<ChessSquare> verticalSquares;
    private PieceColour colour;

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

    public void drawSquare(Context context, Canvas canvas, Rect mRect, Paint mPaint) {
        if (colour == PieceColour.Black) {
            mRect.set(boundary.getLeft(), boundary.getTop(), boundary.getRight(), boundary.getBottom());
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(context.getResources().getColor(R.color.chessBrown));
            mPaint.setAlpha(140);
            canvas.drawRect(mRect, mPaint);
        }
    }

    public void setColour(PieceColour colour) {
        this.colour = colour;
    }
}
