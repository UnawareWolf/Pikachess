package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gravity.R;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.SquareBounds;

import java.util.List;

public class Rook extends ChessPiece {

    public static final ChessPieceId ID = ChessPieceId.Rook;
    private Bitmap pieceImage;

    @Override
    public void drawPiece() {

    }

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
        this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn);
    }

    @Override
    public Bitmap getPieceImage() {
        return this.pieceImage;
    }
}
