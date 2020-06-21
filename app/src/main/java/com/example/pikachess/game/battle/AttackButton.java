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

public class AttackButton extends Button {

    private static final int HEIGHT = 120;
    public static final int WIDTH = 300;

    private AttackMove attack;

    public AttackButton(Context context, AttackMove attack, int[] location) {
        super(context, attack.getName(), location, WIDTH, HEIGHT);
        this.attack = attack;
    }

    public AttackMove getAttack() {
        return attack;
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        // Use this rather than handling in pikaBattle.
    }
}
