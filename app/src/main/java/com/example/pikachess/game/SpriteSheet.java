package com.example.pikachess.game;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class SpriteSheet {

    protected Bitmap image;
    protected Rect backgroundPos;
    protected Rect framePos;
    protected Paint filterPaint;
    private Matrix matrix;

    protected SpriteSheet() {
        matrix = new Matrix();
    }

    public void resizeBitmap(float xScaleFactor, float yScaleFactor) {
        //Matrix matrix = new Matrix();
        matrix.postScale(xScaleFactor, yScaleFactor);
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        image.recycle();
        image = resizedBitmap;
        // I think I could actually just use create scaled bitmap as it does the same thing (I think) see here:
        // https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/graphics/java/android/graphics/Bitmap.java#645
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, backgroundPos, framePos, filterPaint);
    }
}
