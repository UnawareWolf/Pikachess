package com.example.gravity.chess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.gravity.R;
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

public class ChessView extends View {

    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Bitmap totodileBackground;
    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;
    private SquareBounds squareBounds = new SquareBounds();
    private ChessSquare mSquare;
    private List<ChessSquare> allChessSquares = new ArrayList<>(); // dont think this should be static. pretty sure
    private String squareBoardLocation;
    boolean touched = false;
    float xTouch, yTouch;
    private Rect selectRect = new Rect();
    private ChessSquare selectedSquare;
    boolean secondTouched = false;
    private ChessSquare secondSelectedSquare;
    boolean greyedOut = true;
    private SquareBounds confirmMoveButtonBounds = new SquareBounds();
    boolean newBoard = true;
    int canvasWidth;
    int squareSize;
    //private Canvas canvas;
    int boardSize;
    private Context context;// only make variables private if they will be get or set by other classes. Otherwise package private (ie nothing) is good. wrong
    private PieceColour turnToPlay = PieceColour.White;
    public static Map<Integer, LinkedList<ChessSquare>> pieceColumns = new HashMap<>();
    public static Map<Integer, LinkedList<ChessSquare>> pieceRows = new HashMap<>();
    public static Map<Integer, LinkedList<ChessSquare>> pieceUpDiagonals = new HashMap<>();
    public static Map<Integer, LinkedList<ChessSquare>> pieceDownDiagonals = new HashMap<>();
    //public static Map<Character, Integer> letterIndexMap = new HashMap<>();

    public ChessView(Context context) {
        super(context);
        this.context = context;
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
        //fillLetterIndexMap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (newBoard) {
            //this.canvas = canvas;
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            this.allChessSquares = assembleSquares();
            assembleBoardGroups();
            initialisePieceBitmaps();
            resizePieceBitmaps();
            newBoard = false;
        }
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        drawBoardBorder(canvas);
        drawBoard(canvas, allChessSquares);

