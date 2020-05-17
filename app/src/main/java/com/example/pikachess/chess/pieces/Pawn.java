package com.example.pikachess.chess.pieces;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.pikachess.R;
import com.example.pikachess.chess.Board;
import com.example.pikachess.chess.ChessMove;
import com.example.pikachess.chess.ChessPiece;
import com.example.pikachess.chess.ChessPieceId;
import com.example.pikachess.chess.ChessSquare;
import com.example.pikachess.chess.PieceColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(PieceColour colour) {
        super(colour);
        this.id = ChessPieceId.Pawn;
        this.score = 1;
    }

    public Pawn(Pawn chessPiece) {
        super(chessPiece);
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_pawn);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn);
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
        List<ChessSquare> legalMoves = new ArrayList<>();
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        boolean canMoveTwoSquares = false;

        for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
            int targetX = chessSquare.getXCoordinate();
            int targetY = chessSquare.getYCoordinate();
            int relativeXPos = howFarToTheRight(xCoordinate, targetX);
            int relativeYPos = howFarInFront(yCoordinate, targetY);

            if (chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                if (targetX == xCoordinate && relativeYPos == 1) {
                    legalMoves.add(chessSquare);
                    canMoveTwoSquares = true;
                }
//                else if (!this.hasMoved && relativeYPos == 2 && targetX == xCoordinate) {
//                    legalMoves.add(chessSquare);
//                }
            }
            else if (relativeYPos == 1 && (relativeXPos == -1 || relativeXPos == 1)) {
                legalMoves.add(chessSquare);
            }

            ChessMove lastMove = chessBoard.getLastMove();
            if (lastMove != null) {
                if (lastMove.getPieceIdMoved() == ChessPieceId.Pawn && lastMove.getAbsYDistanceMoved() == 2) {
                    if (lastMove.getSquareTo().getXCoordinate() == targetX && lastMove.getSquareTo().getYCoordinate() == yCoordinate && (relativeXPos == 1|| relativeXPos == -1) && relativeYPos == 1) {
                        legalMoves.add(chessSquare);
                    }
                }
            }
        }
        if (!this.hasMoved && canMoveTwoSquares) {

            for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
                int targetX = chessSquare.getXCoordinate();
                int targetY = chessSquare.getYCoordinate();
                int relativeYPos = howFarInFront(yCoordinate, targetY);
                if (chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                    if (relativeYPos == 2 && targetX == xCoordinate) {
                        legalMoves.add(chessSquare);
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public ChessPiece copyPiece() {
        return new Pawn(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }
}
