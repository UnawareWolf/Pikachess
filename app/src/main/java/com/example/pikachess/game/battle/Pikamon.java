package com.example.pikachess.game.battle;

import android.graphics.Canvas;

import com.example.pikachess.game.SpriteSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Pikamon {

    protected int hp, attack, defense, speed;
    protected int maxHP;
    protected int baseHP, baseAttack, baseDefense, baseSpeed;
    protected int baseEXP;
    protected int exp, targetExp;
    protected boolean playerPikamon;
    protected int imageID;
    protected int[] canvasDims;
    protected int level;
    protected PikaType type;
    protected Map<String, Integer> ivs;

    protected SpriteSheet image;
    protected Random rand;
    protected AttackMove[] attacks;

    protected abstract void assignAttacks();

    public Pikamon(boolean playerPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.canvasDims = canvasDims;
        rand = new Random();
        createIVs();
    }

    public void attackedBy(Pikamon attackingPikamon, AttackMove attackMove) {
        int damage = (int) (((((2 * level / 5 + 2) * attackingPikamon.getAttackStat() * attackMove.getDamage() / defense)
                        / 50) + 2) * attackingPikamon.getSTAB(attackMove) * getEffectiveness() * (85 + rand.nextInt(15)) / 100);
        hp = hp - damage;
//        hp = hp - attackMove.getDamage();
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

    public int getAttackStat() {
        return attack;
    }

    public double getSTAB(AttackMove attackMove) {
        double stab = 1;
        if (type == attackMove.getType()) {
            stab = 1.5;
        }
        return stab;
    }

    public double getEffectiveness() {
        return 1.0;
    }

    public int getExpGain() {
        return baseEXP * level / 7;
    }

    public void updateExp(int expGain) {
        exp = exp + expGain;
        if (exp >= targetExp) {
            exp = exp - targetExp;
            increaseLevel();
            updateExp(0);
        }
    }

    private void increaseLevel() {
        level++;
        calculateStats();
    }

    protected void calculateStats() {
        hp = (int) getStatFromIVs("HP", baseHP) + 5 + level;
        attack = (int) getStatFromIVs("Attack", baseAttack);
        defense = (int) getStatFromIVs("Defense", baseDefense);
        speed = (int) getStatFromIVs("Speed", baseSpeed);
        targetExp = (int) (Math.pow(level, 3) - Math.pow(level - 1, 3));
        maxHP = hp;
    }

    private double getStatFromIVs(String ivKey, int baseStat) {
        return ((ivs.get(ivKey) + 2.0 * baseStat) * level / 100.0) + 5;
    }

    private void createIVs() {
        ivs = new HashMap<>();
        ivs.put("HP", rand.nextInt(32));
        ivs.put("Attack", rand.nextInt(32));
        ivs.put("Defense", rand.nextInt(32));
        ivs.put("Speed", rand.nextInt(32));
    }

    public int getMaxHP() {
        return maxHP;
    }

    public boolean getIsPlayer() {
        return playerPikamon;
    }

    public int getBaseExp() {
        return baseEXP;
    }

    public int getLevel() {
        return level;
    }

}
