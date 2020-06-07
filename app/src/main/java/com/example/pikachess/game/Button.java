package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

public class Button {

    private static final int HEIGHT = 100;
    private static final int WIDTH = 300;

    private Paint borderPaint;
    private Paint fillPaint;
    private Paint textPaint;
    private Rect buttonRect;
    private int buttonLeft, buttonTop, buttonRight, buttonBottom;
    private String content;
    private int[] location;

    public Button(Context context, String content, int[] location) {
        this.content = content;
        this.location = location;
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


//    buttonWidth = canvasWidth/4;
//    buttonHeight = buttonWidth/3;
//    buttonLeft = OFFSET + BORDER_WIDTH/2;
//    buttonTop = canvasWidth + BORDER_WIDTH/2;
//    buttonRight = buttonLeft + buttonWidth;
//    buttonBottom = buttonTop + buttonHeight;
//        confirmMoveButtonBounds.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
//        mRect.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
//        mPaint.setColor(getResources().getColor(R.color.buttonGrey));
//        if (greyOutConfirmMoveButton) {
//        mPaint.setAlpha(40);
//    }
//        else {
//        mPaint.setAlpha(255);
//    }
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(mRect, mPaint);
//        mPaint.setColor(Color.BLACK);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(BORDER_WIDTH);
//        canvas.drawRect(mRect, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setTextSize(80 - 2*BORDER_WIDTH);
//        canvas.drawText("Confirm", buttonLeft + BORDER_WIDTH, buttonBottom - 2*BORDER_WIDTH, mPaint);

}
