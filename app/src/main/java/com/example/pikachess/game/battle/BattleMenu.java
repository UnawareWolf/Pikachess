package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.BasicButton;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.battleMenu.AttackStateButton;
import com.example.pikachess.game.battle.battleMenu.CatchButton;
import com.example.pikachess.game.battle.battleMenu.RunButton;
import com.example.pikachess.game.pause.buttons.BackButton;

public class BattleMenu {


    private static final int SCREEN_BORDER = 40;
    private static final String[] BUTTON_NAMES = new String[] {"Attack", "Pikamon", "Catch", "Bag", "Run"};

    private Button[] buttons;

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;
    private BattleMenuState state;
    private PikaBattle pikaBattle;

    public BattleMenu(Context context, int[] canvasDims, PikaBattle pikaBattle) {
        containerBorder = SCREEN_BORDER / 2;

        this.pikaBattle = pikaBattle;
        state = BattleMenuState.Menu;

        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER + canvasDims[1] / 2f;
        bottom = top + canvasDims[1] / 3f;

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
        buttonHeight = (int) ((bottom - top) - SCREEN_BORDER * 2) / 3;
        buttonX = (int) (left + (right - left) / 2) - buttonWidth / 2 - SCREEN_BORDER / 4;
        buttonY = (int) (top + buttonHeight / 2 + containerBorder);


        initialiseButtons(context);
    }

    private void initialiseButtons(Context context) {
        buttons = new Button[BUTTON_NAMES.length];
        int buttonCount = 0;
        for (String buttonName : BUTTON_NAMES) {
            switch(buttonName) {
                case "Attack":
                    buttons[buttonCount] = new AttackStateButton(context, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight);
                    break;
                case "Catch":
                    buttons[buttonCount] = new CatchButton(context, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight, pikaBattle.getOpponentPikamon());
                    break;
                case "Run":
                    buttons[buttonCount] = new RunButton(context, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight);
                    break;
                default:
                    buttons[buttonCount] = new BasicButton(context, buttonName, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight);
                    break;
            }
            changeButtonPosition(buttonCount);
            buttonCount++;
        }
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
        for (Button button : buttons) {
            button.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        for (Button button : buttons) {
            button.onTouchEvent(event, pikaGame);
        }
    }

    public void setState(BattleMenuState state) {
        this.state = state;
    }
}
