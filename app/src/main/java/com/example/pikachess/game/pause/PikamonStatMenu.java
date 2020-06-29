package com.example.pikachess.game.pause;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PlayerCharacter;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.buttons.BackButton;
import com.example.pikachess.game.pause.buttons.PikamonButton;
import com.example.pikachess.game.pause.buttons.PikamonImageButton;

import java.util.List;

public class PikamonStatMenu {

    private static final int SCREEN_BORDER = 40;

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private int backButtonHeight;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;

    private Pikamon pikamon;
    private BackButton backButton;
    private PikamonImageButton imageButton;
    private int[] imageLocation;
    private int imageWidth, imageHeight;

    public PikamonStatMenu(Context context, int[] canvasDims, Pikamon pikamon) {
        containerBorder = SCREEN_BORDER / 2;
        buttonHeight = canvasDims[1] / 5;
        backButtonHeight = buttonHeight / 4;

        this.pikamon = pikamon;

        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER;
        bottom = top + 3 * buttonHeight + (5) * containerBorder + backButtonHeight;

        containerRect = new RectF(left, top, right, bottom);
        containerPaint = new Paint();
        containerPaint.setStyle(Paint.Style.FILL);
        containerPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        containerPaint.setAlpha(160);

        containerBorderPaint = new Paint();
        containerBorderPaint.setStyle(Paint.Style.STROKE);
        containerBorderPaint.setStrokeWidth(4);
        containerBorderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));


        buttonWidth = (int) (right - left - SCREEN_BORDER) / 2 - SCREEN_BORDER / 4;
        buttonX = (int) (left + (right - left) / 2) - buttonWidth / 2 - SCREEN_BORDER / 4;
        buttonY = (int) (top + buttonHeight / 2 + containerBorder);

        backButton = new BackButton(context, new int[]{(int) (right - SCREEN_BORDER / 2 - buttonWidth / 6), (int) (bottom - SCREEN_BORDER / 2 - backButtonHeight / 2)}, buttonWidth / 3, backButtonHeight);
//        pikamon.getPikaSprite().setMenuPos(left, top, right, bottom);

        calculateImagePosition();
        imageButton = new PikamonImageButton(context, pikamon, imageLocation, imageWidth, imageWidth, false);

    }

    private void calculateImagePosition() {
        float imageLeft = left + SCREEN_BORDER;
        float imageRight = imageLeft + (right - left) / 3;
        float imageTop = top + SCREEN_BORDER;
        float imageBottom = imageTop + imageRight - imageLeft;
        imageLocation = new int[] {(int) ((imageRight + imageLeft) / 2), (int) ((imageTop + imageBottom) / 2)};
        imageWidth = (int) (imageRight - imageLeft);
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(containerRect, 30, 30, containerPaint);
        canvas.drawRoundRect(containerRect, 30, 30, containerBorderPaint);
        backButton.draw(canvas);
        imageButton.draw(canvas);
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        backButton.onTouchEvent(event, pikaGame);
    }


}
