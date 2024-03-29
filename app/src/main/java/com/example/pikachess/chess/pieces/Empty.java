package com.example.pikachess.chess.pieces;

import android.content.Context;

import com.example.pikachess.chess.Board;
import com.example.pikachess.chess.ChessPiece;
import com.example.pikachess.chess.ChessPieceId;
import com.example.pikachess.chess.ChessSquare;
import com.example.pikachess.chess.PieceColour;

import java.util.List;

public class Empty extends ChessPiece {
    public Empty() {
        super(PieceColour.NoColour);
        this.id = ChessPieceId.NoPiece;
        this.score = 0;
    }

    public Empty(Empty chessPiece) {
        super(chessPiece);
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
