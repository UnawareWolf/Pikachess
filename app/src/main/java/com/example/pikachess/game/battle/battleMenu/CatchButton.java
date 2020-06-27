package com.example.pikachess.game.battle.battleMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;
import com.example.pikachess.game.battle.PikaBattle;
import com.example.pikachess.game.battle.Pikamon;

public class CatchButton extends Button {

    private Pikamon opponentPikamon;

    public CatchButton(Context context, int[] location, int width, int height, Pikamon opponentPikamon) {
        super(context, "Catch", location, width, height);
        this.opponentPikamon = opponentPikamon;
    }

    @Override
    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (contains((int) xTouch, (int) yTouch) && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (pikaGame.getMainCharacter().getPikamen().size() < 6) {
                pikaGame.getMainCharacter().getPikamen().add(opponentPikamon);
                opponentPikamon.setPlayerPikamonAndUpdateSprite(true);
                pikaGame.setGameState(PikaGameState.Roam);
            }
        }
    }

}
