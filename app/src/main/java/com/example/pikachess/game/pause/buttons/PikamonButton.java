package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.HealthBar;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.PauseState;
import com.example.pikachess.game.pause.PikamonMenu;

public class PikamonButton extends Button {

    private Pikamon pikamon;
    private String name;
    private PikamonImageButton imageButton;
    private boolean pikamonHeld;
    private int buttonID;
    private HealthBar healthBar;

    public PikamonButton(Context context, Pikamon pikamon, int[] location, int width, int height, int buttonID) {
        super(context, location, width, height);
        this.buttonID = buttonID;
        pikamonHeld = false;
        setPikamon(pikamon);
        healthBar = new HealthBar(context, pikamon, new int[] {location[0] - width / 10, location[1] + height / 3}, false);

        textPaint.setTextSize(height / 8f);
        imageButton = new PikamonImageButton(context, pikamon, new int[] {location[0], location[1] + PikamonMenu.SCREEN_BORDER / 2 - height / 4}, width / 2, height / 2, true);
    }

    private void setPikamon(Pikamon pikamon) {
        this.pikamon = pikamon;
        String[] classSegments = pikamon.getClass().getName().split("\\.");
        name = classSegments[classSegments.length - 1];
        setText("Lv." + pikamon.getLevel() + " " + name);
    }

    public void setNewPikamon(Pikamon pikamon) {
        setPikamon(pikamon);
        healthBar.setNewPikamon(pikamon);
        imageButton.setNewPikamon(pikamon);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch) && !imageButton.contains((int) xTouch, (int) yTouch) && event.getAction() == MotionEvent.ACTION_DOWN) {
            pikaGame.getPikaPause().setStatState(pikamon);
        }
        imageButton.onTouchEvent(event, pikaGame);
        drawLast = imageButton.getDrawLast();
        pikamonHeld = imageButton.isPikamonHeld();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        canvas.drawText(content, buttonLeft + (height / 5f), buttonBottom - (height / 4f), textPaint);
        healthBar.draw(canvas);
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

    public void refresh() {
        setText("Lv." + pikamon.getLevel() + " " + name);
        healthBar.update();
//        healthBar.setNewPikamon(pikamon);
    }

    public int getButtonID() {
        return buttonID;
    }

}
