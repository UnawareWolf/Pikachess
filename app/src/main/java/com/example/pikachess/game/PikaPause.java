package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.pause.PauseState;
import com.example.pikachess.game.pause.PikamonMenu;
import com.example.pikachess.game.pause.StartMenu;

public class PikaPause {

    private StartMenu startMenu;
    private PauseState state;
    private PikamonMenu pikamonMenu;

    public PikaPause(Context context, int[] canvasDims, PlayerCharacter mainCharacter) {
        state = PauseState.Menu;
        startMenu = new StartMenu(context, canvasDims);
        pikamonMenu = new PikamonMenu(context, canvasDims, mainCharacter);
    }

    public void draw(Canvas canvas) {
        if (state == PauseState.Menu) {
            startMenu.draw(canvas);
        }
        else if (state == PauseState.Pikamon) {
            pikamonMenu.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        if (state == PauseState.Menu) {
            startMenu.onTouchEvent(event, pikaGame);
        }
        else if (state == PauseState.Pikamon) {
            pikamonMenu.onTouchEvent(event, pikaGame);
        }

    }

    public void setPauseState(PauseState state) {
        this.state = state;
    }

    public PauseState getState() {
        return state;
    }
}
