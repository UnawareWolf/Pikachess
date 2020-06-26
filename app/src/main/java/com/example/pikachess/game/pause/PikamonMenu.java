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

    public static final int SCREEN_BORDER = 40;

//    private Button[] buttons;
    private List<PikamonButton> pikamonButtons;
    private List<Button> otherButtons;

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

        initialiseButtons(context);
    }

    private void initialiseButtons(Context context) {
//        buttons = new Button[7];
        pikamonButtons = new ArrayList<>();
        otherButtons = new ArrayList<>();
        int buttonCount = 0;
        int initialButtonX = buttonX;
        int initialButtonY = buttonY;
        for (Pikamon pikamon : pikamen) {
//            buttonX = initialButtonX + buttonCount * (buttonHeight + containerBorder);
//            buttonY = initialButtonY + buttonCount * (buttonHeight + containerBorder);

            pikamonButtons.add(new PikamonButton(context, pikamon, new int[] {buttonX, buttonY}, buttonWidth, buttonHeight));

//            buttons[buttonCount] = new PikamonButton(context, pikamon, new int[] {buttonX, buttonY}, buttonWidth, buttonHeight);
            changeButtonPosition(buttonCount);
            buttonCount++;
        }
        while (buttonCount < 6) {
//            buttons[buttonCount] = new Button(context, "", new int[]{buttonX, buttonY}, buttonWidth, buttonHeight) {
//                @Override
//                public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
//                }
//            };
            otherButtons.add(new Button(context, "", new int[]{buttonX, buttonY}, buttonWidth, buttonHeight) {
                @Override
                public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
                }
            });
            changeButtonPosition(buttonCount);
            buttonCount++;
        }
        otherButtons.add(new BackButton(context, new int[]{(int) (right - SCREEN_BORDER / 2 - buttonWidth / 6), (int) (bottom - SCREEN_BORDER / 2 - backButtonHeight / 2)}, buttonWidth / 3, backButtonHeight));
//        buttons[buttonCount] = new BackButton(context, new int[]{(int) (right - SCREEN_BORDER / 2 - buttonWidth / 6), (int) (bottom - SCREEN_BORDER / 2 - backButtonHeight / 2)}, buttonWidth / 3, backButtonHeight);
    }

    private void changeButtonPosition(int buttonCount) {
        if (buttonCount % 2 == 0) {
            buttonX += buttonWidth + SCREEN_BORDER / 2;
        }
        else {
            buttonX -= buttonWidth + SCREEN_BORDER / 2;
            buttonY += buttonHeight + SCREEN_BORDER / 2;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(containerRect, 30, 30, containerPaint);
        canvas.drawRoundRect(containerRect, 30, 30, containerBorderPaint);
        for (Button button : otherButtons) {
            button.draw(canvas);
        }
        for (PikamonButton pikamonButton : pikamonButtons) {
            if (!pikamonButton.getDrawLast()) {
                pikamonButton.draw(canvas);
            }
        }
        for (PikamonButton pikamonButton : pikamonButtons) {
            if (pikamonButton.getDrawLast()) {
                pikamonButton.draw(canvas);
            }
        }
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
//        for (Button button : buttons) {
//            button.onTouchEvent(event, pikaGame);
//            if (button.getClass().getName().equals(PikamonButton.class.getName())) {
//                if (((PikamonButton) button).isPikamonHeld()) {
//                    // pikamonHeld = (PikamonButton) button).getPikamon();
//                    // Check if button has been released and
//                }
//            }
//        }
        float xTouch = event.getX();
        float yTouch = event.getY();
        PikamonButton pikamonHeld = null;
        for (PikamonButton pikamonButton : pikamonButtons) {
            pikamonButton.onTouchEvent(event, pikaGame);
            if (pikamonButton.isPikamonHeld()) {
                pikamonHeld = pikamonButton;
            }
        }
        if (pikamonHeld != null) {
            for (PikamonButton pikamonButton : pikamonButtons) {
                if (event.getAction() == MotionEvent.ACTION_UP && pikamonButton.contains((int) xTouch, (int) yTouch) && pikamonButton != pikamonHeld) {
                    Pikamon tempPikamon = pikamonButton.getPikamon();
                    pikamonButton.setPikamon(pikamonHeld.getPikamon());
                    pikamonHeld.setPikamon(tempPikamon);
                }
            }
        }
        for (Button button : otherButtons) {
            button.onTouchEvent(event, pikaGame);
        }
    }

    public void update() {
        for (PikamonButton pikamonButton : pikamonButtons) {
            pikamonButton.update();
        }
    }

}
