package com.example.pikachess.game.battle.battleMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;

public class RunButton extends Button {

    public RunButton(Context context, int[] location, int width, int height) {
        super(context, "Run", location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch)) {
            pikaGame.setGameState(PikaGameState.Roam);
        }
    }

}
