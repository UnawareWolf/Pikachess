package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.pikachess.R;

public class Button {

    protected int width, height;
    protected Paint borderPaint;
    protected Paint fillPaint;
    protected Paint textPaint;
    protected RectF buttonRect;
    protected int buttonLeft, buttonTop, buttonRight, buttonBottom;
    protected String content;
    protected int[] location;
    private float roundX;

    public Button(Context context, String content, int[] location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
        this.content = content;
        buttonLeft = location[0] - width / 2;
        buttonTop = location[1] - height / 2;
        buttonRight = location[0] + width / 2;
        buttonBottom = location[1] + height /2;
        roundX = height / 4f;
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(height / 30f);
        borderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(height / 3f);

        buttonRect = new RectF(buttonLeft, buttonTop, buttonRight, buttonBottom);

    }

    public boolean contains(int x, int y) {
        return (x >= buttonLeft && x <= buttonRight && y >= buttonTop && y <= buttonBottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        canvas.drawText(content, buttonLeft + (height / 5f), buttonBottom - (height / 3f), textPaint);
    }

}
