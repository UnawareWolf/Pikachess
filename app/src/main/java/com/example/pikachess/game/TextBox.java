package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;

import com.example.pikachess.R;
import com.example.pikachess.game.battle.BattleText;

public class TextBox {

    private static final int WIDTH = 400;

    private TextPaint staticTextPaint;
    private String textString;
    private int x, y;

    public TextBox(Context context, int[] location, String textString) {
        x = location[0];
        y = location[1];
        this.textString = textString;
        staticTextPaint = new TextPaint();
        staticTextPaint.setAntiAlias(true);
        staticTextPaint.setTextSize(50);
        staticTextPaint.setColor(context.getResources().getColor(R.color.black));
    }

    public void draw(Canvas canvas) {
        canvas.drawText(textString, (float) x, (float) y, staticTextPaint);
    }
}
