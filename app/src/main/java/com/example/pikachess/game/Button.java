package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.pikachess.R;

public abstract class Button {

    protected int width, height;
    protected Paint borderPaint;
    protected Paint fillPaint;
    protected Paint textPaint;
    protected RectF buttonRect;
    protected int buttonLeft, buttonTop, buttonRight, buttonBottom;
    protected String content;
    protected int[] location;
    protected float roundX;
    protected boolean drawLast;

    public Button(Context context, int[] location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
        buttonLeft = location[0] - width / 2;
        buttonTop = location[1] - height / 2;
        buttonRight = location[0] + width / 2;
        buttonBottom = location[1] + height /2;
//        roundX = height / 4f;
        roundX = 30f;
        drawLast = false;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);
        borderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(context.getResources().getColor(R.color.buttonGrey));

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
//        textPaint.setTextSize(height / 3f);
        textPaint.setTextSize(50);

        buttonRect = new RectF(buttonLeft, buttonTop, buttonRight, buttonBottom);
    }

    public Button(Context context, String content, int[] location, int width, int height) {
        this(context, location, width, height);
        this.content = content;
    }

    public boolean contains(int x, int y) {
        return (x >= buttonLeft && x <= buttonRight && y >= buttonTop && y <= buttonBottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        canvas.drawText(content, buttonLeft + (height / 5f), buttonBottom - (height / 3f), textPaint);
    }

    public abstract void onTouchEvent(MotionEvent event, PikaGame pikaGame);

    public void setText(String content) {
        this.content = content;
    }

    public boolean getDrawLast() {
        return drawLast;
    }

    public int[] getLocation() {
        return location;
    }

    public void setTextSize(float size) {
        textPaint.setTextSize(size);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
