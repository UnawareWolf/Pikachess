package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pikachess.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap background;
    private MainThread thread;
    //private CharacterSpritesheet character;
    private PikaGame pikaGame;
    private int canvasWidth;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        canvasWidth = getWidth();
        pikaGame = new PikaGame(this.getContext(), this);
        //character = new CharacterSpritesheet(BitmapFactory.decodeResource(getResources(), R.drawable.totodile), 0, 0);
        //background = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        //character.update();
        pikaGame.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            //canvas.drawBitmap(background, 0, 0, null);
            pikaGame.drawGame(canvas);
            //character.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pikaGame.onTouchEvent(event);
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            pikaGame.getMainCharacter().setCharacterState(CharacterState.MovingRight);
//        }
//        else if (event.getAction() == MotionEvent.ACTION_UP) {
//            pikaGame.getMainCharacter().setCharacterState(CharacterState.Stationary);
//        }

        return true;
    }
}
