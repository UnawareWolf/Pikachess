package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.ControllerButton;
import com.example.pikachess.game.JoystickButton;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PixelMap;
import com.example.pikachess.game.PlayerCharacter;

public class GamePad {

    private static final int SCREEN_BORDER = 40;
    private static final int B_ANGLE = 225;
    private static final int Y_ANGLE = 340;

    private ControllerButton aButton;
    private ControllerButton bButton;
    private ControllerButton yButton;
    private JoystickButton joystick;

    private int[] canvasDims;
    private float xLeft, xRight, y, buttonOffset;

    public GamePad(Context context, int[] canvasDims) {
        this.canvasDims = canvasDims;
        xLeft = SCREEN_BORDER + JoystickButton.OUTER_RADIUS;
        xRight = canvasDims[0] - xLeft;
        y = canvasDims[1] * 2 / (float) 3;
        int aRadius = canvasDims[0] / 16;
        int bRadius = aRadius * 2 / 3;
        buttonOffset = bRadius + aRadius * 3 / (float) 2;

        aButton = new ControllerButton(context, xRight, y, aRadius, R.color.greenGCA);
        bButton = new ControllerButton(context, xRight + calculateXOffset(B_ANGLE), y + calculateYOffset(B_ANGLE), bRadius, R.color.redGCB);
        yButton = new ControllerButton(context, xRight + calculateXOffset(Y_ANGLE), y + calculateYOffset(Y_ANGLE), bRadius, R.color.buttonGrey);
        joystick = new JoystickButton(context, xLeft + JoystickButton.OUTER_RADIUS, y);
    }

    private float calculateXOffset(int angle) {
        return (float) (buttonOffset * Math.sin(Math.toRadians(angle)));
    }

    private float calculateYOffset(int angle) {
        return (float) (- 1 * buttonOffset * Math.cos(Math.toRadians(angle)));
    }

    public void draw(Canvas canvas) {
        joystick.draw(canvas);
        aButton.draw(canvas);
        bButton.draw(canvas);
        yButton.draw(canvas);
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame, PlayerCharacter mainCharacter, PixelMap pixelMap) {
        joystick.onTouchEvent(event, mainCharacter);
        aButton.onTouchEventA(event, pikaGame, mainCharacter, pixelMap);
        bButton.onTouchEventB(event, mainCharacter);
        yButton.onTouchEventY(event, pikaGame);
    }

    public void release(PlayerCharacter mainCharacter) {
        joystick.release(mainCharacter);
    }
}
