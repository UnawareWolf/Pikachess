package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.Pikamon;

public class PikamonButton extends Button {

    private Pikamon pikamon;
    private String name;

    public PikamonButton(Context context, Pikamon pikamon, int[] location, int width, int height) {
        super(context, location, width, height);
        this.pikamon = pikamon;
        String[] classSegments = pikamon.getClass().getName().split("\\.");
        name = classSegments[classSegments.length - 1];
        setText(name);
        textPaint.setTextSize(height / 6f);
        double scaleFactor;
        int sectionWidth = pikamon.getPikaSprite().getSectionWidth();
        int sectionHeight = pikamon.getPikaSprite().getSectionHeight();
        int newHeight = height / 2;
        int newWidth = width / 2;
        if (sectionWidth > sectionHeight) {
            scaleFactor = width / (float) sectionWidth;
            newHeight = (int) (height / scaleFactor);
        }
        else {
            scaleFactor = height / (float) sectionHeight;
            newWidth = (int) (width / scaleFactor);
        }
        pikamon.getPikaSprite().setMenuPos(location[0] - newWidth / 2, location[1] - newHeight, location[0] + newWidth / 2, location[1]);

    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(buttonRect, roundX, roundX, fillPaint);
        canvas.drawRoundRect(buttonRect, roundX, roundX, borderPaint);
        canvas.drawText(content, buttonLeft + (height / 5f), buttonBottom - (height / 3f), textPaint);
        pikamon.drawInMenu(canvas);
    }
}
