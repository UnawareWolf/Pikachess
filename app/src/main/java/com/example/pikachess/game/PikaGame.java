package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.R;

import java.util.EventListener;

public class PikaGame {

    private PikaGameState gameState;
    private GameCharacter mainCharacter;
    private GameBackground background;
    private JoystickButton joystickButton;
    //private Bitmap background;
    private boolean newGame;
    private int canvasWidth;


    public PikaGame(Context context, GameView gameView) {
        this.canvasWidth = gameView.getWidth();
        mainCharacter = new GameCharacter(context, canvasWidth);
        gameState = PikaGameState.Roam;
        background = new GameBackground(context, canvasWidth);
        joystickButton = new JoystickButton(context, gameView.getHeight());
        //background = BitmapFactory.decodeResource(context.getResources(), R.drawable.totodile);
    }

    public void update() {
        mainCharacter.update();
        background.update(mainCharacter);
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
        //canvas.drawBitmap(background, 0, 0, null);
        background.draw(canvas);
        mainCharacter.draw(canvas);
        joystickButton.draw(canvas);
    }

    public void onTouchEvent(MotionEvent event) {
        joystickButton.onTouchEvent(event, getMainCharacter());
    }

}