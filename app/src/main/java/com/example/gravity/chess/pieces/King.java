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

public class King extends ChessPiece {

    ChessPieceId id = ChessPieceId.King;

    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_king);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.king);
        }
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getLegalMoves(List<ChessSquare> allChessSquares) {
        List<ChessSquare> legalMoves = new ArrayList<>();
//        int positionNumber = this.getParentSquare().getBoardLocation().charAt(1);
//        char currentLetter = this.getParentSquare().getBoardLocation().charAt(0);
//        for (ChessSquare chessSquare : allChessSquares) {
//            int targetNumber = chessSquare.getBoardLocation().charAt(1);
//            char targetLetter = chessSquare.getBoardLocation().charAt(0);
//            if (chessSquare.getPiece().getId() != ChessPieceId.King && howFarToTheRight(currentLetter, targetLetter) <= 1 && howFarToTheRight(currentLetter, targetLetter) >= - 1 && howFarInFront(positionNumber, targetNumber) <= 1 && howFarInFront(positionNumber, targetNumber) >= -1) {
//                legalMoves.add(chessSquare);
//            }
//        }
//
        return legalMoves;
    }
}
