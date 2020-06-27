package com.example.pikachess.game.pause;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.BasicButton;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.pause.buttons.BackButton;
import com.example.pikachess.game.pause.buttons.PikamonMenuButton;

public class StartMenu {

    private static final int SCREEN_BORDER = 40;
    private static final String[] BUTTON_NAMES = new String[] {"Pikamon", "Bag", "Save", "Options", "Back"};

    private Button[] buttons;

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;

    public StartMenu(Context context, int[] canvasDims) {
        containerBorder = SCREEN_BORDER / 2;
        buttonHeight = canvasDims[1] / 20;

        left = canvasDims[0] * 3 / 4f;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER;
        bottom = top + BUTTON_NAMES.length * buttonHeight + (BUTTON_NAMES.length + 1) * containerBorder;

        containerRect = new RectF(left, top, right, bottom);
        containerPaint = new Paint();
        containerPaint.setStyle(Paint.Style.FILL);
        containerPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        containerPaint.setAlpha(160);

        containerBorderPaint = new Paint();
        containerBorderPaint.setStyle(Paint.Style.STROKE);
        containerBorderPaint.setStrokeWidth(4);
        containerBorderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        buttonX = (int) (left + (right - left) / 2);
        buttonWidth = (int) (right - left - SCREEN_BORDER);
        buttonY = (int) (top + buttonHeight / 2 + containerBorder);

        initialiseButtons(context);
    }

    private void initialiseButtons(Context context) {
        buttons = new Button[BUTTON_NAMES.length];
        int buttonCount = 0;
        int initialButtonY = buttonY;
        for (String buttonName : BUTTON_NAMES) {
            buttonY = initialButtonY + buttonCount * (buttonHeight + containerBorder);
            switch(buttonName) {
                case "Pikamon":
                    buttons[buttonCount] = new PikamonMenuButton(context, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight);
                    break;
                case "Back":
                    buttons[buttonCount] = new BackButton(context, new int[]{buttonX + buttonWidth / 4, buttonY}, buttonWidth / 2, buttonHeight);
                    break;
                default:
                    buttons[buttonCount] = new BasicButton(context, buttonName, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight);
            }
            buttonCount++;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(containerRect, 30, 30, containerPaint);
        canvas.drawRoundRect(containerRect, 30, 30, containerBorderPaint);
        for (Button button : buttons) {
            button.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        for (Button button : buttons) {
            button.onTouchEvent(event, pikaGame);
        }
    }

}
