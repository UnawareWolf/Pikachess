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
import com.example.pikachess.game.pause.buttons.PikamonMenuButton;

import java.util.ArrayList;
import java.util.List;

public class PikamonMenu {

    private static final int SCREEN_BORDER = 40;
    private static final String[] BUTTON_NAMES = new String[] {"Pikamon", "Bag", "Save", "Options", "Back"};

    private Button[] buttons;

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private int backButtonHeight;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;

    private List<Pikamon> pikamen;

    public PikamonMenu(Context context, int[] canvasDims, PlayerCharacter mainCharacter) {
        containerBorder = SCREEN_BORDER / 2;
        buttonHeight = canvasDims[1] / 5;
        backButtonHeight = buttonHeight / 4;

        pikamen = mainCharacter.getPikamen();

        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER;
        bottom = top + pikamen.size() * buttonHeight + (pikamen.size() + 2) * containerBorder + backButtonHeight;

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
        buttons = new Button[pikamen.size() + 1];
        int buttonCount = 0;
        int initialButtonX = buttonX;
        int initialButtonY = buttonY;
        for (Pikamon pikamon : pikamen) {
            buttonX = initialButtonX + buttonCount * (buttonHeight + containerBorder);
            buttonY = initialButtonY + buttonCount * (buttonHeight + containerBorder);

            buttons[buttonCount] = new PikamonButton(context, pikamon, new int[] {buttonX, buttonY}, buttonWidth, buttonHeight);

            buttonCount++;
        }
        buttons[buttonCount] = new BackButton(context, new int[]{(int) (right - SCREEN_BORDER / 2 - buttonWidth / 12), (int) (bottom - SCREEN_BORDER / 2 - backButtonHeight / 2)}, buttonWidth / 6, backButtonHeight);
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
