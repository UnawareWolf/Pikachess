package com.example.pikachess.game.battle;

public abstract class AttackMove {

    protected String name;
    protected int damage;
    protected PikaTypeID type;

    public AttackMove(String name, int damage, PikaTypeID type) {
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

    public PikaTypeID getTypeID() {
        return type;
    }

}
