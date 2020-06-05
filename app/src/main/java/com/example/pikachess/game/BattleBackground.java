package com.example.pikachess.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pikachess.R;

public class BattleBackground extends SpriteSheet {

    public BattleBackground(Context context, int canvasWidth, int canvasHeight) {
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass_battle_background);
        float xScale = canvasWidth / (float) image.getWidth();
        float yScale = canvasHeight / (float) image.getHeight();
        resizeBitmap(xScale, yScale);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, 0, null);
    }

}
