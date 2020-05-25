package com.example.pikachess.game;

import android.content.Context;

import com.example.pikachess.R;

public class PixelSquare {

    private boolean walkable;
    private int x, y;
    private boolean canWalkUp, canWalkLeft, canWalkDown, canWalkRight;

    public PixelSquare(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
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
}
