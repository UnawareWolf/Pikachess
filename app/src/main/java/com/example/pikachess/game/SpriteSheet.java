package com.example.pikachess.game;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public abstract class SpriteSheet {

    protected int numberOfAnimations;
    protected int sectionWidth, sectionHeight;
    protected int canvasWidth, canvasHeight;

    protected Bitmap image;
    protected Rect backgroundPos;
    protected Rect framePos;
    protected Paint filterPaint;
    protected List<Rect> animationSections;

    private Matrix matrix;
    protected Options options;

    protected SpriteSheet() {
        matrix = new Matrix();
        filterPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        options = new BitmapFactory.Options();
        options.inScaled = false;
    }

    protected void createAnimationSections() {
        animationSections = new ArrayList<>();

        for (int i = 0; i < numberOfAnimations; i++) {
            int topLeftX = i*sectionWidth;
            int topLeftY = 0;
            int bottomRightX = (i + 1)*sectionWidth;
            int bottomRightY = sectionHeight;
            Rect animationSection = new Rect();
            animationSection.set(topLeftX, topLeftY, bottomRightX, bottomRightY);
            animationSections.add(animationSection);
        }
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
