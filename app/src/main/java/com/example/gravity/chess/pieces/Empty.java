package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.SquareBounds;

import java.util.List;

public class Empty extends ChessPiece {

    ChessPieceId id = ChessPieceId.NoPiece;

    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

    @Override
    public void setPieceImage(Context context) {
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getLegalMoves(List<ChessSquare> allChessSquares) {
        return null;
    }

}
