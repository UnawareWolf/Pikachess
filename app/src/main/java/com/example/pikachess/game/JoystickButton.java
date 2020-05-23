package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.pikachess.R;

import static java.lang.Math.abs;

public class JoystickButton {

    private static final int OUTER_RADIUS = 140;
    private static final int INNER_RADIUS = 60;
    private static final int DEAD_ZONE = 30;
    //private Rect mRect;
    private Paint outlinePaint;
    private Paint innerPaint;
    private Circle outerCircle;
    private Circle innerCircle;
    private Circle deadZoneCircle;
    private int canvasWidth;
    private int centreX;
    private int centreY;
    private boolean initialTouchWithinButton;

    public JoystickButton(Context context, float centreX, float centreY) {
        //this.canvasWidth = canvasWidth;
//        this.centreX = centreX;
//        this.centreY = centreY;
        //mRect = new Rect();
        outerCircle = new Circle(centreX, centreY, OUTER_RADIUS);
        outlinePaint = new Paint();
        outlinePaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        outlinePaint.setStrokeWidth(12);
        outlinePaint.setStyle(Paint.Style.STROKE);

        innerCircle = new Circle(centreX, centreY, INNER_RADIUS);
        innerPaint = new Paint();
        innerPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        innerPaint.setStyle(Paint.Style.FILL);

        deadZoneCircle = new Circle(centreX, centreY, DEAD_ZONE);

        //mRect.set(rectLeft, rectTop, rectRight, rectBottom);
    }

    public void draw(Canvas canvas) {
        outerCircle.draw(canvas, outlinePaint);
        innerCircle.draw(canvas, innerPaint);
        //canvas.drawCircle((float) circle.getX(), (float) circle.getY(), (float) circle.getRadius(), mPaint);
        //canvas.drawRect(mRect, mPaint);
    }

    //public Rect getRect() {
//        return mRect;
//    }

    public void onTouchEvent(MotionEvent event, GameCharacter mainCharacter) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && isTouchWithinButton(xTouch, yTouch)) {
            fingerMovingJoystick(mainCharacter, xTouch, yTouch);
            initialTouchWithinButton = true;
            //initialTouchWithinButton = isTouchWithinButton(xTouch, yTouch);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE && initialTouchWithinButton) {
            fingerMovingJoystick(mainCharacter, xTouch, yTouch);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mainCharacter.setStateAccordingToJoystick(mainCharacter.getStationaryState());
            //mainCharacter.setCharacterState(CharacterState.Stationary);
            innerCircle.setX(outerCircle.getX());
            innerCircle.setY(outerCircle.getY());
            initialTouchWithinButton = false;
        }
    }

    private boolean isTouchWithinButton(float xTouch, float yTouch) {
        boolean touchWithinButton = false;
        if (outerCircle.contains(Math.round(xTouch), Math.round(yTouch))) {
            touchWithinButton = true;
        }
        return touchWithinButton;
    }

    private void fingerMovingJoystick(GameCharacter mainCharacter, float xTouch, float yTouch) {


//        if (outerCircle.contains(Math.round(xTouch), Math.round(yTouch))) {
//            initialTouchWithinButton = true;
//        }
        if (isTouchWithinButton(xTouch, yTouch)) {
            innerCircle.setX(xTouch);
            innerCircle.setY(yTouch);
        }
        else {
            float[] nearestBoundaryPoints = outerCircle.getNearestBoundaryPoint(xTouch, yTouch);
            innerCircle.set(nearestBoundaryPoints[0], nearestBoundaryPoints[1]);
        }

        //if (mainCharacter.isStationary()) {
            mainCharacter.setStateAccordingToJoystick(mainCharacter.getCharacterStateFromTouch(this, xTouch, yTouch));
        //}


//        if (!deadZoneCircle.contains(xTouch, yTouch)) {
//            float xDif = Math.round(xTouch) - outerCircle.getX();
//            float yDif = Math.round(yTouch) - outerCircle.getY();
//            if (abs(xDif) >= abs(yDif)) {
//                if (xDif >= 0) {
//                    mainCharacter.setCharacterState(CharacterState.MovingRight);
//                }
//                else {
//                    mainCharacter.setCharacterState(CharacterState.MovingLeft);
//                }
//            }
//            else {
//                if (yDif >= 0 ) {
//                    mainCharacter.setCharacterState(CharacterState.MovingDown);
//                }
//                else {
//                    mainCharacter.setCharacterState(CharacterState.MovingUp);
//                }
//            }
//        }
//        else {
//            mainCharacter.setStationaryState();
//        }
    }

    public Circle getDeadZoneCircle() {
        return deadZoneCircle;
    }

    public Circle getOuterCircle() {
        return outerCircle;
    }

    //    private void setCharacterStateFromTouch(GameCharacter mainCharacter, float xTouch, float yTouch) {
//        if (!deadZoneCircle.contains(xTouch, yTouch)) {
//            float xDif = Math.round(xTouch) - outerCircle.getX();
//            float yDif = Math.round(yTouch) - outerCircle.getY();
//            if (abs(xDif) >= abs(yDif)) {
//                if (xDif >= 0) {
//                    mainCharacter.setCharacterState(CharacterState.MovingRight);
//                }
//                else {
//                    mainCharacter.setCharacterState(CharacterState.MovingLeft);
//                }
//            }
//            else {
//                if (yDif >= 0 ) {
//                    mainCharacter.setCharacterState(CharacterState.MovingDown);
//                }
//                else {
//                    mainCharacter.setCharacterState(CharacterState.MovingUp);
//                }
//            }
//        }
//        else {
//            mainCharacter.setStationaryState();
//        }
//
//    }

}
