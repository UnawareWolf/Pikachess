package com.example.pikachess.game.battle;

public abstract class AttackMove {

    protected String name;
    protected int damage;

    public AttackMove(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

}
