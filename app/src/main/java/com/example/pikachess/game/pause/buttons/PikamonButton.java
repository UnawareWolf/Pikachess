package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.Pikamon;

public class PikamonButton extends Button {

    Pikamon pikamon;

    public PikamonButton(Context context, Pikamon pikamon, int[] location, int width, int height) {
        super(context, location, width, height);
        setText(pikamon.getClass().getName());

    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {

    }
}
