package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.PauseState;
import com.example.pikachess.game.pause.PikamonMenu;

public class PikamonButton extends Button {

    private Pikamon pikamon;
    private String name;
    private PikamonImageButton imageButton;
    private boolean pikamonHeld;
    private int buttonID;

    public PikamonButton(Context context, Pikamon pikamon, int[] location, int width, int height, int buttonID) {
        super(context, location, width, height);
        this.buttonID = buttonID;
        pikamonHeld = false;
        setPikamon(pikamon);

        textPaint.setTextSize(height / 8f);
        imageButton = new PikamonImageButton(context, pikamon, new int[] {location[0], location[1] + PikamonMenu.SCREEN_BORDER / 2 - height / 4}, width / 2, height / 2, true);
    }

    public void setPikamon(Pikamon pikamon) {
        this.pikamon = pikamon;
        String[] classSegments = pikamon.getClass().getName().split("\\.");
        name = classSegments[classSegments.length - 1];
        setText("Lv." + pikamon.getLevel() + " " + name);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch)) {
            //pikaGame.getPikaPause().setPauseState(PauseState.PikamonStats);
        }
        imageButton.onTouchEvent(event, pikaGame);
        drawLast = imageButton.getDrawLast();
        pikamonHeld = imageButton.isPikamonHeld();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        canvas.drawText(content, buttonLeft + (height / 5f), buttonBottom - (height / 3f), textPaint);
        imageButton.draw(canvas);
    }

    public Pikamon getPikamon() {
        return pikamon;
    }

    public boolean isPikamonHeld() {
        return pikamonHeld;
    }

//    public void setPosition() {
//
//    }

    public void update() {
        setText("Lv." + pikamon.getLevel() + " " + name);
    }

    public int getButtonID() {
        return buttonID;
    }

}
