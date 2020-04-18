package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
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

    protected int howFarInFront(int yCoordinate, int targetY) {
        int relativeRow;
        if (this.colour == PieceColour.White){
            relativeRow = targetY - yCoordinate;
        }
        else {
            relativeRow = yCoordinate - targetY;
        }
        return relativeRow;
    }

    protected int howFarToTheRight(int xCoordinate, int targetX) {
        return xCoordinate - targetX;
    }

    protected List<ChessSquare> getStraightLineMoves() {
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        List<ChessSquare> openSquares = new ArrayList<>();
        LinkedList<ChessSquare> verticalSquares = ChessView.pieceColumns.get(xCoordinate);
        List<ChessSquare> forwardSquares = verticalSquares.subList(yCoordinate, verticalSquares.size());
        List<ChessSquare> backwardSquares = new ArrayList<>(verticalSquares.subList(0, yCoordinate - 1));
        Collections.reverse(backwardSquares);
        LinkedList<ChessSquare> horizontalSquares = ChessView.pieceRows.get(yCoordinate);
        List<ChessSquare> rightSquares = horizontalSquares.subList(xCoordinate, horizontalSquares.size());
        List<ChessSquare> leftSquares = new ArrayList<>(horizontalSquares.subList(0, xCoordinate - 1));
        Collections.reverse(leftSquares);
        addSquaresUntilBlocked(openSquares, forwardSquares);
        addSquaresUntilBlocked(openSquares, backwardSquares);
        addSquaresUntilBlocked(openSquares, leftSquares);
        addSquaresUntilBlocked(openSquares, rightSquares);
        return openSquares;
    }

    protected List<ChessSquare> getDiagonalMoves(List<ChessSquare> allChessSquares) {
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        int diagonalUpIndex = 8 - xCoordinate + yCoordinate;
        int diagonalDownIndex = xCoordinate + yCoordinate - 1;
        List<ChessSquare> openSquares = new ArrayList<>();
        LinkedList<ChessSquare> diagonalUp = ChessView.pieceUpDiagonals.get(diagonalUpIndex);
        int pieceUpIndex = diagonalUp.indexOf(this.getParentSquare()) + 1;
        List<ChessSquare> upRight = diagonalUp.subList(pieceUpIndex, diagonalUp.size());
        List<ChessSquare> downLeft = new ArrayList<>(diagonalUp.subList(0, pieceUpIndex - 1));
        Collections.reverse(downLeft);
        LinkedList<ChessSquare> diagonalDown = ChessView.pieceDownDiagonals.get(diagonalDownIndex);
        int pieceDownIndex = diagonalDown.indexOf(this.getParentSquare()) + 1;
        List<ChessSquare> upLeft = diagonalDown.subList(pieceDownIndex, diagonalDown.size());
        List<ChessSquare> downRight = new ArrayList<>(diagonalDown.subList(0, pieceDownIndex - 1));
        Collections.reverse(downRight);
        addSquaresUntilBlocked(openSquares, upRight);
        addSquaresUntilBlocked(openSquares, downLeft);
        addSquaresUntilBlocked(openSquares, upLeft);
        addSquaresUntilBlocked(openSquares, downRight);
        return openSquares;
    }

    protected void addSquaresUntilBlocked(List<ChessSquare> openSquares, List<ChessSquare> directionSquares) {
        for (ChessSquare chessSquare : directionSquares) {
            if (chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                openSquares.add(chessSquare);
            }
            else if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                openSquares.add(chessSquare);
                break;
            }
        }
    }

    protected void removeMovesThatTakeTeammate(List<ChessSquare> legalMoves) {
        Iterator<ChessSquare> i = legalMoves.iterator();
        while (i.hasNext()) {
            ChessSquare chessSquare = i.next();
            if (this.getColour() == chessSquare.getPiece().getColour()) {
                i.remove();
            }
        }
    }

}
