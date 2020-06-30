package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.BasicButton;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.TextButton;

import static com.example.pikachess.game.pause.PikamonMenu.SCREEN_BORDER;

public class BattleText {

    private static final int WIDTH = 400;

    private TextPaint staticTextPaint;
    private String textString;
    private String focus, body, attackName;
//    private int x, y;
    private TextButton button;
    private int[] canvasDims;
    private int[] location;
    private float left, top, right, bottom;
    private Pikamon playerPikamon, opponentPikamon;

    public BattleText(Context context, int[] canvasDims, Pikamon playerPikamon, Pikamon opponentPikamon) {
        this.canvasDims = canvasDims;
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;

        top = SCREEN_BORDER * 2 + canvasDims[1] * 5 / 6f;
        bottom = canvasDims[1] - SCREEN_BORDER;
        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        location = new int[] {canvasDims[0] / 2, (int) (top + (bottom - top) / 2)};

//        x = location[0];
//        y = location[1];
        staticTextPaint = new TextPaint();
        staticTextPaint.setAntiAlias(true);
        staticTextPaint.setTextSize(50);
        staticTextPaint.setColor(context.getResources().getColor(R.color.black));

//        button = new BasicButton(context, "", location, (int) (right - left), (int) (bottom - top));
        button = new TextButton(context, canvasDims);
    }

    public void draw(Canvas canvas, TextType textType, boolean isPlayer) {
        setText(textType, isPlayer);
//        canvas.drawText(textString, (float) x, (float) y, staticTextPaint);
        button.draw(canvas);
//        canvas.drawText(textString, left + SCREEN_BORDER, (float) location[1], staticTextPaint);
    }

    public void draw(Canvas canvas, TextType textType, boolean isPlayer, String attackName) {
        setText(textType, isPlayer, attackName);
        button.draw(canvas);
//        canvas.drawText(textString, left + SCREEN_BORDER, (float) location[1], staticTextPaint);
    }

    public void setText(String textString) {
        this.textString = textString;
    }

    private void setText(TextType textType, boolean isPlayer, String attackName) {
        if (textType == TextType.AttackTurn) {
            body = " used " + attackName + "!";
        }
        setText(textType, isPlayer);
    }

    private void setText(TextType textType, boolean isPlayer) {
        if (textType == TextType.Faint) {
            body = " fainted!";
        }
        setPlayerText(isPlayer);
        textString = focus + body;
        button.setText(textString);
    }

    private void setPlayerText(boolean isPlayer) {
        if (isPlayer) {
            focus = "Cletus' " + playerPikamon.getName();
        }
        else {
            focus = "Wild " + opponentPikamon.getName();
        }
    }

    public enum TextType {
        AttackTurn(),
        Faint();
    }

}
