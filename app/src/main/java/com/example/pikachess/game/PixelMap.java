package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.example.pikachess.R;

public class PixelMap {

    private Bitmap pixelImage;
    private PixelSquare[][] pixelSquares;
    private PixelSquare startingSquare;
    private int width, height;
    private Context context;

    public PixelMap(Context context) {
        this.context = context;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        pixelImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.littleroot_pixel_per_square, options);
        width = pixelImage.getWidth();
        height = pixelImage.getHeight();

        populatePixelSquareArray();
        setCanWalkForEachPixelSquare();
    }

    public PixelSquare getSquare(int x, int y) {
        return pixelSquares[x][y];
    }

    private void populatePixelSquareArray() {
        pixelSquares = new PixelSquare[width][height];
        for (int imageX = 0; imageX < width; imageX++) {
            for (int imageY = 0; imageY < height; imageY++) {
//                boolean walkable = pixelImage.getPixel(imageX, imageY) != context.getResources().getColor(R.color.black);
//                pixelSquares[imageX][imageY] = new PixelSquare(imageX, imageY, walkable);
                pixelSquares[imageX][imageY] = new PixelSquare(imageX, imageY);
                setWalkable(pixelSquares[imageX][imageY]);
                setStartingSquare(pixelSquares[imageX][imageY]);
            }
        }
    }

    private void setWalkable(PixelSquare pixelSquare) {
        pixelSquare.setWalkable(pixelImage.getPixel(pixelSquare.getX(), pixelSquare.getY()) != context.getResources().getColor(R.color.black));
    }

    private void setStartingSquare(PixelSquare pixelSquare) {
        if (pixelImage.getPixel(pixelSquare.getX(), pixelSquare.getY()) == context.getResources().getColor(R.color.green)) {
            pixelSquare.setStartingSquare(true);
            startingSquare = pixelSquare;
        }
    }

    public double[] getStartingPixelPosition(double bitmapResizeFactor) {
        int pixelX = startingSquare.getX();
        int pixelY = startingSquare.getY();
        double backgroundCentreX = ((double) pixelX + 1.0/2) * PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor;
        double backgroundCentreY = ((double) pixelY + 1.0/2) * PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor;

        double originalCentreXY = ((double) PikaGame.SQUARES_ACROSS_SCREEN/2) * PikaGame.GRID_SQUARE_SIZE * bitmapResizeFactor;

        return new double[] {originalCentreXY - backgroundCentreX, originalCentreXY - backgroundCentreY};
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
