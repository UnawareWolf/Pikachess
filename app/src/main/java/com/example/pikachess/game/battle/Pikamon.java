package com.example.pikachess.game.battle;

import java.util.Random;

public abstract class Pikamon {

    protected int hp;
    protected int attackDamage;
    protected int speed;

    protected Random rand;

    public void attackedBy(Pikamon attackingPikamon) {
        hp = hp - attackingPikamon.getAttackDamage();
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }
}
