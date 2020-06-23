package com.example.pikachess.game.pause.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.NPC;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;
import com.example.pikachess.game.pause.PauseState;

public class BackButton extends Button {

    private static final String TEXT = "Back";

    public BackButton(Context context, int[] location, int width, int height) {
        super(context, TEXT, location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch)) {

            if (pikaGame.getPikaPause().getState() == PauseState.Menu) {
                pikaGame.setGameState(PikaGameState.Roam);
                for (NPC npc : pikaGame.getNPCs()) {
                    npc.getSpriteSheet().setPause(false);
                }
            }
            else if (pikaGame.getPikaPause().getState() == PauseState.Pikamon) {
                pikaGame.getPikaPause().setPauseState(PauseState.Menu);
            }
            else if (pikaGame.getPikaPause().getState() == PauseState.PikamonStats) {
                pikaGame.getPikaPause().setPauseState(PauseState.Pikamon);
            }

        }
    }


}
