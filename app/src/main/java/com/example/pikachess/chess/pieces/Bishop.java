package com.example.pikachess.chess.pieces;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.pikachess.R;
import com.example.pikachess.chess.Board;
import com.example.pikachess.chess.ChessPiece;
import com.example.pikachess.chess.ChessPieceId;
import com.example.pikachess.chess.ChessSquare;
import com.example.pikachess.chess.PieceColour;

import java.util.List;

public class Bishop extends ChessPiece {

    ChessPieceId id = ChessPieceId.Bishop;
    int score = 3;

    public Bishop() {
        super();
        // set id here and id can be part of superclass
    }

    public Bishop(Bishop chessPiece) {
        super(chessPiece);
        id = chessPiece.id;
    }

    public Bishop(PieceColour colour) {
        super(colour);
    }

    @Override
    public void setPieceImage(Context context) {
        if (super.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_bishop);
        }
        else if (super.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bishop);
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
        return getDiagonalMoves(chessBoard);
    }

    @Override
    public Bishop copyPiece() {
        return new Bishop(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }


}
