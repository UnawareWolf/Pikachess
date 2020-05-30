package com.example.pikachess.game;

import android.content.Context;

import com.example.pikachess.R;

public class PixelSquare {

    private boolean walkable, startingSquare;
    private int x, y;
    private boolean canWalkUp, canWalkLeft, canWalkDown, canWalkRight;

    public PixelSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getWalkable() {
        return walkable;
    }

    public void setCanWalkUp(boolean canWalkUp) {
        this.canWalkUp = canWalkUp;
    }

    public void setCanWalkLeft(boolean canWalkLeft) {
        this.canWalkLeft = canWalkLeft;
    }

    public void setCanWalkDown(boolean canWalkDown) {
        this.canWalkDown = canWalkDown;
    }

    public void setCanWalkRight(boolean canWalkRight) {
        this.canWalkRight = canWalkRight;
    }

    public boolean canWalkInDirection(CharacterState joystickState) {
        boolean canWalk = false;
        if (joystickState == CharacterState.MovingLeft) {
            canWalk = canWalkLeft;
        }
        else if (joystickState == CharacterState.MovingUp) {
            canWalk = canWalkUp;
        }
        else if (joystickState == CharacterState.MovingRight) {
            canWalk = canWalkRight;
        }
        else if (joystickState == CharacterState.MovingDown) {
            canWalk = canWalkDown;
        }
        return canWalk;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void setStartingSquare(boolean startingSquare) {
        this.startingSquare = startingSquare;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean canWalkUp() {
        return canWalkUp;
    }

    public boolean canWalkLeft() {
        return canWalkLeft;
    }

    public boolean canWalkDown() {
        return canWalkDown;
    }

    public boolean canWalkRight() {
        return canWalkRight;
    }

    public double getXOnScreen(double bitmapResizeFactor) {
        return getOnScreenPosition(bitmapResizeFactor)[0];
    }

    public double getYOnScreen(double bitmapResizeFactor) {
        return getOnScreenPosition(bitmapResizeFactor)[1];
    }

    private double[] getOnScreenPosition(double bitmapResizeFactor) {
        double backgroundCentreX = ((double) x + 1.0/2) * PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor;
        double backgroundCentreY = ((double) y + 1.0/2) * PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor;

        return new double[] {backgroundCentreX, backgroundCentreY};
    }
}
