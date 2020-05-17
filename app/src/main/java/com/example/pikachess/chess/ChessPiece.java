package com.example.pikachess.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pikachess.chess.pieces.Empty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public abstract class ChessPiece implements Serializable {

    private static final long serialVersionUID = 4661494751713456981L;

    protected PieceColour colour;
    protected transient Bitmap pieceImage;
    //protected SquareBounds location;
    protected ChessSquare parentSquare;
    protected boolean hasMoved = false;
    protected ChessPieceId id;
    protected int score;

    public abstract void setPieceImage(Context context);
    public abstract ChessPieceId getId();
    public abstract List<ChessSquare> getPieceSpecificLegalMoves(Board chessBoard);
    public abstract List<ChessSquare> getPieceSpecificAttackingMoves(Board chessBoard);
    public void setParentSquare(ChessSquare chessSquare) {
        this.parentSquare = chessSquare;
    }
    public ChessSquare getParentSquare() {
        return this.parentSquare;
    }
    public void setHasMoved() {
        this.hasMoved = true;
    }
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public ChessPiece(PieceColour colour) {
        this.colour = colour;
    }

    void resizePieceImage(int squareSize) {
        this.pieceImage = Bitmap.createScaledBitmap(pieceImage, squareSize, squareSize, true);
    }

    public ChessPiece(ChessPiece chessPiece) {
        colour = chessPiece.colour;
        id = chessPiece.id;
        hasMoved = chessPiece.hasMoved;
        score = chessPiece.score;
    }



    public abstract ChessPiece copyPiece();

//    public SquareBounds getLocation() {
//        return this.location;
//    }
//
//    public void setLocation(SquareBounds location) {
//        this.location = location;
//    }

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

    protected List<ChessSquare> getStraightLineMoves(Board chessBoard) {
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        List<ChessSquare> openSquares = new ArrayList<>();
        LinkedList<ChessSquare> verticalSquares = chessBoard.getPieceColumns().get(xCoordinate);
        List<ChessSquare> forwardSquares = verticalSquares.subList(yCoordinate, verticalSquares.size());
        List<ChessSquare> backwardSquares = new ArrayList<>(verticalSquares.subList(0, yCoordinate - 1));
        Collections.reverse(backwardSquares);
        LinkedList<ChessSquare> horizontalSquares = chessBoard.getPieceRows().get(yCoordinate);
        List<ChessSquare> rightSquares = horizontalSquares.subList(xCoordinate, horizontalSquares.size());
        List<ChessSquare> leftSquares = new ArrayList<>(horizontalSquares.subList(0, xCoordinate - 1));
        Collections.reverse(leftSquares);
        openSquares.addAll(addSquaresUntilBlocked(forwardSquares));
        openSquares.addAll(addSquaresUntilBlocked(backwardSquares));
        openSquares.addAll(addSquaresUntilBlocked(leftSquares));
        openSquares.addAll(addSquaresUntilBlocked(rightSquares));
        return openSquares;
    }

    protected List<ChessSquare> getDiagonalMoves(Board chessBoard) {
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        int diagonalUpIndex = 8 - xCoordinate + yCoordinate;
        int diagonalDownIndex = xCoordinate + yCoordinate - 1;
        List<ChessSquare> openSquares = new ArrayList<>();
        LinkedList<ChessSquare> diagonalUp = chessBoard.getPieceUpDiagonals().get(diagonalUpIndex);
        int pieceUpIndex = diagonalUp.indexOf(this.getParentSquare()) + 1;
        List<ChessSquare> upRight = diagonalUp.subList(pieceUpIndex, diagonalUp.size());
        List<ChessSquare> downLeft = new ArrayList<>(diagonalUp.subList(0, pieceUpIndex - 1));
        Collections.reverse(downLeft);
        LinkedList<ChessSquare> diagonalDown = chessBoard.getPieceDownDiagonals().get(diagonalDownIndex);
        int pieceDownIndex = diagonalDown.indexOf(this.getParentSquare()) + 1;
        List<ChessSquare> upLeft = diagonalDown.subList(pieceDownIndex, diagonalDown.size());
        List<ChessSquare> downRight = new ArrayList<>(diagonalDown.subList(0, pieceDownIndex - 1));
        Collections.reverse(downRight);
        openSquares.addAll(addSquaresUntilBlocked(upRight));
        openSquares.addAll(addSquaresUntilBlocked(downLeft));
        openSquares.addAll(addSquaresUntilBlocked(upLeft));
        openSquares.addAll(addSquaresUntilBlocked(downRight));
        return openSquares;
    }

    protected List<ChessSquare> addSquaresUntilBlocked(List<ChessSquare> directionSquares) {
        List<ChessSquare> openSquares = new ArrayList<>();
        for (ChessSquare chessSquare : directionSquares) {
            if (chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                openSquares.add(chessSquare);
            }
            else if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                openSquares.add(chessSquare);
                break;
            }
        }
        return openSquares;
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

    protected List<ChessSquare> getLegalMoves(Board chessBoard) {
        List<ChessSquare> legalMoves = getPieceSpecificLegalMoves(chessBoard);
        removeMovesThatTakeTeammate(legalMoves);// check that this does something. ie size decreases.
        removeMovesThatLeaveSelfInCheck(chessBoard, legalMoves);
        return legalMoves;
    }

    protected void removeMovesThatLeaveSelfInCheck(Board chessBoard, List<ChessSquare> legalMoves) {
        Iterator<ChessSquare> i = legalMoves.iterator();
        while (i.hasNext()) {
            ChessSquare legalMoveSquare = i.next();
            if (moveLeavesSelfInCheck(legalMoveSquare, chessBoard)) {
                i.remove();
            }
        }
    }

    protected boolean moveLeavesSelfInCheck(ChessSquare legalMoveSquare, Board chessBoard) {
        Board boardCopy = new Board(chessBoard); // refactor this into separate methods to make it clear what each step is doing.
//        ChessSquare legalSquareCopy = new ChessSquare(legalMoveSquare);
        ChessSquare legalSquareCopy = new ChessSquare();
        for (ChessSquare copySquare : boardCopy.getBoardSquares()) {
            if (copySquare.getXCoordinate() == legalMoveSquare.getXCoordinate() && copySquare.getYCoordinate() == legalMoveSquare.getYCoordinate()) {
                legalSquareCopy = copySquare;
            }
        }
        for (ChessSquare copySquare : boardCopy.getBoardSquares()) {
            //copySquare.getPiece().setParentSquare(copySquare); // not ideal // don't think this is needed anymore?
            //return ();
            // if the square on the new board is the same as the square we clicked on,
            // do the legal move on the copy board. Then get every square that is under attack
            // on the copy board. For each of those squares, check if the piece under attack
            // is a king. If it is, return true which will make the legal move become illegal.
            int xSelectedPiece = this.getParentSquare().getXCoordinate();
            int ySelectedPiece = this.getParentSquare().getYCoordinate();
            if (copySquare.getYCoordinate() == ySelectedPiece && copySquare.getXCoordinate() == xSelectedPiece) {
                // why does 'this' change??? Not sure but it happens upon this if statement becoming true.
                //ChessPiece savePiece = legalSquareCopy.getPiece();
                legalSquareCopy.setPiece(copySquare.getPiece()); // should this be a copy or something? yes.
                copySquare.setPiece(new Empty());

                List<ChessSquare> underAttackSquares = boardCopy.getSquaresUnderAttack();
                for (ChessSquare underAttackSquare : underAttackSquares) {
                    if (underAttackSquare.getPiece().getId() == ChessPieceId.King && underAttackSquare.getPiece().getColour() == this.getColour()) {
                        return true;
                    }
                }
//                copySquare.setPiece(legalSquareCopy.getPiece()); does nothing
//                legalSquareCopy.setPiece(savePiece);
            }
        }
        return false;
    }

    private List<ChessSquare> getAttackingSquares(Board chessBoard, ChessSquare underAttackSquare) {
        Board boardCopy = new Board(chessBoard);
        List<ChessSquare> attackingSquares = new ArrayList<>();
        //underAttackSquare

        return attackingSquares;
    }

    public abstract int getPieceScore();
}
