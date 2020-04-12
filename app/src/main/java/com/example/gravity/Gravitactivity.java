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
import android.view.View;
import android.widget.ImageView;

public class Gravitactivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();
    private static final int OFFSET = 120;
    private int mOffset = OFFSET;
    private static final int MULTIPLIER = 100;
    private int mColourBackground;
    private int mColourRectangle;
    private int mColourAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_gravitactivity);
        mColourBackground = ResourcesCompat.getColor(getResources(),
                R.color.colourBackground, null);
        mColourRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colourRectangle, null);
        mColourAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);
        mPaint.setColor(0);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mPaintText.setTextSize(70);


        mImageView = (ImageView) findViewById(R.id.myimageview);
     MyDrawable myDrawing = new MyDrawable();
        mImageView = (ImageView) findViewById(R.id.myimageview);
        mImageView.setImageDrawable(myDrawing);
        mImageView.setContentDescription(getResources().getString(R.string.my_image_string));
        setContentView(mImageView);
*/
        setContentView(new ChessView(this));
    }

    public class MyDrawable extends Drawable {
        private final Paint redPaint;

        public MyDrawable() {

            redPaint = new Paint();
            redPaint.setARGB(255, 255, 0, 0);
        }

        @Override
        public void draw(Canvas canvas) {

            int width = getBounds().width();
            int height = getBounds().height();
            mRect.set(mOffset, mOffset, width - mOffset, height - mOffset);
            canvas.drawRect(mRect, redPaint);
        }
        @Override
        public void setAlpha(int alpha) {
            //
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }

    public void drawChessboard(View view) {
        int vWidth = view.getMeasuredWidth();
        int vHeight = view.getMeasuredHeight();
        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImageView.setImageBitmap(mBitmap);
        mCanvas = new Canvas(mBitmap);
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
        mCanvas.drawRect(mRect, mPaint);


    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if (mOffset == OFFSET) {
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColourBackground);
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        } else {
            if (mOffset < halfWidth && mOffset < halfHeight) {
                mPaint.setColor(mColourRectangle - MULTIPLIER*mOffset);
                mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else {
                mPaint.setColor(mColourAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        view.invalidate();
    }
}
