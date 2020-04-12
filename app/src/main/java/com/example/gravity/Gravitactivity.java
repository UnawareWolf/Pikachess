package com.example.gravity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Gravitactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ChessView(this));
    }

    public void highlightSelectedSquare(ChessView chessView) {
        int hi = ChessView.OFFSET;

    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            float xTouch = event.getX();
            float yTouch = event.getY();
            for (ChessSquare chessSquare : ChessView.allChessSquares) {
                SquareBounds test = chessSquare.getBoundary();
                if (chessSquare.getBoundary().chessSquareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    //Rect rectChessHighlight = new Rect();
                    Rect rectChessHighlight = new Rect();
                    rectChessHighlight.set(chessSquare.getBoundary().getLeft(), chessSquare.getBoundary().getTop(), chessSquare.getBoundary().getRight(), chessSquare.getBoundary().getBottom());
                    Paint highlightPaint = new Paint();
                    highlightPaint.setStyle(Paint.Style.FILL);
                    highlightPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
                    //highlightPaint.setAlpha(40);

                    Canvas highlightCanvas = new Canvas();
                    highlightCanvas.drawRect(rectChessHighlight, highlightPaint);
                }
            }
        }
        return true;
    }

     */

    public void drawSomething(View view) {

        view.invalidate();
    }
}
