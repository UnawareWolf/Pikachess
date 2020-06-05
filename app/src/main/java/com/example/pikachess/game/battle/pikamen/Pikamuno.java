package com.example.pikachess.game.battle.pikamen;

import com.example.pikachess.game.battle.Pikamon;

import java.util.Random;

public class Pikamuno extends Pikamon {

    private static final int HP = 60;
    private static final int ATTACK_DAMAGE = 25;
    private static final int SPEED_AVERAGE = 30;

    public Pikamuno() {
        rand = new Random();
        hp = HP;
        attackDamage = ATTACK_DAMAGE;
        speed = SPEED_AVERAGE - 6 + rand.nextInt(13);
    }

}
