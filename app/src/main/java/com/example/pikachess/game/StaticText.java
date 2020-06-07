package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.pikachess.R;

public class StaticText {

    private static final int WIDTH = 400;

    private TextPaint staticTextPaint;
    private String textString;
    private StaticLayout staticLayout;
    private int xShift, yShift;

    public StaticText(Context context, String content, int[] location) {
        xShift = location[0];
        yShift = location[1];
        staticTextPaint = new TextPaint();
        staticTextPaint.setAntiAlias(true);
        staticTextPaint.setTextSize(30);
        staticTextPaint.setColor(context.getResources().getColor(R.color.black));
        textString = content;
        staticLayout = new StaticLayout(textString, staticTextPaint, WIDTH, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(xShift, yShift);
        staticLayout.draw(canvas);
        canvas.restore();

    }

}
