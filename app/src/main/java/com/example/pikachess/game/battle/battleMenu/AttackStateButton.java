package com.example.pikachess.game.battle.battleMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.BattleMenuState;

public class AttackStateButton extends Button {

    public AttackStateButton(Context context, int[] location, int width, int height) {
        super(context, "Attack", location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch) && event.getAction() == MotionEvent.ACTION_DOWN) {
            pikaGame.getPikaBattle().setBattleMenuState(BattleMenuState.Attack);
        }
    }

}
