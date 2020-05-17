package com.example.pikachess.chess.pieces;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.pikachess.R;
import com.example.pikachess.chess.Board;
import com.example.pikachess.chess.ChessPiece;
import com.example.pikachess.chess.ChessPieceId;
import com.example.pikachess.chess.ChessSquare;
import com.example.pikachess.chess.PieceColour;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
    public Knight(Knight chessPiece) {
        super(chessPiece);
    }

    public Knight(PieceColour colour) {
        super(colour);
        this.id = ChessPieceId.Knight;
        this.score = 3;
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_knight);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.knight);
        }
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getPieceSpecificLegalMoves(Board chessBoard) {
        return getPieceSpecificAttackingMoves(chessBoard);
    }

    @Override
    public List<ChessSquare> getPieceSpecificAttackingMoves(Board chessBoard) {
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        List<ChessSquare> legalMoves = new ArrayList<>();
        for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
            int targetX = chessSquare.getXCoordinate();
            int targetY = chessSquare.getYCoordinate();
            int relativeXPos = howFarToTheRight(xCoordinate, targetX);
            int relativeYPos = howFarInFront(yCoordinate, targetY);
            if (((relativeXPos == 1 || relativeXPos == - 1) && (relativeYPos == 2 || relativeYPos == - 2)) || ((relativeXPos == 2 || relativeXPos == - 2) && (relativeYPos == 1 || relativeYPos == - 1))) {
                legalMoves.add(chessSquare);
            }
        }
        return legalMoves;
    }

    @Override
    public ChessPiece copyPiece() {
        return new Knight(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }
}
