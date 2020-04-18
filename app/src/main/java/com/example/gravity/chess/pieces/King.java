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
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        for (ChessSquare chessSquare : allChessSquares) {
            int targetX = chessSquare.getXCoordinate();
            int targetY = chessSquare.getYCoordinate();
            if (chessSquare.getPiece().getId() != ChessPieceId.King && howFarToTheRight(xCoordinate, targetX) <= 1 && howFarToTheRight(xCoordinate, targetX) >= - 1 && howFarInFront(yCoordinate, targetY) <= 1 && howFarInFront(yCoordinate, targetY) >= -1) {
                legalMoves.add(chessSquare);
            }
        }
        return legalMoves;
    }
}
