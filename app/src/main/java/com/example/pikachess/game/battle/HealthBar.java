package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

public class HealthBar {

    private static final int HEIGHT = 40;
    private static final int WIDTH = 200;
    private static final int DIST_FROM_EDGE = 120;
    private static final int BORDER_WIDTH = 6;

    private int maxHp;
    //private int currentHp;
    private boolean isPlayer;
    private int xLeft, yTop;
    private float xHp;
    private int canvasWidth;
    private Rect borderRect;
    private Rect healthRect;
    private Rect greyRect;
    private Paint borderPaint;
    private Paint healthPaint;
    private Paint greyPaint;

    public HealthBar(Context context, int maxHp, boolean isPlayer, int canvasWidth) {
        this.maxHp = maxHp;
        //this.currentHp = maxHp;
        this.isPlayer = isPlayer;
        this.canvasWidth = canvasWidth;
        setPositionValues();
        borderRect = new Rect(xLeft - BORDER_WIDTH, yTop - BORDER_WIDTH, xLeft + WIDTH + BORDER_WIDTH, yTop + HEIGHT + BORDER_WIDTH);
        healthRect = new Rect(xLeft, yTop, xLeft + WIDTH, yTop + HEIGHT);
        //greyRect = new Rect();
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        borderPaint.setColor(context.getResources().getColor(R.color.black));
        healthPaint = new Paint();
        healthPaint.setStyle(Paint.Style.FILL);
        healthPaint.setColor(context.getResources().getColor(R.color.green));
        //greyPaint = new Paint();
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(borderRect, borderPaint);
        canvas.drawRect(healthRect, healthPaint);
        //canvas.drawRect(greyRect, greyPaint);
    }

    public void update(int currentHp) {
        xHp = (currentHp / (float) maxHp) * WIDTH;
        healthRect.set(xLeft, yTop, xLeft + (int) xHp, yTop + HEIGHT);
    }

    private void setPositionValues() {
        if (isPlayer) {
//            xLeft = canvasWidth - DIST_FROM_EDGE - WIDTH;
            xLeft = canvasWidth / 2 + DIST_FROM_EDGE;
            yTop = canvasWidth / 2;
        }
        else {
            xLeft = canvasWidth / 2 - DIST_FROM_EDGE;
            yTop = DIST_FROM_EDGE;
        }
    }

}
