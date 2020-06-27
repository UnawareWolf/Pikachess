package com.example.pikachess.game.battle.battleMenu;

import android.content.Context;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.NPC;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;
import com.example.pikachess.game.battle.BattleMenuState;
import com.example.pikachess.game.pause.PauseState;

public class BattleBackButton extends Button {

    public BattleBackButton(Context context, int[] location, int width, int height) {
        super(context, "Back", location, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch)) {
            if (pikaGame.getPikaBattle().getBattleMenuState() == BattleMenuState.Attack) {
                pikaGame.getPikaBattle().setBattleMenuState(BattleMenuState.Menu);
            }

        }
    }
}
