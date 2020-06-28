package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.battleMenu.AttackMenu;

public class PikaBattle {

    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
    private BattleMenu battleMenu;
    private BattleBackground grassBattleBackground;
    private AttackMenu attackMenu;
    private BattleMenuState battleMenuState;

    private int[] canvasDims;
    private boolean battleOver;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        this.canvasDims = canvasDims;

        battleMenu = new BattleMenu(context, canvasDims, this);
        attackMenu = new AttackMenu(context, this);
        battleMenuState = BattleMenuState.Menu;

        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon, new int[] {canvasDims[0] / 2 + 120, canvasDims[0] / 2}, true);
        opponentHealthBar = new HealthBar(context, opponentPikamon, new int[] {canvasDims[0] / 2 - 120, 120}, true);
        grassBattleBackground = new BattleBackground(context, canvasDims);
    }

    public void onTouch(MotionEvent event, PikaGame pikaGame) {
        switch (battleMenuState) {
            case Menu:
                battleMenu.onTouchEvent(event, pikaGame);
                break;
            case Attack:
                attackMenu.onTouchEvent(event, pikaGame);
                break;
        }
    }

    public void draw(Canvas canvas) {
        grassBattleBackground.draw(canvas);
        playerPikamon.draw(canvas);
        opponentPikamon.draw(canvas);
        drawHealthBars(canvas);

        switch (battleMenuState) {
            case Menu:
                battleMenu.draw(canvas);
                break;
            case Attack:
                attackMenu.draw(canvas);
                break;
        }
    }

    public void setBattleOver(boolean battleOver) {
        this.battleOver = battleOver;
    }

    public boolean getBattleOver() {
        return battleOver;
    }

    private void drawHealthBars(Canvas canvas) {
        playerHealthBar.draw(canvas);
        opponentHealthBar.draw(canvas);
    }

    public BattleMenu getBattleMenu() {
        return battleMenu;
    }

    public Pikamon getPlayerPikamon() {
        return playerPikamon;
    }

    public Pikamon getOpponentPikamon() {
        return opponentPikamon;
    }

    public void setBattleMenuState(BattleMenuState battleMenuState) {
        this.battleMenuState = battleMenuState;
    }

    public int[] getCanvasDims() {
        return canvasDims;
    }

    public HealthBar getPlayerHealthBar() {
        return playerHealthBar;
    }

    public HealthBar getOpponentHealthBar() {
        return opponentHealthBar;
    }

    public BattleMenuState getBattleMenuState() {
        return battleMenuState;
    }
}
