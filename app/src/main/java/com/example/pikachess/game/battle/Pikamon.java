package com.example.pikachess.game.battle;

import android.graphics.Canvas;

import com.example.pikachess.game.PikaSprite;
import com.example.pikachess.game.SpriteSheet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
    //protected PikaTypeID type;
    protected Map<String, Integer> ivs;

    protected Set<PikaType> types;
    protected PikaSprite image;
    protected Random rand;
    protected AttackMove[] attacks;

    protected abstract void assignAttacks();

    public Pikamon(boolean playerPikamon, int[] canvasDims) {
        this.playerPikamon = playerPikamon;
        this.canvasDims = canvasDims;
        rand = new Random();
        types = new HashSet<>();
        createIVs();
    }

    public void attackedBy(Pikamon attackingPikamon, AttackMove attackMove) {
        int damage = (int) (((((2 * attackingPikamon.getLevel() / 5 + 2) * attackingPikamon.getAttackStat() * attackMove.getDamage() / defense)
                        / 50) + 2) * attackingPikamon.getSTAB(attackMove) * getEffectiveness(attackMove) * (85 + rand.nextInt(16)) / 100);
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

    public void drawInMenu(Canvas canvas) {
        image.drawInMenu(canvas);
    }

    public PikaSprite getPikaSprite() {
        return image;
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
        for (PikaType type : types) {
            if (type.getID() == attackMove.getTypeID()) {
                stab = 1.5;
                break;
            }
        }
        return stab;
    }

    public double getEffectiveness(AttackMove attackMove) {
        double effectiveness = 1.0;
        for (PikaType type : types) {
            effectiveness *= type.getEffectivenessMultiplier(attackMove.getTypeID());
        }
        return effectiveness;
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
        int oldMaxHP = maxHP;
        maxHP = (int) getStatFromIVs("HP", baseHP) + 5 + level;
        attack = (int) getStatFromIVs("Attack", baseAttack);
        defense = (int) getStatFromIVs("Defense", baseDefense);
        speed = (int) getStatFromIVs("Speed", baseSpeed);
        targetExp = (int) (Math.pow(level, 3) - Math.pow(level - 1, 3));
        hp += maxHP - oldMaxHP;
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

    public void restoreHP() {
        hp = maxHP;
    }

    public int getExp() {
        return exp;
    }

    public int getTargetEXP() {
        return targetExp;
    }

}
