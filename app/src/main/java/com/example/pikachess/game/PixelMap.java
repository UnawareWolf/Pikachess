package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.example.pikachess.R;

public class PixelMap {

    private Bitmap pixelImage;
    private PixelSquare[][] pixelSquares;
    private int width, height;

    public PixelMap(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        pixelImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.littleroot_pixel_per_square, options);
        width = pixelImage.getWidth();
        height = pixelImage.getHeight();

        populatePixelSquareArray(context);
        setCanWalkForEachPixelSquare();
    }

    public PixelSquare getSquare(int x, int y) {
        return pixelSquares[x][y];
    }

    private void populatePixelSquareArray(Context context) {
        pixelSquares = new PixelSquare[width][height];
        for (int imageX = 0; imageX < width; imageX++) {
            for (int imageY = 0; imageY < height; imageY++) {
                boolean walkable = pixelImage.getPixel(imageX, imageY) != context.getResources().getColor(R.color.black);
                pixelSquares[imageX][imageY] = new PixelSquare(imageX, imageY, walkable);
            }
        }
    }

    private void setCanWalkForEachPixelSquare() {
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                setInitialCanWalkLeft(xx, yy);
                setInitialCanWalkUp(xx, yy);
                setInitialCanWalkRight(xx, yy);
                setInitialCanWalkDown(xx, yy);
            }
        }
    }

    private void setInitialCanWalkLeft(int x, int y) {
        boolean canWalkLeft = false;
        if (x > 0 && pixelSquares[x - 1][y].getWalkable()) {
            canWalkLeft = true;
        }
        pixelSquares[x][y].setCanWalkLeft(canWalkLeft);
    }

    private void setInitialCanWalkUp(int x, int y) {
        boolean canWalkUp = false;
        if (y > 0 && pixelSquares[x][y - 1].getWalkable()) {
            canWalkUp = true;
        }
        pixelSquares[x][y].setCanWalkUp(canWalkUp);
    }

    private void setInitialCanWalkRight(int x, int y) {
        boolean canWalkRight = false;
        if (x + 1 < width && pixelSquares[x + 1][y].getWalkable()) {
            canWalkRight = true;
        }
        pixelSquares[x][y].setCanWalkRight(canWalkRight);
    }

    private void setInitialCanWalkDown(int x, int y) {
        boolean canWalkDown = false;
        if (y + 1 < height && pixelSquares[x][y + 1].getWalkable()) {
            canWalkDown = true;
        }
        pixelSquares[x][y].setCanWalkDown(canWalkDown);
    }

    public PixelSquare getSquareFromBackgroundLocation(double x, double y, double bitmapResizeFactor) {
        double pixelMapXCoordinate = x / (PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor);
        double pixelMapYCoordinate = y / (PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor);
        return getSquare((int) pixelMapXCoordinate, (int) pixelMapYCoordinate);
    }
}
