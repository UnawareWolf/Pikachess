package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.Button;

public class PikaBattle {

    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
//    private BattleMenu battleMenu;
    private BattleBackground grassBattleBackground;
    private BattleState battleState;
    private Button attackButton;
    private BattleText battleText;

    private boolean playerTurn;
    private boolean battleOver;
    private boolean playerWon;
    private boolean bothAttacksDone;
    private boolean displayedFinalAttack;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        bothAttacksDone = false;
        displayedFinalAttack = false;
        //battleMenu = new BattleMenu();
        battleState = BattleState.Action;
        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();
        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon.getHp(), true, canvasDims);
        opponentHealthBar = new HealthBar(context, opponentPikamon.getHp(), false, canvasDims);
        grassBattleBackground = new BattleBackground(context, canvasDims);

        attackButton = new Button(context, "ATTACK!", new int[]{canvasDims[0] / 3, canvasDims[1] / 2});

        battleText = new BattleText(context, new int[]{canvasDims[0] / 4, 5 * canvasDims[1] / 8});
    }

    public void onTouch(MotionEvent event) {
        if (battleState == BattleState.Action) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (attackButton.contains((int) event.getX(), (int) event.getY())){
                    executeTurn();
                    if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
                        playerWon = playerPikamon.getHp() <= 0;
                        battleState = BattleState.BattleOver;
                    }
                    else {
                        battleState = BattleState.DisplayText;
                    }

                }
            }
        }
        else if (battleState == BattleState.DisplayText) {
            if (bothAttacksDone) {
                battleState = BattleState.Action;
                bothAttacksDone = !bothAttacksDone;
            }
            else  {
                bothAttacksDone = !bothAttacksDone;
                executeTurn();
            }



            if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
                playerWon = playerPikamon.getHp() <= 0;
                battleState = BattleState.BattleOver;
            }


        }
        else if (battleState == BattleState.BattleOver) {
            if (displayedFinalAttack) {
                battleOver = true;
            }
            else {
                displayedFinalAttack = true;
            }
        }
    }

    public void draw(Canvas canvas) {
        grassBattleBackground.draw(canvas);
        playerPikamon.draw(canvas);
        opponentPikamon.draw(canvas);
        drawStateDependentContent(canvas);
        drawHealthBars(canvas);
    }

    public boolean getBattleOver() {
        return battleOver;
    }

    public void executeTurn() {
        if (playerTurn) {
            executePlayerTurn();
        }
        else {
            executeOpponentTurn();
        }
        playerHealthBar.update(playerPikamon.getHp());
        opponentHealthBar.update(opponentPikamon.getHp());
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

    private void drawStateDependentContent(Canvas canvas) {
        if (battleState == BattleState.Action) {
            drawAttackMenu(canvas);
        }
        else if (battleState == BattleState.DisplayText) {
            drawAttackText(canvas);
        }
        else if (battleState == BattleState.BattleOver) {
            drawAttackOrBattleOverText(canvas);
        }
    }

    private void drawHealthBars(Canvas canvas) {
        playerHealthBar.draw(canvas);
        opponentHealthBar.draw(canvas);
    }

    private void drawAttackMenu(Canvas canvas) {
        attackButton.draw(canvas);
    }

    private void drawAttackText(Canvas canvas) {
        battleText.draw(canvas, BattleText.TextType.AttackTurn, !playerTurn, "Fat Pat");
    }

    private void drawBattleOverText(Canvas canvas) {
        battleText.draw(canvas, BattleText.TextType.Faint, playerTurn);
    }

    private void drawAttackOrBattleOverText(Canvas canvas) {
        if (!displayedFinalAttack) {
            drawAttackText(canvas);
        }
        else {
            drawBattleOverText(canvas);
        }
    }

}
