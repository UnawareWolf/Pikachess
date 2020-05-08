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

public class Rook extends ChessPiece {

    ChessPieceId id = ChessPieceId.Rook;
    int score = 5;

    public Rook() {
        super();
    }

    public Rook(Rook chessPiece) {
        super(chessPiece);
        id = chessPiece.id;
    }

    public Rook(PieceColour colour) {
        super(colour);
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_rook);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.rook);
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
        return getStraightLineMoves(chessBoard);
    }

    @Override
    public ChessPiece copyPiece() {
        return new Rook(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }
}
