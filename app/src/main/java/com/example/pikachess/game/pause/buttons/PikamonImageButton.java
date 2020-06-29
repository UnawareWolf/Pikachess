package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.Pikamon;

public class PikamonImageButton extends Button {

    private boolean moveOnTouch;
    private Pikamon pikamon;
    private boolean initialTouchWithinButton;
    private int newWidth, newHeight;
    private boolean pikamonHeld;
    private Rect menuPos;

    public PikamonImageButton(Context context, Pikamon pikamon, int[] location, int width, int height, boolean moveOnTouch) {
        super(context, location, width, height);
        this.pikamon = pikamon;
        this.moveOnTouch = moveOnTouch;
        initialTouchWithinButton = false;
        pikamonHeld = false;

        fillPaint.setColor(context.getResources().getColor(R.color.chessWhite));
        int sectionWidth = pikamon.getPikaSprite().getSectionWidth();
        int sectionHeight = pikamon.getPikaSprite().getSectionHeight();
        newHeight = height;
        newWidth = width;
        if (sectionWidth > sectionHeight) {
            newHeight = sectionHeight * newWidth / sectionWidth;
        }
        else {
            newWidth = sectionWidth * newHeight / sectionHeight;
        }
        menuPos = new Rect(location[0] - newWidth / 2, location[1] - newHeight / 2, location[0] + newWidth / 2, location[1] + newHeight / 2);
//        setPikamonImageLocation(location[0], location[1]);
    }

//    public void setMenuPos(int left, int top, int right, int bottom) {
//        menuPos.set(left, top, right, bottom);
//    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        if (moveOnTouch) {
            float xTouch = event.getX();
            float yTouch = event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN && contains((int) xTouch, (int) yTouch)) {
                setPikamonImageLocation((int) xTouch, (int) yTouch);
                initialTouchWithinButton = true;
                drawLast = true;
                pikamonHeld = true;
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE && initialTouchWithinButton) {
                setPikamonImageLocation((int) xTouch, (int) yTouch);
                drawLast = true;
                pikamonHeld = true;
            }
            else if (event.getAction() == MotionEvent.ACTION_UP && initialTouchWithinButton) {
                setPikamonImageLocation(location[0], location[1]);
                initialTouchWithinButton = false;
                drawLast = true;
                pikamonHeld = true;
            }
            else {
                drawLast = false;
                pikamonHeld = false;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        pikamon.drawInMenu(canvas, menuPos);
    }

    private void setPikamonImageLocation(int x, int y) {
        menuPos.set(x - newWidth / 2, y - newHeight / 2, x + newWidth / 2, y + newHeight / 2);
    }

    public boolean isPikamonHeld() {
        return pikamonHeld;
    }

}
