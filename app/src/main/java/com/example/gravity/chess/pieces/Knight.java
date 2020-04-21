package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gravity.R;
import com.example.gravity.chess.Board;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.ChessView;
import com.example.gravity.chess.PieceColour;
import com.example.gravity.chess.SquareBounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Knight extends ChessPiece {

    ChessPieceId id = ChessPieceId.Knight;

    public Knight() {
        super();
    }

    public Knight(Knight chessPiece) {
        super(chessPiece);
        id = chessPiece.id;
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
}
