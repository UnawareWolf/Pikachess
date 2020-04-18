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

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    ChessPieceId id = ChessPieceId.Knight;

    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_knight);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.knight);
        }
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getLegalMoves(List<ChessSquare> allChessSquares) {
        List<ChessSquare> legalMoves = new ArrayList<>();
        for (ChessSquare chessSquare : allChessSquares) {

        }
        return null;
    }

    public List<ChessSquare> getHorizontalSquares(List<ChessSquare> allChessSquares, ChessSquare currentSquare) {
        List<ChessSquare> horizontalSquares = new ArrayList<>();
//        int positionNumber = currentSquare.getBoardLocation().charAt(1);
//        char currentLetter = currentSquare.getBoardLocation().charAt(0);
//        for (ChessSquare chessSquare : allChessSquares) {
//            if (positionNumber == chessSquare.getBoardLocation().charAt(1)) {
//
//            }
//        }
        return horizontalSquares;
    }


}
