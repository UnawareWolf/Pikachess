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

public class Pawn extends ChessPiece {

    ChessPieceId id = ChessPieceId.Pawn;

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_pawn);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn);
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
//            if (chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
//                if (targetLetter == currentLetter && howFarInFront(positionNumber, targetNumber) == 1) {
//                    legalMoves.add(chessSquare);
//                }
//                else if (!this.hasMoved && howFarInFront(positionNumber, targetNumber) == 2 && targetLetter == currentLetter) {
//                    legalMoves.add(chessSquare);
//                }
//            }
//            else if (chessSquare.getPiece().getId() != ChessPieceId.King) {
//                if (howFarInFront(positionNumber, targetNumber) == 1 && (howFarToTheRight(currentLetter, targetLetter) == -1 || howFarToTheRight(currentLetter, targetLetter) == 1)) {
//                    legalMoves.add(chessSquare);
//                }
//            }
//        }

        return legalMoves;
    }

//    private int howFarToTheRight(char positionLetter, char targetLetter) {
//        return positionLetter - targetLetter;
//    }


//    private int howFarInFront(int positionNumber, int targetNumber) {
//        int relativeRow;
//        if (this.colour == PieceColour.White){
//            relativeRow = targetNumber - positionNumber;
//        }
//        else {
//            relativeRow = positionNumber - targetNumber;
//        }
//        return relativeRow;
//    }

//    private char getNextChar(char letter) {
//        int charValue = letter;
//        char nextLetter = (char) (charValue + 1);
//        return nextLetter;
//    }



    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

}
