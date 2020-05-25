package com.example.pikachess.game;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class SpriteSheet {

    protected Bitmap image;

    public void resizeBitmap(float scaleFactor) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleFactor, scaleFactor);
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        image.recycle();
        image = resizedBitmap;
        // I think I could actually just use create scaled bitmap as it does the same thing (I think) see here:
        // https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/graphics/java/android/graphics/Bitmap.java#645
    }
}
