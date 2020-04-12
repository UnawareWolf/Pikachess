package com.example.gravity;

public class SquareBounds {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public SquareBounds(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getLeft() {return left;}
    public int getTop() {return top;}
    public int getRight() {return right;}
    public int getBottom() {return bottom;}

    public boolean chessSquareContainsCoordinates(SquareBounds square, float xLoc, float yLoc) {
        boolean contained;
        contained = xLoc > square.left && xLoc < square.right && yLoc < square.bottom && yLoc > square.top;
        return contained;
    }

}
