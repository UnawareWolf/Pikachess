package com.example.gravity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class ChessView extends View {

    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Bitmap totodileBackground;
    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;

    public ChessView(Context context) {
        super(context);
        mRect.set(1000, 1000, 1000, 1000);
        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        int width = getWidth();
        int height = getHeight();
        int boardSize = width - 2*OFFSET - 2*BORDER_WIDTH;
        int squareSize = boardSize/8;
        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, width - OFFSET - BORDER_WIDTH/2, width - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
        boolean fillSquare = true;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                if (fillSquare){
                    int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                    int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                    int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                    int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                    mRect.set(squareLeft, squareTop, squareRight, squareBottom);
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(getResources().getColor(R.color.chessBrown));
                    mPaint.setAlpha(140);
                    //mPaint.setARGB(100, 0, 0, 0);
                    canvas.drawRect(mRect, mPaint);
                    fillSquare = false;
                } else {
                    fillSquare = true;
                }
            }
            fillSquare = !fillSquare;
        }
    }

    //private
}
