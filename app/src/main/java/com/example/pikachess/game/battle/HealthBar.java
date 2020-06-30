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
    private static final int EXP_HEIGHT = 10;
    private static final int DIST_FROM_EDGE = 120;
    private static final int BORDER_WIDTH = 6;

    private int maxHp, currentHp;
    private int targetEXP, currentEXP;
    private int level;
    //private int currentHp;
    private boolean isPlayer;
    private boolean writeLevel, writeHPText;
    private int left, right, yTop;
    private int healthTop, healthBottom, expTop, expBottom;
    private float xHp, xEXP;
    private int canvasWidth;
    private Rect borderRect;
    private Rect expBorderRect;
    private Rect healthRect;
    private Rect expRect;
    private Rect greyRect;
    private String hpText;
    private Paint borderPaint;
    private Paint healthPaint;
    private Paint greyPaint;
    private Paint hpTextPaint;
    private Paint expPaint;
    private Context context;
    private Pikamon pikamon;

    public HealthBar(Context context, Pikamon pikamon, int[] location, boolean writeLevel) {
        this.pikamon = pikamon;
        maxHp = pikamon.getMaxHP();
        currentHp = pikamon.getHp();
        isPlayer = pikamon.getIsPlayer();
        level = pikamon.getLevel();
        targetEXP = pikamon.getTargetEXP();
        this.context = context;
        canvasWidth = pikamon.getCanvasDims()[0];
        this.writeLevel = writeLevel;
        writeHPText = true;

        left = location[0] - WIDTH / 2;
        right = left + WIDTH;
        healthTop = location[1] - HEIGHT / 2;
        healthBottom = healthTop + HEIGHT;
        expTop = healthBottom + BORDER_WIDTH / 2;
        expBottom = expTop + EXP_HEIGHT;

//        setPositionValues();
        borderRect = new Rect(left, healthTop, right, healthBottom);
//        expBorderRect = new Rect(xLeft, yTop + HEIGHT, xLeft + WIDTH, yTop + (3 * HEIGHT / 2));
        expBorderRect = new Rect(left, expTop, right, expBottom);
        healthRect = new Rect();
        expRect = new Rect();

        //greyRect = new Rect();
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        borderPaint.setColor(context.getResources().getColor(R.color.black));
        healthPaint = new Paint();
        healthPaint.setStyle(Paint.Style.FILL);
        healthPaint.setColor(context.getResources().getColor(R.color.green));
        expPaint = new Paint();
        expPaint.setStyle(Paint.Style.FILL);
        expPaint.setColor(context.getResources().getColor(R.color.expBlue));
        hpTextPaint = new Paint();
        hpTextPaint.setStyle(Paint.Style.FILL);
        hpTextPaint.setColor(context.getResources().getColor(R.color.black));
        hpTextPaint.setTextSize(3 * HEIGHT / 2f);
        hpTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //greyPaint = new Paint();
        update();
        updateHPText();
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(healthRect, healthPaint);
        if (isPlayer) {
            canvas.drawRect(expRect, expPaint);
            canvas.drawRect(expBorderRect, borderPaint);
            if (writeHPText) {
                canvas.drawText(hpText, right + BORDER_WIDTH * 2, healthBottom, hpTextPaint);
            }
        }
        canvas.drawRect(borderRect, borderPaint);
        //canvas.drawRect(greyRect, greyPaint);

        if (writeLevel) {
            canvas.drawText("Lv" + level, right - 3 * HEIGHT, healthTop - 2 * HEIGHT / 3f, hpTextPaint);
        }
    }

    public void update() {
        currentHp = pikamon.getHp();
        level = pikamon.getLevel();
        currentEXP = pikamon.getExp();
        xHp = (currentHp / (float) maxHp) * WIDTH;
        xEXP = (currentEXP / (float) targetEXP) * WIDTH;
        healthRect.set(left, healthTop, left + (int) xHp, healthBottom);
        expRect.set(left, expTop, left + (int) xEXP, expBottom);
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

    public void setNewPikamon(Pikamon pikamon) {
        this.pikamon = pikamon;
        maxHp = pikamon.getMaxHP();
        targetEXP = pikamon.getTargetEXP();
        update();
    }

    public void setWriteHPText(boolean writeHPText) {
        this.writeHPText = writeHPText;
    }

//    private void setPositionValues() {
//        if (isPlayer) {
//            xLeft = canvasWidth / 2 + DIST_FROM_EDGE;
//            yTop = canvasWidth / 2;
//        }
//        else {
//            xLeft = canvasWidth / 2 - DIST_FROM_EDGE;
//            yTop = DIST_FROM_EDGE;
//        }
//    }

}
