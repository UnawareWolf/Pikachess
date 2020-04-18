package com.example.gravity.chess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gravity.R;
import com.example.gravity.chess.ChessPiece;
import com.example.gravity.chess.ChessPieceId;
import com.example.gravity.chess.ChessSquare;
import com.example.gravity.chess.ChessView;
import com.example.gravity.chess.PieceColour;
import com.example.gravity.chess.SquareBounds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rook extends ChessPiece {

    ChessPieceId id = ChessPieceId.Rook;

    @Override
    public List<ChessSquare> getLegalMoves() {
        return null;
    }

    @Override
    public void setPieceImage(Context context) {
        if (this.colour == PieceColour.White) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_rook);
        }
        else if (this.colour == PieceColour.Black) {
            this.pieceImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.rook);
        }
    }

    @Override
    public ChessPieceId getId() {
        return this.id;
    }

    @Override
    public List<ChessSquare> getLegalMoves(List<ChessSquare> allChessSquares) {

        List<ChessSquare> legalMoves = new ArrayList<>();
        //Map<Character, ChessSquare> horizontalSquares = this.getParentSquare().getHorizontalSquares();
        //Map<Character, ChessSquare> verticalSquares = this.getParentSquare().getHorizontalSquares();
        legalMoves.addAll(getOpenSquares());

        return legalMoves;
    }

    private List<ChessSquare> getOpenSquares() {
//        char horizontalLetter = this.getParentSquare().getBoardLocation().charAt(0);
//        int horizontalPosition = ChessView.getLetterIndex(this.getParentSquare().getBoardLocation().charAt(0));
//        int verticalPosition = this.getParentSquare().getBoardLocation().charAt(1);
        List<ChessSquare> openSquares = new ArrayList<>();
//        LinkedList<ChessSquare> verticalSquares = ChessView.boardGroups.get(horizontalLetter);
//        List<ChessSquare> forwardSquares = verticalSquares.subList(verticalPosition, verticalSquares.size());
//        for (ChessSquare forwardSquare : forwardSquares) {
//            if (forwardSquare.getPiece().getId() == ChessPieceId.NoPiece) {
//                openSquares.add(forwardSquare);
//            }
//            else if (forwardSquare.getPiece().getId() != ChessPieceId.NoPiece) {
//                openSquares.add(forwardSquare);
//                break;
//            }
//        }

//        for (int horizontalDestination = horizontalPosition + 1; horizontalDestination < 'i'; horizontalDestination++){
//            ChessSquare destinationSquare = horizontalSquares.get((char) horizontalDestination);
//            if (destinationSquare.getPiece().getId() == ChessPieceId.NoPiece) {
//                openSquares.add(destinationSquare);
//            }
//            else if (destinationSquare.getPiece().getId() != ChessPieceId.NoPiece) {
//                openSquares.add(destinationSquare);
//                break;
//            }
//        }
        //a=97, h=104;
        return openSquares;
    }


}
