package com.example.pikachess.game.pause;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.BasicButton;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.HealthBar;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.pause.buttons.BackButton;
import com.example.pikachess.game.pause.buttons.PikamonImageButton;

public class PikamonStatMenu {

    private static final int SCREEN_BORDER = 40;
    private static final String[] statNames = {"HP", "Attack", "Defense", "Speed"};

    private float left, top, right, bottom;
    private int buttonHeight, buttonWidth, buttonX, buttonY, containerBorder;
    private int backButtonHeight;
    private RectF containerRect;
    private Paint containerPaint, containerBorderPaint;

    private Pikamon pikamon;
    private BackButton backButton;
    private PikamonImageButton imageButton;
    private int[] imageLocation;
    private int imageWidth, imageHeight;
    private BasicButton[] attackButtons;
    private Context context;
    private HealthBar healthBar;
    private String hpText, expText, levelText, nameText;
    private String[] stats;
//    private String[] battleStats;
    private Paint textPaint;
    private int attackButtonWidth, attackButtonHeight, attackButtonX, attackButtonY;
    private int[] canvasDims;

    public PikamonStatMenu(Context context, int[] canvasDims, Pikamon pikamon) {
        this.context = context;
        this.canvasDims = canvasDims;
        containerBorder = SCREEN_BORDER / 2;
        buttonHeight = canvasDims[1] / 5;
        backButtonHeight = buttonHeight / 4;

        this.pikamon = pikamon;

        left = SCREEN_BORDER;
        right = canvasDims[0] - SCREEN_BORDER;
        top = SCREEN_BORDER;
        bottom = top + 3 * buttonHeight + (5) * containerBorder + backButtonHeight;

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
        buttonX = (int) (left + (right - left) / 2) - buttonWidth / 2 - SCREEN_BORDER / 4;
        buttonY = (int) (top + buttonHeight / 2 + containerBorder);

        backButton = new BackButton(context, new int[]{(int) (right - SCREEN_BORDER / 2 - buttonWidth / 6), (int) (bottom - SCREEN_BORDER / 2 - backButtonHeight / 2)}, buttonWidth / 3, backButtonHeight);
//        pikamon.getPikaSprite().setMenuPos(left, top, right, bottom);
        initialiseAttackButtons();
        stats = new String[7];
        textPaint = new Paint();
        textPaint.setTextSize(44);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        updateStats();

//        battleStats = new String[3];
//        updateBattleStats();
        calculateImagePosition();

//        healthBar = new HealthBar(context, pikamon, new int[] {imageLocation[0] + imageWidth - SCREEN_BORDER / 2, SCREEN_BORDER * 3}, false);
        healthBar = new HealthBar(context, pikamon, new int[] {SCREEN_BORDER * 6, imageLocation[1] + imageHeight * 2 / 3}, false);
        healthBar.setWriteHPText(false);

        imageButton = new PikamonImageButton(context, pikamon, imageLocation, imageWidth, imageHeight, false);

    }

    private void initialiseAttackButtons() {
        attackButtonWidth = (int) (right - left - SCREEN_BORDER) / 2 - SCREEN_BORDER / 4;
        attackButtonHeight = (int) ((bottom - top) - SCREEN_BORDER * 2) / 8;
        attackButtonX = (int) (left + (right - left) / 2) - buttonWidth / 2 - SCREEN_BORDER / 4;
        attackButtonY = (int) (bottom - backButtonHeight - SCREEN_BORDER * 1.5 - attackButtonHeight * 1.5);

        attackButtons = new BasicButton[4];
        int count = 0;
        for (AttackMove attackMove : pikamon.getAttacks()) {
            attackButtons[count] = new BasicButton(context, attackMove.getName(), new int[] {attackButtonX, attackButtonY}, attackButtonWidth, attackButtonHeight);
            changeButtonPosition(count);
            count++;
        }
        while (count < 4) {
            attackButtons[count] = new BasicButton(context, "", new int[]{attackButtonX, attackButtonY}, attackButtonWidth, attackButtonHeight);
            changeButtonPosition(count);
            count++;
        }
    }

    private void updateStats() {
//        nameText = "Breed: " + pikamon.getClass().getName();
//        levelText = "Lvl: " + pikamon.getLevel();
//        hpText = "HP: " + pikamon.getHp() + "/" + pikamon.getMaxHP();
//        expText = "Exp: " + pikamon.getExp() + "/" + pikamon.getTargetEXP();

        String[] nameSplit = pikamon.getClass().getName().split("\\.");
        stats[0] = nameSplit[nameSplit.length - 1];
        stats[1] = "Lvl: " + pikamon.getLevel();
        stats[2] = "Exp: " + pikamon.getExp() + "/" + pikamon.getTargetEXP();
        stats[3] = "HP: " + pikamon.getHp() + "/" + pikamon.getMaxHP();
        stats[4] = "Attack: " + pikamon.getAttackStat();
        stats[5] = "Defense: " + pikamon.getDefense();
        stats[6] = "Speed: " + pikamon.getSpeed();
    }

//    private void updateBattleStats() {
//        battleStats[0] = "Attack: " + pikamon.getAttackStat();
//        battleStats[1] = "Defense: " + pikamon.getDefense();
//        battleStats[2] = "Speed: " + pikamon.getSpeed();
//    }

    private void changeButtonPosition(int buttonCount) {
        if (buttonCount % 2 == 0) {
            attackButtonX += attackButtonWidth + SCREEN_BORDER / 2;
        }
        else {
            attackButtonX -= attackButtonWidth + SCREEN_BORDER / 2;
            attackButtonY += attackButtonHeight + SCREEN_BORDER / 2;
        }
    }

    private void calculateImagePosition() {
        float imageLeft = left + SCREEN_BORDER;
        float imageRight = imageLeft + (right - left) / 3;
        float imageTop = top + SCREEN_BORDER;
        float imageBottom = imageTop + imageRight - imageLeft;
        imageLocation = new int[] {(int) ((imageRight + imageLeft) / 2), (int) ((imageTop + imageBottom) / 2)};
        imageWidth = (int) (imageRight - imageLeft);
        imageHeight = imageWidth;
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(containerRect, 30, 30, containerPaint);
        canvas.drawRoundRect(containerRect, 30, 30, containerBorderPaint);
        backButton.draw(canvas);
        imageButton.draw(canvas);
        healthBar.draw(canvas);
        int mainTextCount = 0;
        for (String mainStat : stats) {
            canvas.drawText(mainStat, imageLocation[0] * 2, SCREEN_BORDER * (2f + mainTextCount) * 3 / 2f, textPaint);
            mainTextCount++;
        }
//        int battleStatCount = 0;
//        for (String battleStat : battleStats) {
////            canvas.drawText(battleStat, SCREEN_BORDER * 3, imageLocation[1] + imageHeight * 2 / 3f + SCREEN_BORDER * 3 / 2f * battleStatCount, textPaint);
//            canvas.drawText(battleStat, imageLocation[0] * 2, SCREEN_BORDER * (2f + mainTextCount) * 3 / 2f, textPaint);
//            mainTextCount++;
//            battleStatCount++;
//        }
        for (BasicButton attackButton : attackButtons) {
            attackButton.draw(canvas);
        }



    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        backButton.onTouchEvent(event, pikaGame);
    }

    public void refresh() {
        healthBar.update();
        updateStats();
//        updateMainStatText();
    }

}
