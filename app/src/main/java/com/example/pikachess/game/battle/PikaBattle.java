package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.Button;

public class PikaBattle {

    private Context context;
    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
//    private BattleMenu battleMenu;
    private BattleBackground grassBattleBackground;
    private BattleState battleState;
//    private Button attackButton;
    private Button[] attackButtons;
    private AttackMove playerAttack;
    private BattleText battleText;
    private String currentAttackName;

    private int[] canvasDims;
    private boolean playerTurn;
    private boolean battleOver;
//    private boolean playerWon;
    private boolean bothAttacksDone;
    private boolean displayedFinalAttack;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int[] canvasDims) {
        this.context = context;
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        this.canvasDims = canvasDims;
        bothAttacksDone = false;
        displayedFinalAttack = false;
        //battleMenu = new BattleMenu();
        battleState = BattleState.Action;
        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();
        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon);
        opponentHealthBar = new HealthBar(context, opponentPikamon);
        grassBattleBackground = new BattleBackground(context, canvasDims);

        initialiseAttackButtons();
        battleText = new BattleText(context, new int[]{canvasDims[0] / 4, 5 * canvasDims[1] / 8});
    }

    public void onTouch(MotionEvent event) {
        if (battleState == BattleState.Action) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (Button attackButton : attackButtons) {
                    if (attackButton.contains((int) event.getX(), (int) event.getY())){
                        playerAttack = attackButton.getAttack();
                        executeTurn();
                        if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
                            setBattleStateOver();
                        }
                        else {
                            battleState = BattleState.DisplayText;
                        }
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
                setBattleStateOver();
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
        playerHealthBar.update();
        opponentHealthBar.update();
    }

    private void setBattleStateOver() {
        if (opponentPikamon.getHp() <= 0 && playerPikamon.getHp() > 0) {
            playerPikamon.updateExp(opponentPikamon.getExpGain());
        }

        battleState = BattleState.BattleOver;

    }

    private void initialiseAttackButtons() {
        attackButtons = new Button[playerPikamon.getAttacks().length];
        int attackIndex = 0;
        for (AttackMove attack : playerPikamon.getAttacks()) {
            attackButtons[attackIndex] = new Button(context, attack, new int[]{attackIndex * (int) ((float) Button.WIDTH * 1.2) + canvasDims[0] / 3, canvasDims[1] / 2});
            attackIndex++;
        }
    }

    private void executePlayerTurn() {
        opponentPikamon.attackedBy(playerPikamon, playerAttack);
        currentAttackName = playerAttack.getName();
        changeTurn();
    }

    private void executeOpponentTurn() {
        AttackMove opponentAttack = opponentPikamon.getRandomAttack();
        playerPikamon.attackedBy(opponentPikamon, opponentAttack);
        currentAttackName = opponentAttack.getName();
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
        for (Button attackButton : attackButtons) {
            attackButton.draw(canvas);
        }
    }

    private void drawAttackText(Canvas canvas) {
        battleText.draw(canvas, BattleText.TextType.AttackTurn, !playerTurn, currentAttackName);
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
