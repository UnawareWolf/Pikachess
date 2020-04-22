package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.gravity.chess.pieces.Bishop;
import com.example.gravity.chess.pieces.Empty;
import com.example.gravity.chess.pieces.King;
import com.example.gravity.chess.pieces.Knight;
import com.example.gravity.chess.pieces.Pawn;
import com.example.gravity.chess.pieces.Queen;
import com.example.gravity.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.gravity.chess.ChessView.BORDER_WIDTH;
import static com.example.gravity.chess.ChessView.OFFSET;

public class Board {

    private List<ChessSquare> boardSquares = new ArrayList<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceColumns = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceRows = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceUpDiagonals = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceDownDiagonals = new HashMap<>();
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    //private ChessSquare[][] boardArray = new ChessSquare[8][8];
    private int squareSize;
    private LinkedList<ChessMove> chessMoves = new LinkedList<>();

    public Board(ChessView chessView) {
        this.squareSize = chessView.getSquareSize();
        assembleBoard();
        assembleBoardGroups();
    }

    public Board(Board board) {
        for (ChessSquare chessSquare : board.getBoardSquares()) {
            boardSquares.add(new ChessSquare(chessSquare));
        }
        assembleBoardGroups();
        mRect = board.mRect;
        mPaint = board.mPaint;
        squareSize = board.squareSize;
    }
    //public

    private void assembleBoard() {
        boolean fillSquare = false;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                int xCoordinate = i + 1;
                int yCoordinate = 8 - j;
                SquareBounds squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                ChessPiece piece = getPieceFromCoordinates(xCoordinate, yCoordinate);
                ChessSquare chessSquare = new ChessSquare(squareBounds, piece, xCoordinate, yCoordinate);
                if (fillSquare){
                    chessSquare.setColour(PieceColour.Black);
                } else {
                    chessSquare.setColour(PieceColour.White);
                }
                boardSquares.add(chessSquare);
                fillSquare = !fillSquare;
            }
            fillSquare = !fillSquare;
        }
    }

    private ChessPiece getPieceFromCoordinates(int xCoordinate, int yCoordinate) {
        ChessPiece piece = null;
        if (yCoordinate == 2 || yCoordinate == 7) {
            piece = new Pawn();
        }
        else if(yCoordinate == 1 || yCoordinate == 8) {
            switch (xCoordinate) {
                case 1:
                case 8:
                    piece = new Rook();
                    break;
                case 2:
                case 7:
                    piece = new Knight();
                    break;
                case 3:
                case 6:
                    piece = new Bishop();
                    break;
                case 4: piece = new Queen();
                    break;
                case 5: piece = new King();
                    break;
            }
        }
        else {
            piece = new Empty();
        }
        if (yCoordinate == 1 || yCoordinate == 2){
            piece.setColour(PieceColour.White);
        }
        else if (yCoordinate == 7 || yCoordinate == 8) {
            piece.setColour(PieceColour.Black);
        }
        return piece;
    }

    private void assembleBoardGroups() {
        for (int num = 1; num < 9; num++) {
            LinkedList<ChessSquare> verticalBoardGroup = new LinkedList<>();
            LinkedList<ChessSquare> horizontalBoardGroup = new LinkedList<>();
            for (ChessSquare chessSquare : boardSquares) {
                if (chessSquare.getXCoordinate() == num) {
                    verticalBoardGroup.add(chessSquare);
                }
                if (chessSquare.getYCoordinate() == num) {
                    horizontalBoardGroup.add(chessSquare);
                }
            }
            Collections.reverse(verticalBoardGroup);
            pieceColumns.put(num, verticalBoardGroup);
            pieceRows.put(num, horizontalBoardGroup);
        }
        for (int i = 1; i < 16; i++) {

            LinkedList<ChessSquare> diagonalUpBoardGroup = new LinkedList<>();
            LinkedList<ChessSquare> diagonalDownBoardGroup = new LinkedList<>();
            for (ChessSquare chessSquare : boardSquares) {
                int xCoordinate = chessSquare.getXCoordinate();
                int yCoordinate = chessSquare.getYCoordinate();
                if (xCoordinate + yCoordinate == i + 1) {
                    diagonalDownBoardGroup.add(chessSquare);
                }
                if ((9 - xCoordinate) + yCoordinate == i + 1) {
                    diagonalUpBoardGroup.add(chessSquare);
                }
            }
            Collections.reverse(diagonalDownBoardGroup);
            pieceUpDiagonals.put(i, diagonalUpBoardGroup);
            pieceDownDiagonals.put(i, diagonalDownBoardGroup);
        }
    }
    public void drawBoard(Context context, Canvas canvas) {
        for (ChessSquare chessSquare : boardSquares) {
            chessSquare.drawSquare(context, canvas, mRect, mPaint);
        }
    }

    public List<ChessSquare> getBoardSquares() {
        return this.boardSquares;
    }

    public void initialisePieceBitmaps(Context context) {
        for (ChessSquare chessSquare : boardSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().setPieceImage(context); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
                int x = 0;
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    public void resizePieceBitmaps() {
        for (ChessSquare chessSquare : boardSquares) {
            ChessPiece newPiece = chessSquare.getPiece();
            ChessPieceId newID = chessSquare.getPiece().getId();
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().resizePieceImage(squareSize); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceColumns() {
        return this.pieceColumns;
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceRows() {
        return this.pieceRows;
    }
    public Map<Integer, LinkedList<ChessSquare>> getPieceUpDiagonals() {
        return this.pieceUpDiagonals;
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceDownDiagonals() {
        return this.pieceDownDiagonals;
    }

    public List<ChessSquare> getSquaresUnderAttack() {
        List<ChessSquare> squaresUnderAttack = new ArrayList<>(); // refactor into multiple methods for readability
        for (ChessSquare chessSquare : boardSquares) {
            try {
                chessSquare.getPiece().setParentSquare(chessSquare); // this isn't ideal
                List<ChessSquare> possibleMoves = chessSquare.getPiece().getPieceSpecificLegalMoves(this);
                if (possibleMoves != null) {
                    for (ChessSquare possibleMove : possibleMoves) {
                        if (possibleMove.getPiece().getId() != ChessPieceId.NoPiece && possibleMove.getPiece().getColour() != chessSquare.getPiece().getColour()) {
                            squaresUnderAttack.add(possibleMove);
                        }
                    }
                }
            }
            catch(Exception e) {
                System.out.println();
            }


        }
        return squaresUnderAttack;
    }

    public void storeMove(ChessMove chessMove) {
        this.chessMoves.add(chessMove);
    }

    public ChessMove getLastMove() {
        if (chessMoves.size() != 0) {
            return this.chessMoves.getLast();
        }
        else {
            return null;
        }
    }

    public LinkedList<ChessMove> getAllMoves() {
        return this.chessMoves;
    }

    public void removePawnIfEnPassant() {
        ChessMove lastMove = getLastMove();
        if (lastMove == null) {
            return;
        }
        if (lastMove.getPieceIdMoved() == ChessPieceId.Pawn && lastMove.getPieceIdTaken() == ChessPieceId.NoPiece && lastMove.getAbsXDistanceMoved() == 1) {
            for (ChessSquare chessSquare : this.boardSquares) {
                if (chessSquare.getXCoordinate() == lastMove.getSquareTo().getXCoordinate()) {
                    if (chessSquare.getYCoordinate() == lastMove.getSquareFrom().getYCoordinate()) {
                        chessSquare.setPiece(new Empty());
                    }
                }
            }
        }
    }


//    public void drawBoard(Canvas canvas) {
//    }
}
