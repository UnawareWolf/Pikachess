package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pikachess.R;
import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.battle.pikamen.BattleMenu;

public class PikaBattle {

    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
    //private BattleMenu battleMenu;
//    private Bitmap grassBattleBackground;
    private Rect backgroundRect;
    private BattleBackground grassBattleBackground;

    private boolean playerTurn;
    private boolean battleOver;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int canvasWidth, int canvasHeight) {
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        //battleMenu = new BattleMenu();
        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();
        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon.getHp(), true, canvasWidth);
        opponentHealthBar = new HealthBar(context, opponentPikamon.getHp(), false, canvasWidth);
//        grassBattleBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass_battle_background);
        grassBattleBackground = new BattleBackground(context, canvasWidth, canvasHeight);
    }

    public void onTouch() {
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

    public void draw(Canvas canvas) {
//        canvas.drawBitmap(grassBattleBackground, 0, 0, null);
        grassBattleBackground.draw(canvas);
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
