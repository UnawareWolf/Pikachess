package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.gravity.chess.Board;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.PieceColour;
import com.example.gravity.chess.SquareBounds;

import java.util.List;

public class Empty extends ChessPiece {

    ChessPieceId id = ChessPieceId.NoPiece;
    int score = 0;

    public Empty() {
        super();
        colour = PieceColour.NoColour;
    }

    public Empty(Empty chessPiece) {
        super(chessPiece);
        id = chessPiece.id;
    }

    @Override
    public void setPieceImage(Context context) {
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
        return new Empty(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }

}
