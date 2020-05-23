package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pikachess.R;

import static java.lang.Math.abs;

public class GameCharacter {

    //private boolean isMoving;
    //private Bitmap coreSpriteSheet;
    private CharacterSpritesheet spritesheet;
    private CharacterState characterState;
    private CharacterState stateAccordingToJoystick;

    private int gridSquareSize;
    private int x, y, speed;
    private int xVel, yVel;
    private int distTravelled;
    private int canvasWidth;

    public GameCharacter(Context context, PikaGame pikaGame) {
        this.canvasWidth = pikaGame.getCanvasWidth();
        gridSquareSize = PikaGame.GRID_SQUARE_SIZE;
        spritesheet = new CharacterSpritesheet(context, canvasWidth);
        characterState = CharacterState.StationaryDown;
        stateAccordingToJoystick = CharacterState.StationaryDown;
        distTravelled = 0;
        speed = spritesheet.getNumberOfCyclesPerGridSquare();
        x = 0;
        y = 0;
    }

    public CharacterState getStationaryState() {
        CharacterState stationaryState;
        switch (characterState) {
            case MovingUp:
                stationaryState = CharacterState.StationaryUp;
                break;
            case MovingLeft:
                stationaryState = CharacterState.StationaryLeft;
                break;
            case MovingDown:
                stationaryState = CharacterState.StationaryDown;
                break;
            default:
                stationaryState = CharacterState.StationaryRight;
        }
        return stationaryState;
    }

    public void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

    public CharacterState getCharacterState() {
        return this.characterState;
    }

    public void draw(Canvas canvas) {
        spritesheet.draw(canvas, characterState);
    }

    public void update() {
        updateCharacterMotionAndPosition();
        updateCharacterState();
    }

    private void updateCharacterState() {
        if (distTravelled == 0) {
            characterState = stateAccordingToJoystick;
        }
    }

    private void updateCharacterMotionAndPosition() {
        if (characterState == CharacterState.MovingLeft) {
            xVel = -speed;
            yVel = 0;
        }
        else if (characterState == CharacterState.MovingUp){
            xVel = 0;
            yVel = -speed;
        }
        else if (characterState == CharacterState.MovingRight) {
            xVel = speed;
            yVel = 0;
        }
        else if (characterState == CharacterState.MovingDown){
            xVel = 0;
            yVel = speed;
        }
        else {
            xVel = 0;
            yVel = 0;
        }
        distTravelled = distTravelled + xVel + yVel;
        if (abs(distTravelled) >= gridSquareSize) {
            distTravelled = 0;
        }
        x = x + xVel;
        y = y + yVel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXVel() {
        return xVel;
    }

    public int getYVel() {
        return yVel;
    }

    public boolean isStationary() {
        boolean stationary = false;
        if (characterState == CharacterState.StationaryDown || characterState == CharacterState.StationaryLeft || characterState == CharacterState.StationaryUp || characterState == CharacterState.StationaryRight) {
            stationary = true;
        }
        return stationary;
    }

    public CharacterState getCharacterStateFromTouch(JoystickButton joystickButton, float xTouch, float yTouch) {
        CharacterState stateAccordingToTouch;
        if (!joystickButton.getDeadZoneCircle().contains(xTouch, yTouch)) {
            float xDif = Math.round(xTouch) - joystickButton.getOuterCircle().getX();
            float yDif = Math.round(yTouch) - joystickButton.getOuterCircle().getY();
            if (abs(xDif) >= abs(yDif)) {
                if (xDif >= 0) {
                    stateAccordingToTouch = CharacterState.MovingRight;
                } else {
                    stateAccordingToTouch = CharacterState.MovingLeft;
                }
            } else {
                if (yDif >= 0) {
                    stateAccordingToTouch = CharacterState.MovingDown;
                } else {
                    stateAccordingToTouch = CharacterState.MovingUp;
                }
            }
        } else {
            stateAccordingToTouch = getStationaryState();
        }
        return stateAccordingToTouch;
    }

    public void setStateAccordingToJoystick(CharacterState state) {
        stateAccordingToJoystick = state;
    }

//    public void setCharacterStateFromTouch(JoystickButton joystickButton, float xTouch, float yTouch) {
//        if (distTravelled == 0) {
//            if (!joystickButton.getDeadZoneCircle().contains(xTouch, yTouch)) {
//                float xDif = Math.round(xTouch) - joystickButton.getOuterCircle().getX();
//                float yDif = Math.round(yTouch) - joystickButton.getOuterCircle().getY();
//                if (abs(xDif) >= abs(yDif)) {
//                    if (xDif >= 0) {
//                        this.setCharacterState(CharacterState.MovingRight);
//                    } else {
//                        this.setCharacterState(CharacterState.MovingLeft);
//                    }
//                } else {
//                    if (yDif >= 0) {
//                        this.setCharacterState(CharacterState.MovingDown);
//                    } else {
//                        this.setCharacterState(CharacterState.MovingUp);
//                    }
//                }
//            } else {
//                this.setStationaryState();
//            }
//        }

//    }
}
