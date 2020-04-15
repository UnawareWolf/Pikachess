package com.example.gravity.chess;

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
    private Canvas canvas;
    int boardSize;
    private Context context;// only make variables private if they will be get or set by other classes. Otherwise package private (ie nothing) is good.

    public ChessView(Context context) {
        super(context);
        this.context = context;
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (newBoard) {
            this.canvas = canvas;
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            this.allChessSquares = assembleSquares();
            initialisePieceBitmaps();
            resizePieceBitmaps();
            newBoard = false;
        }
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        drawBoardBorder();
        drawBoard(allChessSquares);

        if (touched) {
            highlightChessSquare(selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(secondSelectedSquare);
            greyedOut = false;
        }

        drawConfirmMoveButton();

        drawAllChessPieces(allChessSquares);

    }

    private void resizePieceBitmaps() {
        for (ChessSquare chessSquare : allChessSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().resizePieceImage(squareSize); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    private void drawBoardBorder() {
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
                squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                squareBoardLocation = getSquareBoardLocation(i, j);
                ChessPiece piece = getChessPieceFromBoardLocation(squareBoardLocation);
                mSquare = new ChessSquare(squareBounds, piece, squareBoardLocation);
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

    private void drawBoard(List<ChessSquare> allChessSquares) {
        for (ChessSquare chessSquare : allChessSquares) {
            chessSquare.drawSquare(context, canvas, mRect, mPaint);
        }
    }

    private void drawAllChessPieces(List<ChessSquare> chessSquares) {
        for (ChessSquare chessSquare : chessSquares){
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece){
                chessSquare.getPiece().drawPiece(canvas);
            }
        }
    }

    private void initialisePieceBitmaps() {
        for (ChessSquare chessSquare : allChessSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().setPieceImage(context); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    private void drawConfirmMoveButton() {
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

    private void highlightChessSquare(ChessSquare squareToHighlight) {
        mRect.set(squareToHighlight.getBoundary().getLeft(), squareToHighlight.getBoundary().getTop(), squareToHighlight.getBoundary().getRight(), squareToHighlight.getBoundary().getBottom());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
        canvas.drawRect(mRect, mPaint);
    }

    private ChessPiece getChessPieceFromBoardLocation(String squareBoardLocation) {
        ChessPiece piece = null;
        if (squareBoardLocation.charAt(1) == '2' || squareBoardLocation.charAt(1) == '7') {
            piece = new Pawn();
        }
        else if(squareBoardLocation.charAt(1) == '1' || squareBoardLocation.charAt(1) == '8') {
            if (squareBoardLocation.charAt(0) == 'a' || squareBoardLocation.charAt(0) == 'h') {
                piece = new Rook();
            }
            if (squareBoardLocation.charAt(0) == 'b' || squareBoardLocation.charAt(0) == 'g') {
                piece = new Knight();
            }
            if (squareBoardLocation.charAt(0) == 'c' || squareBoardLocation.charAt(0) == 'f') {
                piece = new Bishop();
            }
            if (squareBoardLocation.charAt(0) == 'd') {
                piece = new Queen();
            }
            if (squareBoardLocation.charAt(0) == 'e') {
                piece = new King();
            }
        }
        else {
            piece = new Empty();
        }
        if (squareBoardLocation.charAt(1) == '1' || squareBoardLocation.charAt(1) == '2'){
            assert piece != null;
            piece.setColour(PieceColour.White);
        }
        else if (squareBoardLocation.charAt(1) == '7' || squareBoardLocation.charAt(1) == '8') {
            assert piece != null;
            piece.setColour(PieceColour.White);
        }
        return piece;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : allChessSquares) {
                if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                        selectedSquare = chessSquare;
                        secondTouched = false;
                        touched = true;
                    }
                    if(touched && chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                        secondSelectedSquare = chessSquare;
                        secondTouched = true;
                    }
                    invalidate();
                }
            }
            if(secondTouched && confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                secondSelectedSquare.setPiece(selectedSquare.getPiece());
                selectedSquare.setPiece(new Empty());
                touched = false;
                secondTouched = false;
                greyedOut = true;
                invalidate();
            }
        }

        return true;
    }

    private String getSquareBoardLocation(int xNum, int yNum) {
        String loc;
        switch(xNum) {
            case 0:
                loc = "a" + Integer.toString(8 - yNum);
                break;
            case 1:
                loc = "b" + Integer.toString(8 - yNum);
                break;
            case 2:
                loc = "c" + Integer.toString(8 - yNum);
                break;
            case 3:
                loc = "d" + Integer.toString(8 - yNum);
                break;
            case 4:
                loc = "e" + Integer.toString(8 - yNum);
                break;
            case 5:
                loc = "f" + Integer.toString(8 - yNum);
                break;
            case 6:
                loc = "g" + Integer.toString(8 - yNum);
                break;
            case 7:
                loc = "h" + Integer.toString(8 - yNum);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + xNum);
        }
        return loc;
    }
}
