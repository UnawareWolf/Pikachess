package com.example.gravity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ChessView extends View {

    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Bitmap totodileBackground;
    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;
    private SquareBounds squareBounds;
    private ChessSquare mSquare;
    public static List<ChessSquare> allChessSquares = new ArrayList<>();
    private String squareBoardLocation;
    boolean touched = false;
    float xTouch, yTouch;
    private Rect selectRect = new Rect();
    private ChessSquare selectedSquare;
    private Bitmap pawnImage;
    boolean secondTouched = false;
    private ChessSquare secondSelectedSquare;
    boolean greyedOut = true;
    private SquareBounds confirmMoveButtonBounds = new SquareBounds();
    boolean boardSetup = true;

    public ChessView(Context context) {
        //this(context, null);
        super(context);
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
        pawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.pawn);
    }
/*
    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

 */

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        //mRect.set(1000, 1000, 1000, 1000);
        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        int width = getWidth();
        int height = getHeight();
        int boardSize = width - 2*OFFSET - 2*BORDER_WIDTH;
        int squareSize = boardSize/8;
        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, width - OFFSET - BORDER_WIDTH/2, width - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
        boolean fillSquare = true;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                if (boardSetup){
                    squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                    squareBoardLocation = getSquareBoardLocation(i, j);
                    ChessPieceId pieceId = getChessPieceIdFromBoardLocation(squareBoardLocation);
                    mSquare = new ChessSquare(squareBounds, pieceId, squareBoardLocation);
                    allChessSquares.add(mSquare);
                }
                if (fillSquare){

                    mRect.set(squareLeft, squareTop, squareRight, squareBottom);
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(getResources().getColor(R.color.chessBrown));
                    mPaint.setAlpha(140);
                    canvas.drawRect(mRect, mPaint);

                    fillSquare = false;
                } else {
                    fillSquare = true;
                }
            }
            fillSquare = !fillSquare;
        }
        boardSetup = false;
        if (touched) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyedOut = false;
            //Button confirmMove = (Button) findViewById(R.id.confirmMove);
            //confirmMove.setVisibility(View.VISIBLE);
        }

        drawConfirmMoveButton(canvas, width);

        for (ChessSquare chessSquare : ChessView.allChessSquares){
            if (chessSquare.getPiece() == ChessPieceId.BlackPawn){
                pawnImage = Bitmap.createScaledBitmap(pawnImage, squareSize, squareSize, true);
                canvas.drawBitmap(pawnImage, chessSquare.getBoundary().getLeft(), chessSquare.getBoundary().getTop(), null);

            }
        }

    }

    private void drawConfirmMoveButton(Canvas canvas, int canvasWidth) {
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
        //canvas.dr
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


    private ChessPieceId getChessPieceIdFromBoardLocation(String squareBoardLocation) {
        ChessPieceId pieceId;
        if (squareBoardLocation.charAt(1) == '2') {
            pieceId = ChessPieceId.WhitePawn;
        }
        else if(squareBoardLocation.charAt(1) == '7') {
            pieceId = ChessPieceId.BlackPawn;
        }
        else {
            pieceId = ChessPieceId.NoPiece;
        }
        /*switch(squareBoardLocation) {
            //case
        }
        */
        return pieceId;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : ChessView.allChessSquares) {
                if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    if (chessSquare.getPiece() != ChessPieceId.NoPiece) {
                        selectedSquare = chessSquare;
                        secondTouched = false;
                        touched = true;
                    }
                    if(touched && chessSquare.getPiece() == ChessPieceId.NoPiece) {
                        secondSelectedSquare = chessSquare;
                        secondTouched = true;
                    }
                    invalidate();
                }
            }
            if(confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                secondSelectedSquare.setPiece(selectedSquare.getPiece());
                selectedSquare.setPiece(ChessPieceId.NoPiece);
                touched = false;
                secondTouched = false;
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
