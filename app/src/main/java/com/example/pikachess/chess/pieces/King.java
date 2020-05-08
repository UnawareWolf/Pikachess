package com.example.pikachess.chess.pieces;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.pikachess.R;
import com.example.pikachess.chess.Board;
import com.example.pikachess.chess.ChessPiece;
import com.example.pikachess.chess.ChessPieceId;
import com.example.pikachess.chess.ChessSquare;
import com.example.pikachess.chess.PieceColour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class King extends ChessPiece {

    ChessPieceId id = ChessPieceId.King;
    int score = 5;


    public King() {
        super();
    }

    public King(King chessPiece) {
        super(chessPiece);
        id = chessPiece.id;

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
    public List<ChessSquare> getPieceSpecificLegalMoves(Board chessBoard) {
        List<ChessSquare> legalMoves = getPieceSpecificAttackingMoves(chessBoard);
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        if (!hasMoved && !chessBoard.isSquareUnderAttack(this.parentSquare)) {
            // I think there will be a bug where the opponent pawn can block castling with its non taking move.
            // Don't know if there is a case where this matters because if its non taking move blocks castling,
            // so will its taking move.
            LinkedList<ChessSquare> horizontalSquares = chessBoard.getPieceRows().get(yCoordinate);
            List<ChessSquare> rightSquares = horizontalSquares.subList(xCoordinate, horizontalSquares.size());
            List<ChessSquare> leftSquares = new ArrayList<>(horizontalSquares.subList(0, xCoordinate - 1));
            Collections.reverse(leftSquares);

            for (ChessSquare rightSquare : rightSquares) {
                //List<ChessSquare> attackingSquares = chessBoard.getAttackingSquares(rightSquare);
                if ((rightSquares.indexOf(rightSquare) == 0 || rightSquares.indexOf(rightSquare) == 1) && moveLeavesSelfInCheck(rightSquare, chessBoard)) {
                    break;
                }
                if (rightSquare.getPiece().getId() == ChessPieceId.Rook && !rightSquare.getPiece().getHasMoved()) {
                    legalMoves.add(rightSquares.get(rightSquares.size() - 2));
                }
            }

            for (ChessSquare leftSquare : leftSquares) {
                if ((leftSquares.indexOf(leftSquare) == 0 || leftSquares.indexOf(leftSquare) == 1) && moveLeavesSelfInCheck(leftSquare, chessBoard)) {
                    break;
                }
                if (leftSquare.getPiece().getId() == ChessPieceId.Rook && !leftSquare.getPiece().getHasMoved()) {
                    legalMoves.add(leftSquares.get(leftSquares.size() - 3));
                }
            }

        }

        return legalMoves;
    }

    @Override
    public List<ChessSquare> getPieceSpecificAttackingMoves(Board chessBoard) {
        List<ChessSquare> legalMoves = new ArrayList<>();
        int xCoordinate = this.getParentSquare().getXCoordinate();
        int yCoordinate = this.getParentSquare().getYCoordinate();
        for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
            int targetX = chessSquare.getXCoordinate();
            int targetY = chessSquare.getYCoordinate();
            if (chessSquare.getPiece().getId() != ChessPieceId.King && howFarToTheRight(xCoordinate, targetX) <= 1 && howFarToTheRight(xCoordinate, targetX) >= - 1 && howFarInFront(yCoordinate, targetY) <= 1 && howFarInFront(yCoordinate, targetY) >= -1) {
                legalMoves.add(chessSquare);
            }
        }
        return legalMoves;
    }

    @Override
    public ChessPiece copyPiece() {
        return new King(this);
    }

    @Override
    public int getPieceScore() {
        return this.score;
    }
}
