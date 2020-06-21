package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;

public class PikamonButton extends Button {

    private static final String TEXT = "Pikamon";

    public PikamonButton(Context context, int[] location, int width, int height) {
        super(context, TEXT, location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {

    }
}
