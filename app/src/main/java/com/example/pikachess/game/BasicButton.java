package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class BasicButton extends Button {
    public BasicButton(Context context, String content, int[] location, int width, int height) {
        super(context, content, location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
    }
}
