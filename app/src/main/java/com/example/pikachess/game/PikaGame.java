package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pikachess.R;

public class PikaGame {

    private PikaGameState gameState;
    private GameCharacter mainCharacter;
    private Bitmap background;
    private boolean newGame;
    private int canvasWidth;


    public PikaGame(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        mainCharacter = new GameCharacter(context, canvasWidth);
        gameState = PikaGameState.Roam;
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.totodile);
    }

    public void setGameState(PikaGameState gameState) {
        this.gameState = gameState;
    }

    public PikaGameState getGameState() {
        return this.gameState;
    }

    public GameCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    public void drawGame(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        mainCharacter.draw(canvas);
    }

}
