package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;

import static java.lang.Math.abs;

public abstract class GameCharacter {

    protected CharacterSpriteSheet spriteSheet;
    protected CharacterState characterState;
    protected PixelSquare currentSquare;

    protected int gridSquareSize;
    protected int canvasWidth;
    protected double xMoved, yMoved, speed;
    protected double xVel, yVel;
    protected double distTravelled;
    protected double bitmapResizeFactor;
    protected double[] startingShift;

    protected abstract void updateCharacterState();
    protected abstract void updateCurrentSquare();

    public GameCharacter(Context context, PikaGame pikaGame) {
        canvasWidth = pikaGame.getCanvasWidth();
        bitmapResizeFactor = pikaGame.getBitmapResizeFactor();
        gridSquareSize = pikaGame.getPixelsAcrossSquare();
        startingShift = pikaGame.getStartingShift();


        characterState = CharacterState.StationaryDown;
        distTravelled = 0;

        speed = (double) gridSquareSize / 10;
        xMoved = 0;
        yMoved = 0;

//        updateCurrentSquare();
    }

    public void update() {
        updateCharacterMotionAndPosition();
        updateCurrentSquare();
        updateCharacterState();
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public double getBitmapResizeFactor() {
        return bitmapResizeFactor;
    }

    public CharacterState getStationaryState() {
        CharacterState stationaryState;
        if (!isStationary()) {
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
        }
        else {
            stationaryState = characterState;
        }
        return stationaryState;
    }

    public boolean isStationary() {
        boolean stationary = false;
        if (characterState == CharacterState.StationaryDown || characterState == CharacterState.StationaryLeft || characterState == CharacterState.StationaryUp || characterState == CharacterState.StationaryRight) {
            stationary = true;
        }
        return stationary;
    }

    public double getXMoved() {
        return xMoved;
    }

    public double getYMoved() {
        return yMoved;
    }

    public void draw(Canvas canvas) {
        spriteSheet.draw(canvas, characterState);
    }

    protected void updateCharacterMotionAndPosition() {
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
        xMoved = xMoved + xVel;
        yMoved = yMoved + yVel;
    }

}