        if (touched) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyedOut = false;
        }

        drawConfirmMoveButton(canvas);
        /*Pawn pawn = new Pawn();
        ChessPieceId id = pawn.getId();*/

        drawAllChessPieces(allChessSquares, canvas);

    }

    private void resizePieceBitmaps() {
        for (ChessSquare chessSquare : allChessSquares) {
            ChessPiece newPiece = chessSquare.getPiece();
            ChessPieceId newID = chessSquare.getPiece().getId();
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().resizePieceImage(squareSize); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    private void drawBoardBorder(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
    }

    private List<ChessSquare> assembleSquares() {
        List<ChessSquare> boardSquares = new ArrayList<>();
        boolean fillSquare = true;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                int xCoordinate = i + 1;
                int yCoordinate = 8 - j;
                squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                //squareBoardLocation = getSquareBoardLocation(i, j);
                ChessPiece piece = getPieceFromCoordinates(xCoordinate, yCoordinate);
                //ChessPiece piece = getChessPieceFromBoardLocation(squareBoardLocation);
                mSquare = new ChessSquare(squareBounds, piece, xCoordinate, yCoordinate);
                if (fillSquare){
                    mSquare.setColour(PieceColour.Black);
                } else {
                    mSquare.setColour(PieceColour.White);
                }
                boardSquares.add(mSquare);
                fillSquare = !fillSquare;
            }
            fillSquare = !fillSquare;
        }
        return boardSquares;
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
            for (ChessSquare chessSquare : allChessSquares) {
                if (chessSquare.getXCoordinate() == num) {
                    verticalBoardGroup.add(chessSquare);
                }
                if (chessSquare.getYCoordinate() == num) {
                    horizontalBoardGroup.add(chessSquare);
                }
            }

//            for (ChessSquare chessSquare : allChessSquares) {
//                if (chessSquare.getYCoordinate() == num) {
//                    horizontalBoardGroup.add(chessSquare);
//                }
//            }
            Collections.reverse(verticalBoardGroup);

//            for (ChessSquare chessSquare : allChessSquares) {
//                if (chessSquare.getYCoordinate() == num && chessSquare.getXCoordinate() == num) {
//                    diagonalUpBoardGroup.add(chessSquare);
//                }
//            }
//
//            for (ChessSquare chessSquare : allChessSquares) {
//                if (chessSquare.getYCoordinate() == num && chessSquare.getXCoordinate() == (9 - chessSquare.getYCoordinate())) {
//                    diagonalUpBoardGroup.add(chessSquare);
//                }
//            }

            pieceColumns.put(num, verticalBoardGroup);
            pieceRows.put(num, horizontalBoardGroup);
        }
        for (int i = 1; i < 16; i++) {

            LinkedList<ChessSquare> diagonalUpBoardGroup = new LinkedList<>();
            LinkedList<ChessSquare> diagonalDownBoardGroup = new LinkedList<>();
            for (ChessSquare chessSquare : allChessSquares) {
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

    private void drawBoard(Canvas canvas, List<ChessSquare> allChessSquares) {
        for (ChessSquare chessSquare : allChessSquares) {
            chessSquare.drawSquare(context, canvas, mRect, mPaint);
        }
    }

    private void drawAllChessPieces(List<ChessSquare> chessSquares, Canvas canvas) {
        for (ChessSquare chessSquare : chessSquares){
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece){
                chessSquare.getPiece().drawPiece(canvas, chessSquare.getBoundary()); // maybe actually set location in piece
            }
        }
    }

    private void initialisePieceBitmaps() {
        for (ChessSquare chessSquare : allChessSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().setPieceImage(context); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
                int x = 0;
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    private void drawConfirmMoveButton(Canvas canvas) {
        int buttonWidth = canvasWidth/4;
        int buttonHeight = buttonWidth/3;
        int buttonLeft = OFFSET + BORDER_WIDTH/2;
        int buttonTop = canvasWidth - BORDER_WIDTH/2;
        int buttonRight = buttonLeft + buttonWidth;
        int buttonBottom = buttonTop + buttonHeight;
        confirmMoveButtonBounds.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mRect.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mPaint.setColor(getResources().getColor(R.color.buttonGrey));
        if (greyedOut) {
            mPaint.setAlpha(40);
        }
        else {
            mPaint.setAlpha(255);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BORDER_WIDTH);
        canvas.drawRect(mRect, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80 - 2*BORDER_WIDTH);
        canvas.drawText("Confirm", buttonLeft + BORDER_WIDTH, buttonBottom - 2*BORDER_WIDTH, mPaint);
    }

    private void highlightChessSquare(Canvas canvas, ChessSquare squareToHighlight) {
        mRect.set(squareToHighlight.getBoundary().getLeft(), squareToHighlight.getBoundary().getTop(), squareToHighlight.getBoundary().getRight(), squareToHighlight.getBoundary().getBottom());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
        canvas.drawRect(mRect, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : allChessSquares) {
                if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece && chessSquare.getPiece().getColour() == turnToPlay) {
                        selectedSquare = chessSquare;
                        secondTouched = false;
                        touched = true;
                        greyedOut = true;
                    }
                    if(touched) {
                        selectedSquare.getPiece().setParentSquare(selectedSquare);
                        boolean legalMove = false;
                        for (ChessSquare legalSquare : selectedSquare.getPiece().getLegalMoves(allChessSquares)) {
                            if (chessSquare == legalSquare) {
                                legalMove = true;
                                break;
                            }
                        }
                        if (legalMove) {
                            secondSelectedSquare = chessSquare;
                            secondTouched = true;
                        }
                    }
                    invalidate();
                }
            }
            if(secondTouched && confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                selectedSquare.getPiece().setHasMoved();
                secondSelectedSquare.setPiece(selectedSquare.getPiece());
                secondSelectedSquare.getPiece().setParentSquare(secondSelectedSquare);
                selectedSquare.setPiece(new Empty());
                changeTurn();
                touched = false;
                secondTouched = false;
                greyedOut = true;
                invalidate();
            }
        }

        return true;
    }
    private void changeTurn() {
        if (this.turnToPlay == PieceColour.Black){
            this.turnToPlay = PieceColour.White;
        }
        else {
            this.turnToPlay = PieceColour.Black;
        }
    }


}
