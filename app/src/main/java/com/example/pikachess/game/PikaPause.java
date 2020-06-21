package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.pause.PauseState;
import com.example.pikachess.game.pause.StartMenu;

public class PikaPause {

    private StartMenu startMenu;
    private PauseState state;

    public PikaPause(Context context, int[] canvasDims) {
        state = PauseState.Menu;
        startMenu = new StartMenu(context, canvasDims);
    }

    public void draw(Canvas canvas) {
        if (state == PauseState.Menu) {
            startMenu.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        startMenu.onTouchEvent(event, pikaGame);
    }


}
