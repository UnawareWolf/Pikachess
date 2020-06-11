package com.example.pikachess.game.battle;

import android.graphics.Canvas;

import com.example.pikachess.game.SpriteSheet;

import java.util.Random;

public abstract class Pikamon {

    protected int hp;
//    protected int attackDamage;
    protected int speed;
    protected boolean playerPikamon;
    protected int imageID;
    protected int[] canvasDims;

    protected SpriteSheet image;
    protected Random rand;
    protected AttackMove[] attacks;

    protected abstract void assignAttacks();

    public Pikamon(boolean playerPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.canvasDims = canvasDims;
    }

    public void attackedBy(Pikamon attackingPikamon, AttackMove attackMove) {
//        hp = hp - attackingPikamon.getAttackDamage();
        hp = hp - attackMove.getDamage();
        if (hp < 0) {
            hp = 0;
        }
    }

//    public int getAttackDamage() {
//        return attackDamage;
//    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void draw(Canvas canvas) {
        image.draw(canvas);
    }

    public int getImageID() {
        return imageID;
    }

    public boolean isPlayerPikamon() {
        return playerPikamon;
    }

    public int[] getCanvasDims() {
        return canvasDims;
    }

    public AttackMove[] getAttacks() {
        return attacks;
    }

    public AttackMove getRandomAttack() {
        return attacks[rand.nextInt(attacks.length)];
    }
}
