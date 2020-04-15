package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gravity.R;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.PieceColour;
import com.example.gravity.chess.SquareBounds;

import java.util.List;

public class King extends ChessPiece {

    @Override
    public SquareBounds getLocation() {
        return null;
    }

    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

    @Override
    public void movePiece() {

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
    public Bitmap getPieceImage() {
        return null;
    }

    @Override
    public ChessPieceId getId() {
        return null;
    }
}
