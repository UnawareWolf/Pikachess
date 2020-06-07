package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.Button;

public class PikaBattle {

    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
//    private BattleMenu battleMenu;
//    private Bitmap grassBattleBackground;
    private Rect backgroundRect;
    private BattleBackground grassBattleBackground;
    private BattleState battleState;
    private Button attackButton;

    private boolean playerTurn;
    private boolean battleOver;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        //battleMenu = new BattleMenu();
        battleState = BattleState.Action;
        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();
        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon.getHp(), true, canvasDims);
        opponentHealthBar = new HealthBar(context, opponentPikamon.getHp(), false, canvasDims);
        grassBattleBackground = new BattleBackground(context, canvasDims);

        attackButton = new Button(context, "ATTACK!", new int[]{canvasDims[0] / 3, canvasDims[1] / 2});

    }

    public void onTouch(MotionEvent event) {
        if (battleState == BattleState.Action) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (attackButton.contains((int) event.getX(), (int) event.getY())){
                    if (playerTurn) {
                        executePlayerTurn();
                    }
                    else {
                        executeOpponentTurn();
                    }
                    if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
                        battleOver = true;
                    }
                    playerHealthBar.update(playerPikamon.getHp());
                    opponentHealthBar.update(opponentPikamon.getHp());


                }
            }

        }


    }

    public void draw(Canvas canvas) {
        grassBattleBackground.draw(canvas);
        playerPikamon.draw(canvas);
        opponentPikamon.draw(canvas);
        attackButton.draw(canvas);
        drawHealthBars(canvas);
    }

    public boolean getBattleOver() {
        return battleOver;
    }

    private void executePlayerTurn() {
        opponentPikamon.attackedBy(playerPikamon);
        changeTurn();
    }

    private void executeOpponentTurn() {
        playerPikamon.attackedBy(opponentPikamon);
        changeTurn();
    }

    private void changeTurn() {
        playerTurn = !playerTurn;
    }

    private void drawHealthBars(Canvas canvas) {
        playerHealthBar.draw(canvas);
        opponentHealthBar.draw(canvas);
    }
}
