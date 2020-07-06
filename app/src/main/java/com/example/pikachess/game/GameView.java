package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private PikaGame pikaGame;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (pikaGame == null) {
            pikaGame = new PikaGame(this.getContext(), this);
            thread.setRunning(true);
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        stopThread();
//        thread = null;
    }

    public void update() {
        pikaGame.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            pikaGame.drawGame(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pikaGame.onTouchEvent(event);
        return true;
    }

    private void stopThread() {
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

    public void pause() {
        stopThread();
        thread = null;
    }

    public void resume() {
        if (thread == null) {
            thread = new MainThread(getHolder(),this);
            thread.setRunning(true);
            thread.start();
        }
    }
}
