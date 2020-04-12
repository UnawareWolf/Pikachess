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
    private Bitmap bmp;
    private int width;
    private int height;

    public ChessView(Context context) {
        super(context);
        mRect.set(1000, 1000, 1000, 1000);
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        mPaint.setStyle(Paint.Style.STROKE);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        mRect.set(120, 120, width - 120, height - 120);
        //canvas.drawColor(Color.CYAN);
        canvas.drawBitmap(bmp, 0, 0, null);
        canvas.drawRect(mRect, mPaint);
    }
}
