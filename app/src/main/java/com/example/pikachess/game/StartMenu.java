package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class StartMenu {

    private Button pikamonButton, backButton, bagButton, saveButton, optionsButton;

    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;

    public StartMenu(Context context, int[] canvasDims) {
        containerRect = new RectF();
//        pikamonButton = new Button(context, "Pikamon");

    }

    public void draw(Canvas canvas) {

    }

    public void onTouchEvent(MotionEvent event) {

    }

}
