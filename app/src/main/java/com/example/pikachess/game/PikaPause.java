package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.PauseState;
import com.example.pikachess.game.pause.PikamonMenu;
import com.example.pikachess.game.pause.PikamonStatMenu;
import com.example.pikachess.game.pause.StartMenu;
import com.example.pikachess.game.pause.buttons.PikamonButton;

import java.util.ArrayList;
import java.util.List;

public class PikaPause {

    private StartMenu startMenu;
    private PauseState state;
    private PikamonMenu pikamonMenu;
    private PikamonStatMenu statMenu;
    private List<PikamonStatMenu> statMenus;
    private PlayerCharacter mainCharacter;
    private Context context;
    private int[] canvasDims;

    public PikaPause(Context context, int[] canvasDims, PlayerCharacter mainCharacter) {
        this.context = context;
        this.canvasDims = canvasDims;
        this.mainCharacter = mainCharacter;
        state = PauseState.Menu;
        startMenu = new StartMenu(context, canvasDims);
        pikamonMenu = new PikamonMenu(context, canvasDims, mainCharacter);
        statMenu = new PikamonStatMenu(context, canvasDims, mainCharacter.getPikamen().get(0));
    }

//    private void initialiseStatMenus() {
//        statMenus = new ArrayList<>();
//        for (Pikamon pikamon : mainCharacter.getPikamen()) {
//            statMenus.add(new PikamonStatMenu());
//        }
//    }

    public void draw(Canvas canvas) {
        if (state == PauseState.Menu) {
            startMenu.draw(canvas);
        }
        else if (state == PauseState.Pikamon) {
            pikamonMenu.draw(canvas);
        }
        else if (state == PauseState.PikamonStats) {
            statMenu.draw(canvas);
        }
    }

    public void setStatMenu(Pikamon pikamon) {
        statMenu = new PikamonStatMenu(context, canvasDims, pikamon);
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        if (state == PauseState.Menu) {
            startMenu.onTouchEvent(event, pikaGame);
        }
        else if (state == PauseState.Pikamon) {
            pikamonMenu.onTouchEvent(event, pikaGame);
        }
        else if (state == PauseState.PikamonStats) {
            statMenu.onTouchEvent(event, pikaGame);
        }

    }

    public void setPauseState(PauseState state) {
        this.state = state;
    }

    public PauseState getState() {
        return state;
    }

    public void update() {
        pikamonMenu.update();
    }

    public PikamonMenu getPikamonMenu() {
        return pikamonMenu;
    }

    public PikamonStatMenu getPikamonStatMenu() {
        return statMenu;
    }

    public void setStatState(Pikamon pikamon) {
        setPauseState(PauseState.PikamonStats);
        setStatMenu(pikamon);
    }
}
