package com.example.gravity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

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

    public ChessView(Context context) {
        super(context);

        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        mRect.set(1000, 1000, 1000, 1000);
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

                squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                squareBoardLocation = getSquareBoardLocation(i, j);
                mSquare = new ChessSquare(squareBounds, ChessPieceId.NoPiece, squareBoardLocation);
                allChessSquares.add(mSquare);
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

        if (touched) {
            mRect.set(selectedSquare.getBoundary().getLeft(), selectedSquare.getBoundary().getTop(), selectedSquare.getBoundary().getRight(), selectedSquare.getBoundary().getBottom());
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
            canvas.drawRect(mRect, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : ChessView.allChessSquares) {
                if (chessSquare.getBoundary().chessSquareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    selectedSquare = chessSquare;
                    touched = true;
                    invalidate();
                }
            }
        }
        return true;
    }

    private String getSquareBoardLocation(int xNum, int yNum) {
        String loc;
        switch(xNum) {
            case 0:
                loc = "a" + Integer.toString(yNum + 1);
                break;
            case 1:
                loc = "b" + Integer.toString(yNum + 1);
                break;
            case 2:
                loc = "c" + Integer.toString(yNum + 1);
                break;
            case 3:
                loc = "d" + Integer.toString(yNum + 1);
                break;
            case 4:
                loc = "e" + Integer.toString(yNum + 1);
                break;
            case 5:
                loc = "f" + Integer.toString(yNum + 1);
                break;
            case 6:
                loc = "g" + Integer.toString(yNum + 1);
                break;
            case 7:
                loc = "h" + Integer.toString(yNum + 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + xNum);
        }
        return loc;
    }
}
