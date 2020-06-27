package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.battleMenu.AttackMenu;

public class AttackButton extends Button {

    private AttackMove attack;
    private AttackMenu attackMenu;

    public AttackButton(Context context, AttackMenu attackMenu, AttackMove attack, int[] location, int width, int height) {
        super(context, attack.getName(), location, width, height);
        this.attackMenu = attackMenu;
        this.attack = attack;
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch) && event.getAction() == MotionEvent.ACTION_DOWN) {
            attackMenu.executeTurnAndUpdate(attack);
        }
    }
}
