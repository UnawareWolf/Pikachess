package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.NPC;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;
import com.example.pikachess.game.pause.PauseState;

public class PikamonMenuButton extends Button {

    private static final String TEXT = "Pikamon";

    public PikamonMenuButton(Context context, int[] location, int width, int height) {
        super(context, TEXT, location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch) && event.getAction() == MotionEvent.ACTION_DOWN) {
            pikaGame.getPikaPause().setPauseState(PauseState.Pikamon);
            pikaGame.getPikaPause().getPikamonMenu().refresh();
        }
    }
}
