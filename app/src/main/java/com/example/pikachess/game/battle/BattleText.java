package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;

import com.example.pikachess.R;

public class BattleText {

    private static final int WIDTH = 400;

    private TextPaint staticTextPaint;
    private String textString;
    private String focus, body, attackName;
    private int x, y;

    public BattleText(Context context, int[] location) {
        x = location[0];
        y = location[1];
        staticTextPaint = new TextPaint();
        staticTextPaint.setAntiAlias(true);
        staticTextPaint.setTextSize(50);
        staticTextPaint.setColor(context.getResources().getColor(R.color.black));
    }

    public void draw(Canvas canvas, TextType textType, boolean isPlayer) {
        setText(textType, isPlayer);
        canvas.drawText(textString, (float) x, (float) y, staticTextPaint);
    }

    public void draw(Canvas canvas, TextType textType, boolean isPlayer, String attackName) {
        setText(textType, isPlayer, attackName);
        canvas.drawText(textString, (float) x, (float) y, staticTextPaint);
    }

    public void setText(String textString) {
        this.textString = textString;
    }

    private void setText(TextType textType, boolean isPlayer, String attackName) {
        if (textType == TextType.AttackTurn) {
            body = "Fatty used " + attackName + "!";
        }
        setText(textType, isPlayer);
    }

    private void setText(TextType textType, boolean isPlayer) {
        if (textType == TextType.Faint) {
            body = "Fatty fainted!";
        }
        setPlayerText(isPlayer);
        textString = focus + body;
    }

    private void setPlayerText(boolean isPlayer) {
        if (isPlayer) {
            focus = "Cletus' ";
        }
        else {
            focus = "Wild ";
        }
    }

    public enum TextType {
        AttackTurn(),
        Faint();
    }

}
