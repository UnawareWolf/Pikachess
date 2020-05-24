package com.example.pikachess.game;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class SpriteSheet {

    protected Bitmap image;
    //protected Bitmap scaledBitmap

//    protected Bitmap resizeBitmap(Canvas canvas, int newWidth, int newHeight) {
//        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Config.ARGB_8888);
//
//        float ratioX = newWidth / (float) image.getWidth();
//        float ratioY = newHeight / (float) image.getHeight();
//
//        Matrix scaleMatrix = new Matrix();
//        scaleMatrix.setScale(ratioX, ratioY, 0, 0);
//
//        //Canvas canvas = new Canvas(scaledBitmap);
//        canvas.setMatrix(scaleMatrix);
//
//    }

    public void resizeBitmap(float scaleFactor) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleFactor, scaleFactor);


        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        image.recycle();
        image = resizedBitmap;
    }


}
