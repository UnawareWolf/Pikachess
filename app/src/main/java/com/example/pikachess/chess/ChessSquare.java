package com.example.pikachess.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.LinkedList;
import java.io.Serializable;

public class ChessSquare implements Serializable {

    private static final long serialVersionUID = 9075154632104430357L;

    private SquareBounds boundary;
    private ChessPiece piece;
    private String boardLocation;
    //private LinkedList<ChessSquare> diagonalSquares;// make a new class so that it has attributes like blocked. Maybe. Might need something different because of knights. Actually should work.
    //private LinkedList<ChessSquare> horizontalSquares = new LinkedList<>(); // maybe up down left right (or forwards backwards left right).
    //private Map<Character, ChessSquare> verticalSquares = new HashMap<>();
    private PieceColour colour;
    private int xCoordinate;
    private int yCoordinate;


    public ChessSquare() {}

    public ChessSquare(SquareBounds boundary, ChessPiece piece, int xCoordinate, int yCoordinate){
        this.boundary = boundary;
        this.piece = piece;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.piece.setParentSquare(this);
        //this.boardLocation = boardLocation;
    }

    public ChessSquare(ChessPiece piece){
        this.piece = piece;
        //this.boardLocation = boardLocation;
    }

    public ChessSquare(ChessSquare chessSquare) {
        boundary = chessSquare.boundary;
        piece = chessSquare.piece.copyPiece();
        colour = chessSquare.colour;
        xCoordinate = chessSquare.xCoordinate;
        yCoordinate = chessSquare.yCoordinate;

    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
        this.piece.setParentSquare(this);
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public void set(SquareBounds boundary, ChessPiece piece, String boardLocation){
        this.boundary = boundary;
        this.piece = piece;
        this.boardLocation = boardLocation;
    }

    public SquareBounds getBoundary() {
        return boundary;
    }

//    public String getBoardLocation() {
//        return boardLocation;
//    }

    public void drawSquare(Context context, Canvas canvas, Rect mRect, Paint mPaint) {
        if (colour == PieceColour.Black) {
            mRect.set(boundary.getLeft(), boundary.getTop(), boundary.getRight(), boundary.getBottom());
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(context.getResources().getColor(R.color.chessBrown));
            mPaint.setAlpha(140);
            canvas.drawRect(mRect, mPaint);
        }
        else {
            mRect.set(boundary.getLeft(), boundary.getTop(), boundary.getRight(), boundary.getBottom());
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(context.getResources().getColor(R.color.chessWhite));
            mPaint.setAlpha(140);
            canvas.drawRect(mRect, mPaint);
        }
    }

    public void setColour(PieceColour colour) {
        this.colour = colour;
    }

//    public void setBoardGroups(List<ChessSquare> allChessSquares) {
//        for (ChessSquare chessSquare : allChessSquares) {
//            if (chessSquare.getBoardLocation().charAt(0) == this.boardLocation.charAt(0)) {
//                //this.horizontalSquares.add(chessSquare, chessSquare.getBoardLocation().charAt(0));
//            }
//            if (chessSquare.getBoardLocation().charAt(1) == this.boardLocation.charAt(1)) {
//                this.verticalSquares.put(chessSquare.getBoardLocation().charAt(1), chessSquare);
//            }
//        }
//    }
    //public LinkedList<ChessSquare> getHorizontalSquares() {
//        return this.horizontalSquares;
//    }
    public LinkedList<ChessSquare> getVerticalSquares() {
        //return this.verticalSquares;
        return null;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getXCoordinate() {
        return this.xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getYCoordinate() {
        return this.yCoordinate;
    }

    public void setBounds(SquareBounds boundary) {
        this.boundary = boundary;
    }

    public PieceColour getColour() {
        return this.colour;
    }
}
