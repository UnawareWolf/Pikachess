package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;
import com.example.pikachess.game.battle.AttackMove;

public class Button {

    private static final int HEIGHT = 60;
    public static final int WIDTH = 150;

    private Paint borderPaint;
    private Paint fillPaint;
    private Paint textPaint;
    private Rect buttonRect;
    private int buttonLeft, buttonTop, buttonRight, buttonBottom;
    private String content;
    private int[] location;
    private AttackMove attack;

    public Button(Context context, AttackMove attack, int[] location) {
        this.content = attack.getName();
        this.location = location;
        this.attack = attack;
        buttonLeft = location[0] - WIDTH / 2;
        buttonTop = location[1] - HEIGHT / 2;
        buttonRight = location[0] + WIDTH / 2;
        buttonBottom = location[1] + HEIGHT /2;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(HEIGHT / (float) 12);
        borderPaint.setColor(context.getResources().getColor(R.color.black));
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(HEIGHT / (float) 2);

        buttonRect = new Rect(buttonLeft, buttonTop, buttonRight, buttonBottom);

    }

    public boolean contains(int x, int y) {
        return (x >= buttonLeft && x <= buttonRight && y >= buttonTop && y <= buttonBottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(buttonRect, fillPaint);
        canvas.drawRect(buttonRect, borderPaint);
//        canvas.drawText(content, buttonLeft + WIDTH / 10, buttonRight - WIDTH / 10, textPaint);
        canvas.drawText(content, buttonLeft + (HEIGHT / (float) 3), buttonBottom - (HEIGHT / (float) 3), textPaint);
    }

    public AttackMove getAttack() {
        return attack;
    }

}
