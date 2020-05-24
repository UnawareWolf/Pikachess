package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;

import static java.lang.Math.abs;

public class GameCharacter {

    //private boolean isMoving;
    //private Bitmap coreSpriteSheet;
    private CharacterSpriteSheet spritesheet;
    private CharacterState characterState;
    private CharacterState stateAccordingToJoystick;

    private int gridSquareSize;
    private double x, y, speed;
    private double xVel, yVel;
    private double distTravelled;
    private int canvasWidth;
    private double bitmapResizeFactor;

    public GameCharacter(Context context, PikaGame pikaGame) {
        canvasWidth = pikaGame.getCanvasWidth();
        bitmapResizeFactor = pikaGame.getBitmapResizeFactor();
        //gridSquareSize = PikaGame.GRID_SQUARE_SIZE;
        gridSquareSize = pikaGame.getPixelsAcrossSquare();
        spritesheet = new CharacterSpriteSheet(context, this);
        characterState = CharacterState.StationaryDown;
        stateAccordingToJoystick = CharacterState.StationaryDown;
        distTravelled = 0;
        //speed = spritesheet.getNumberOfCyclesPerGridSquare();
        speed = (double) gridSquareSize / 10;
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

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public double getBitmapResizeFactor() {
        return bitmapResizeFactor;
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
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
}
