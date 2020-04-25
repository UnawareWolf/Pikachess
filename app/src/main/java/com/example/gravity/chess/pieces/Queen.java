package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gravity.R;
import com.example.gravity.chess.Board;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.ChessView;
import com.example.gravity.chess.PieceColour;
import com.example.gravity.chess.SquareBounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queen extends ChessPiece {

    ChessPieceId id = ChessPieceId.Queen;

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

}