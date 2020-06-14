package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import com.example.pikachess.R;
import com.example.pikachess.game.battle.BattleText;

public class TextBox {

    private static final int WIDTH = 650;
    private static final int HEIGHT = 120;
    private static final int BORDER_WIDTH = 12;

    private TextPaint staticTextPaint;
    private String textString;
    private int x, y;

    private Paint borderPaint;
    private Paint fillPaint;
    private Rect boxRect;

    public TextBox(Context context, int[] location, String textString) {
        x = location[0];
        y = location[1];
        this.textString = textString;
        staticTextPaint = new TextPaint();
        staticTextPaint.setAntiAlias(true);
        staticTextPaint.setTextSize(50);
        staticTextPaint.setColor(context.getResources().getColor(R.color.black));

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(context.getResources().getColor(R.color.black));
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        boxRect = new Rect(x, y - HEIGHT / 2, x + WIDTH, y + HEIGHT / 2);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(boxRect, fillPaint);
        canvas.drawRect(boxRect, borderPaint);
        canvas.drawText(textString, (float) x + BORDER_WIDTH * 2, (float) y + BORDER_WIDTH, staticTextPaint);
    }
}
