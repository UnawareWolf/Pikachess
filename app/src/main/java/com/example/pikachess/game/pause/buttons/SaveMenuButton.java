package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaPause;
import com.example.pikachess.game.pause.PauseState;

public class SaveMenuButton extends Button {

    private PikaPause pikaPause;

    public SaveMenuButton(Context context, PikaPause pikaPause, int[] location, int width, int height) {
        super(context, "Save", location, width, height);
        this.pikaPause = pikaPause;
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && contains((int) xTouch, (int) yTouch)) {
            pikaPause.setPauseState(PauseState.Save);
        }
    }
}
