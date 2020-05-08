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

public class Queen extends ChessPiece {

    ChessPieceId id = ChessPieceId.Queen;
    int score = 8;

    public Queen() {
        super();
    }

    public Queen(PieceColour colour) {
        super(colour);
    }

    public Queen(Queen chessPiece) {
        super(chessPiece);
        id = chessPiece.id;
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_queen);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.queen);
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
        legalMoves.addAll(getStraightLineMoves(chessBoard));
        legalMoves.addAll(getDiagonalMoves(chessBoard));
        return legalMoves;
    }

    @Override
    public ChessPiece copyPiece() {
        return new Queen(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }

}