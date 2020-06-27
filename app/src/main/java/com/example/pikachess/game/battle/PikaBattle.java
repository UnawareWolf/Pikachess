package com.example.pikachess.game.battle;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.BattleBackground;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.battleMenu.AttackMenu;

public class PikaBattle {

//    private Context context;
    private Pikamon playerPikamon;
    private Pikamon opponentPikamon;
    private HealthBar playerHealthBar;
    private HealthBar opponentHealthBar;
    private BattleMenu battleMenu;
    private BattleBackground grassBattleBackground;
//    private BattleState battleState;
//    private Button attackButton;
//    private AttackButton[] attackButtons;
//    private AttackMove playerAttack;
//    private BattleText battleText;
//    private String currentAttackName;
    private AttackMenu attackMenu;
    private BattleMenuState battleMenuState;

    private int[] canvasDims;
//    private boolean playerTurn;
    private boolean battleOver;
//    private boolean playerWon;
//    private boolean bothAttacksDone;
//    private boolean displayedFinalAttack;

    public PikaBattle(Context context, Pikamon playerPikamon, Pikamon opponentPikamon, int[] canvasDims) {
//        this.context = context;
        this.playerPikamon = playerPikamon;
        this.opponentPikamon = opponentPikamon;
        this.canvasDims = canvasDims;
//        bothAttacksDone = false;
//        displayedFinalAttack = false;
        battleMenu = new BattleMenu(context, canvasDims, this);
        attackMenu = new AttackMenu(context, this);
//        battleState = BattleState.Action;
        battleMenuState = BattleMenuState.Menu;

//        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();
        battleOver = false;
        playerHealthBar = new HealthBar(context, playerPikamon);
        opponentHealthBar = new HealthBar(context, opponentPikamon);
        grassBattleBackground = new BattleBackground(context, canvasDims);

        initialiseAttackButtons();
//        battleText = new BattleText(context, new int[]{canvasDims[0] / 4, 5 * canvasDims[1] / 8});
    }

    public void onTouch(MotionEvent event, PikaGame pikaGame) {
//        if (battleState == BattleState.Action) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                for (AttackButton attackButton : attackButtons) {
//                    if (attackButton.contains((int) event.getX(), (int) event.getY())){
//                        playerAttack = attackButton.getAttack();
//                        executeTurn();
//                        if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
//                            setBattleStateOver();
//                        }
//                        else {
//                            battleState = BattleState.DisplayText;
//                        }
//                    }
//                }
//            }
//        }
//        else if (battleState == BattleState.DisplayText) {
//            if (bothAttacksDone) {
//                battleState = BattleState.Action;
//                bothAttacksDone = !bothAttacksDone;
//            }
//            else  {
//                bothAttacksDone = !bothAttacksDone;
//                executeTurn();
//            }
//            if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
//                setBattleStateOver();
//            }
//        }
//        else if (battleState == BattleState.BattleOver) {
//            if (displayedFinalAttack) {
//                battleOver = true;
//            }
//            else {
//                displayedFinalAttack = true;
//            }
//        }
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
//        drawStateDependentContent(canvas);
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

//    public void executeTurn() {
//        if (playerTurn) {
//            executePlayerTurn();
//        }
//        else {
//            executeOpponentTurn();
//        }
//        playerHealthBar.update();
//        opponentHealthBar.update();
//    }

//    private void setBattleStateOver() {
//        if (opponentPikamon.getHp() <= 0 && playerPikamon.getHp() > 0) {
//            playerPikamon.updateExp(opponentPikamon.getExpGain());
//        }
//
//        battleState = BattleState.BattleOver;
//
//    }

    private void initialiseAttackButtons() {
//        attackButtons = new AttackButton[playerPikamon.getAttacks().length];
//        int attackIndex = 0;
//        for (AttackMove attack : playerPikamon.getAttacks()) {
//            attackButtons[attackIndex] = new AttackButton(context, attack, new int[]{attackIndex * (int) ((float) AttackButton.WIDTH * 1.2) + canvasDims[0] / 3, canvasDims[1] / 2});
//            attackIndex++;
//        }
    }

//    private void executePlayerTurn() {
//        opponentPikamon.attackedBy(playerPikamon, playerAttack);
//        currentAttackName = playerAttack.getName();
//        changeTurn();
//    }
//
//    private void executeOpponentTurn() {
//        AttackMove opponentAttack = opponentPikamon.getRandomAttack();
//        playerPikamon.attackedBy(opponentPikamon, opponentAttack);
//        currentAttackName = opponentAttack.getName();
//        changeTurn();
//    }

//    private void changeTurn() {
//        playerTurn = !playerTurn;
//    }

//    private void drawStateDependentContent(Canvas canvas) {
//        if (battleState == BattleState.Action) {
//            drawAttackMenu(canvas);
//        }
//        else if (battleState == BattleState.DisplayText) {
//            drawAttackText(canvas);
//        }
//        else if (battleState == BattleState.BattleOver) {
//            drawAttackOrBattleOverText(canvas);
//        }
//    }
//
    private void drawHealthBars(Canvas canvas) {
        playerHealthBar.draw(canvas);
        opponentHealthBar.draw(canvas);
    }
//
//    private void drawAttackMenu(Canvas canvas) {
////        for (AttackButton attackButton : attackButtons) {
////            attackButton.draw(canvas);
////        }
//    }
//
//    private void drawAttackText(Canvas canvas) {
////        battleText.draw(canvas, BattleText.TextType.AttackTurn, !playerTurn, currentAttackName);
//        battleText.draw(canvas, BattleText.TextType.AttackTurn, !playerTurn, "");
//    }
//
//    private void drawBattleOverText(Canvas canvas) {
//        battleText.draw(canvas, BattleText.TextType.Faint, playerTurn);
//    }
//
//    private void drawAttackOrBattleOverText(Canvas canvas) {
//        if (!displayedFinalAttack) {
//            drawAttackText(canvas);
//        }
//        else {
//            drawBattleOverText(canvas);
//        }
//    }

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
