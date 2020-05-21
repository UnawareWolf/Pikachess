package com.example.pikachess.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Circle {

    private float radius, x, y;

    public Circle() {
    }

    public Circle(float x, float y, float radius) {
        this.radius = radius;
        this.x  = x;
        this.y = y;
    }

    public void set(float x, float y, float radius) {
        this.radius = radius;
        this.x  = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x  = x;
        this.y = y;
    }

    public boolean contains(float xPos, float yPos) {
        boolean contained = false;
        float xDif = abs(x - xPos);
        float yDif = abs(y - yPos);
        double absDif = sqrt(Math.pow(xDif, 2.0) + Math.pow(yDif, 2.0));
        if (Math.round(absDif) <= radius) {
            contained = true;
        }
        return contained;
    }

    private double getHypotenuseLength(float xDif, float yDif) {
        return sqrt(Math.pow(xDif, 2.0) + Math.pow(yDif, 2.0));
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw(Canvas canvas, Paint mPaint) {
        canvas.drawCircle(x, y, radius, mPaint);
    }

    public float[] getNearestBoundaryPoint(float xPos, float yPos) {
        float xDif = xPos - x;
        float yDif = yPos - y;
        float xCircumference = (float) sqrt(Math.pow(radius, 2.0)/(1 + Math.pow(yDif, 2)/Math.pow(xDif, 2)));
//        float xCircumference = (float) ((radius*xDif)/(yDif));
        float yCircumference = xCircumference*yDif/xDif;
        if (xDif < 1) {
            xCircumference = xCircumference*(-1);
            yCircumference = yCircumference*(-1);
        }

        return new float[] {xCircumference + x, yCircumference + y};
    }

}
