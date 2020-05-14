package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.example.pikachess.R;

public class GameView extends View {

    private Bitmap totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(totodileBackground, 0, 0, null);
    }
}
