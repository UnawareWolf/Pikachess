package com.example.pikachess.game.battle.pikamen;

import android.content.Context;

import com.example.pikachess.R;
import com.example.pikachess.game.PikaSprite;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.battle.attackMoves.FatPat;
import com.example.pikachess.game.battle.attackMoves.TreadmillTrod;
import com.example.pikachess.game.battle.types.Normal;

public class Wurmple extends Pikamon {
    private static final int HP = 45;
    private static final int ATTACK = 45;
    private static final int DEFENSE = 35;
    private static final int SPEED = 20;
    private static final int EXP = 60;

    public Wurmple(Context context, boolean playerPikamon, int[] canvasDims, int level) {
        super(playerPikamon, canvasDims);
        this.level = level;
        baseEXP = EXP;
        baseHP = HP;
        baseAttack = ATTACK;
        baseDefense = DEFENSE;
        baseSpeed = SPEED;
        exp = 0;

        types.add(new Normal());
        imageID = R.drawable.wurmple;
        image = new PikaSprite(context, this, 2);

        calculateStats();
        assignAttacks();
    }

    @Override
    protected void assignAttacks() {
        attacks = new AttackMove[2];
        attacks[0] = new FatPat();
        attacks[1] = new TreadmillTrod();
    }
}
