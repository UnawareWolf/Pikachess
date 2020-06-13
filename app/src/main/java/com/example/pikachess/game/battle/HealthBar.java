package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.example.pikachess.R;

public class HealthBar {

    private static final int HEIGHT = 20;
    private static final int WIDTH = 200;
    private static final int DIST_FROM_EDGE = 120;
    private static final int BORDER_WIDTH = 6;

    private int maxHp, currentHp;
    //private int currentHp;
    private boolean isPlayer;
    private int xLeft, yTop;
    private float xHp;
    private int canvasWidth;
    private Rect borderRect;
    private Rect healthRect;
    private Rect greyRect;
    private String hpText;
    private Paint borderPaint;
    private Paint healthPaint;
    private Paint greyPaint;
    private Paint hpTextPaint;
    private Context context;
    private Pikamon pikamon;

    public HealthBar(Context context, Pikamon pikamon) {
        maxHp = pikamon.getMaxHP();
        currentHp = pikamon.getHp();
        isPlayer = pikamon.getIsPlayer();
        this.context = context;
        canvasWidth = pikamon.getCanvasDims()[0];
        setPositionValues();
        borderRect = new Rect(xLeft - BORDER_WIDTH, yTop - BORDER_WIDTH, xLeft + WIDTH + BORDER_WIDTH, yTop + HEIGHT + BORDER_WIDTH);
        healthRect = new Rect();

        //greyRect = new Rect();
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        borderPaint.setColor(context.getResources().getColor(R.color.black));
        healthPaint = new Paint();
        healthPaint.setStyle(Paint.Style.FILL);
        healthPaint.setColor(context.getResources().getColor(R.color.green));
        hpTextPaint = new Paint();
        hpTextPaint.setStyle(Paint.Style.FILL);
        hpTextPaint.setColor(context.getResources().getColor(R.color.black));
        hpTextPaint.setTextSize(3 * HEIGHT / (float) 2);
        hpTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //greyPaint = new Paint();
        update(currentHp);
        updateHPText();
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(borderRect, borderPaint);
        canvas.drawRect(healthRect, healthPaint);
        //canvas.drawRect(greyRect, greyPaint);
        canvas.drawText(hpText, xLeft, yTop - 2 * HEIGHT / (float) 3, hpTextPaint);
    }

    public void update(int currentHp) {
        this.currentHp = currentHp;
        xHp = (currentHp / (float) maxHp) * WIDTH;
        healthRect.set(xLeft, yTop, xLeft + (int) xHp, yTop + HEIGHT);
        updateHealthColour();
        updateHPText();
    }

    private void updateHealthColour() {
        if (currentHp > maxHp / 2) {
            healthPaint.setColor(context.getResources().getColor(R.color.green));
        }
        else if (currentHp < maxHp / 4) {
            healthPaint.setColor(context.getResources().getColor(R.color.red));
        }
        else {
            healthPaint.setColor(context.getResources().getColor(R.color.orange));
        }
    }

    private void updateHPText() {
        hpText = currentHp + "/" + maxHp;
    }

    private void setPositionValues() {
        if (isPlayer) {
            xLeft = canvasWidth / 2 + DIST_FROM_EDGE;
            yTop = canvasWidth / 2;
        }
        else {
            xLeft = canvasWidth / 2 - DIST_FROM_EDGE;
            yTop = DIST_FROM_EDGE;
        }
    }

}
