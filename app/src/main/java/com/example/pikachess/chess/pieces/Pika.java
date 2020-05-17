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

public class Pika extends ChessPiece {

    public Pika() {
        super(PieceColour.NoColour);
        this.id = ChessPieceId.NoPiece;
        this.score = 0;
    }

    @Override
    public void setPieceImage(Context context) {
        this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.surprised_pikachu);
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getPieceSpecificLegalMoves(Board chessBoard) {
        return null;
    }

    @Override
    public List<ChessSquare> getPieceSpecificAttackingMoves(Board chessBoard) {
        return null;
    }

    @Override
    public ChessPiece copyPiece() {
        return null;
    }

    @Override
    public int getPieceScore() {
        return 0;
    }
}
