package com.example.pikachess.game;

import android.content.Context;
import android.view.MotionEvent;

import static com.example.pikachess.game.pause.PikamonMenu.SCREEN_BORDER;

public class TextButton extends Button {
//
//    private TextButton(Context context, int[] location, int width, int height) {
//        super(context, "", location, width, height);
//    }

    public TextButton(Context context, int[] canvasDims) {
        super(context, calculateLocation(canvasDims), calculateWidth(canvasDims[0]), calculateHeight(canvasDims));
    }

    private static int[] calculateLocation(int[] canvasDims) {
        float top = SCREEN_BORDER * 2 + canvasDims[1] * 5 / 6f;
        float bottom = canvasDims[1] - SCREEN_BORDER;
        return new int[] {canvasDims[0] / 2, (int) (top + (bottom - top) / 2)};
    }

    private static int calculateWidth(int canvasWidth) {
        return canvasWidth - SCREEN_BORDER * 2;
    }

    private static int calculateHeight(int[] canvasDims) {
        float top = SCREEN_BORDER * 2 + canvasDims[1] * 5 / 6f;
        float bottom = canvasDims[1] - SCREEN_BORDER;
        return (int) (bottom - top);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {

    }
}
