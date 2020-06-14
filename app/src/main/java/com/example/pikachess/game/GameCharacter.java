package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;

import java.util.Random;

import static java.lang.Math.abs;

public abstract class GameCharacter {

    protected CharacterSpriteSheet spriteSheet;
    protected CharacterState characterState;
    protected PixelSquare currentSquare;
    protected PixelSquare nextSquare;
    protected PixelSquare lastSquare;
    protected PixelMap pixelMap;
    protected CharacterState stateAccordingToJoystick;
    protected Random rand;

    protected Context context;
    protected int gridSquareSize;
    protected int canvasWidth, canvasHeight;
    protected int waitTime;
    protected int timeToWait;
    protected double xMoved, yMoved, speed;
    protected double xVel, yVel;
    protected double distTravelled;
    protected double bitmapResizeFactor;
    protected double[] startingShift;

    public abstract void update();
    protected abstract void updateCharacterState();
    //protected abstract void updateCurrentSquare();
    protected abstract PixelSquare getCurrentSquareFromPixelMap();

    public GameCharacter(Context context, PikaGame pikaGame) {
        canvasWidth = pikaGame.getCanvasWidth();
        canvasHeight = pikaGame.getCanvasHeight();
        bitmapResizeFactor = pikaGame.getBitmapResizeFactor();
        gridSquareSize = pikaGame.getPixelsAcrossSquare();
        startingShift = pikaGame.getStartingShift();
        this.context = context;

        rand = new Random();
        timeToWait = rand.nextInt(40);

        waitTime = 0;
        characterState = CharacterState.StationaryDown;
        distTravelled = 0;

        speed = (double) gridSquareSize / 10;
        xMoved = 0;
        yMoved = 0;

        stateAccordingToJoystick = CharacterState.StationaryDown;

        pixelMap = pikaGame.getPixelMap();
//        updateCurrentSquare();
    }

    protected boolean stateFromJoystickIsWalkable() {
        return currentSquare.canWalkInDirection(stateAccordingToJoystick);
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

    protected boolean stateFromJoystickIsStationary() {
        boolean stationary = false;
        if (stateAccordingToJoystick == CharacterState.StationaryDown || stateAccordingToJoystick == CharacterState.StationaryLeft || stateAccordingToJoystick == CharacterState.StationaryUp || stateAccordingToJoystick == CharacterState.StationaryRight) {
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
            waitTime = 0;
        }
        else if (characterState == CharacterState.MovingUp){
            xVel = 0;
            yVel = -speed;
            waitTime = 0;
        }
        else if (characterState == CharacterState.MovingRight) {
            xVel = speed;
            yVel = 0;
            waitTime = 0;
        }
        else if (characterState == CharacterState.MovingDown){
            xVel = 0;
            yVel = speed;
            waitTime = 0;
        }
        else {
            waitTime++;
            xVel = 0;
            yVel = 0;
        }
        if (waitTime >= timeToWait) {
            waitTime = 0;
            timeToWait = rand.nextInt(40);
        }

        distTravelled = distTravelled + xVel + yVel;
        if (abs(distTravelled) >= gridSquareSize) {
            distTravelled = 0;
        }
        xMoved = xMoved + xVel;
        yMoved = yMoved + yVel;
    }

    public PixelSquare getCurrentSquare() {
        return currentSquare;
    }

    public void updateCurrentSquare() {
        if (currentSquare != null) {// doesn't this only happen the very first time?
            currentSquare = getCurrentSquareFromPixelMap();
            currentSquare.setWalkable(false);
            currentSquare.setGameCharacter(this);
            if (distTravelled == 0) {


                if (lastSquare != currentSquare) {
                    lastSquare.setWalkable(true);
                    lastSquare.setGameCharacter(null);// double check this
                    lastSquare = nextSquare;
                }

                nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
                nextSquare.setWalkable(false);
            }

        }
        else {
            currentSquare = getCurrentSquareFromPixelMap();
            lastSquare = currentSquare;
            nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
            currentSquare.setWalkable(false);
        }
    }

    public double getDistTravelled() {
        return distTravelled;
    }

    public CharacterState getCharacterState() {
        return characterState;
    }

}
