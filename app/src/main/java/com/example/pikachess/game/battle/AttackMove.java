package com.example.pikachess.game.battle;

public abstract class AttackMove {

    protected String name;
    protected int damage;
    protected PikaType type;

    public AttackMove(String name, int damage, PikaType type) {
        this.name = name;
        this.damage = damage;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public PikaType getType() {
        return type;
    }

}
