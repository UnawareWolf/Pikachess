package com.example.pikachess.game.battle.battleMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.BasicButton;
import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.AttackButton;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.BattleMenuState;
import com.example.pikachess.game.battle.BattleState;
import com.example.pikachess.game.battle.BattleText;
import com.example.pikachess.game.battle.PikaBattle;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.buttons.BackButton;

import java.util.ArrayList;
import java.util.List;

public class AttackMenu {

    private static final int SCREEN_BORDER = 40;

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;
    private BattleState battleState;
//    private AttackButton[] attackButtons;
    private AttackMove playerAttack;
    private List<Button> buttons;
    private Pikamon playerPikamon;

    private boolean playerTurn, bothAttacksDone, displayedFinalAttack;
    private Pikamon opponentPikamon;
    private String currentAttackName;
    private PikaBattle pikaBattle;
    private BattleText battleText;

    private int[] canvasDims;

    public AttackMenu(Context context, PikaBattle pikaBattle) {
        this.pikaBattle = pikaBattle;
        canvasDims = pikaBattle.getCanvasDims();
        playerPikamon = pikaBattle.getPlayerPikamon();
        opponentPikamon = pikaBattle.getOpponentPikamon();
        bothAttacksDone = false;
        displayedFinalAttack = false;
//        battleOver = false;
        containerBorder = SCREEN_BORDER / 2;

        playerTurn = playerPikamon.getSpeed() > opponentPikamon.getSpeed();

        battleState = BattleState.Action;

        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER + canvasDims[1] / 2f;
        bottom = top + canvasDims[1] / 3f;

        containerRect = new RectF(left, top, right, bottom);
        containerPaint = new Paint();
        containerPaint.setStyle(Paint.Style.FILL);
        containerPaint.setColor(context.getResources().getColor(R.color.buttonGrey));
        containerPaint.setAlpha(160);

        containerBorderPaint = new Paint();
        containerBorderPaint.setStyle(Paint.Style.STROKE);
        containerBorderPaint.setStrokeWidth(4);
        containerBorderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));


        buttonWidth = (int) (right - left - SCREEN_BORDER) / 2 - SCREEN_BORDER / 4;
        buttonHeight = (int) ((bottom - top) - SCREEN_BORDER * 2) / 3;
        buttonX = (int) (left + (right - left) / 2) - buttonWidth / 2 - SCREEN_BORDER / 4;
        buttonY = (int) (top + buttonHeight / 2 + containerBorder);
        battleText = new BattleText(context, new int[]{canvasDims[0] / 4, 5 * canvasDims[1] / 8});

        initialiseButtons(context);
    }

    private void initialiseButtons(Context context) {
//        buttons = new Button[4];
        buttons = new ArrayList<>();
        int buttonCount = 0;
        for (AttackMove attack : playerPikamon.getAttacks()) {
            buttons.add(new AttackButton(context, this, attack, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight));
            changeButtonPosition(buttonCount);
            buttonCount++;
        }
        while (buttons.size() < 4) {
            buttons.add(new BasicButton(context, "", new int[]{buttonX, buttonY}, buttonWidth, buttonHeight));
            changeButtonPosition(buttonCount);
            buttonCount++;
        }
        buttons.add(new BattleBackButton(context, new int[]{buttonX, buttonY}, buttonWidth, buttonHeight));
    }

    private void changeButtonPosition(int buttonCount) {
        if (buttonCount % 2 == 0) {
            buttonX += buttonWidth + SCREEN_BORDER / 2;
        }
        else {
            buttonX -= buttonWidth + SCREEN_BORDER / 2;
            buttonY += buttonHeight + SCREEN_BORDER / 2;
        }
    }

    public void draw(Canvas canvas) {
        drawStateDependentContent(canvas);
    }

    private void drawAttackMenu(Canvas canvas) {
        canvas.drawRoundRect(containerRect, 30, 30, containerPaint);
        canvas.drawRoundRect(containerRect, 30, 30, containerBorderPaint);
        for (Button button : buttons) {
            button.draw(canvas);
        }
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

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        if (battleState == BattleState.Action) {
            for (Button button : buttons) {
                button.onTouchEvent(event, pikaGame);
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
                pikaBattle.setBattleOver(true);
            }
            else {
                displayedFinalAttack = true;
            }
        }
//        for (Button button : buttons) {
//            button.onTouchEvent(event, pikaGame);
//        }
    }

    public void executeTurnAndUpdate(AttackMove attackMove) {
        playerAttack = attackMove;
        executeTurn();
        if (playerPikamon.getHp() <= 0 || opponentPikamon.getHp() <= 0) {
            setBattleStateOver();
        } else {
            battleState = BattleState.DisplayText;
        }
    }

    public void executeTurn() {
        if (playerTurn) {
            executePlayerTurn();
        }
        else {
            executeOpponentTurn();
        }
        pikaBattle.getPlayerHealthBar().update();
        pikaBattle.getOpponentHealthBar().update();
    }

    private void setBattleStateOver() {
        if (opponentPikamon.getHp() <= 0 && playerPikamon.getHp() > 0) {
            playerPikamon.updateExp(opponentPikamon.getExpGain());
        }

        battleState = BattleState.BattleOver;

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

}
