package com.example.pikachess.game.battle.pikamen;

import android.content.Context;

import com.example.pikachess.R;
import com.example.pikachess.game.PikaSprite;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.battle.attackMoves.FatPat;
import com.example.pikachess.game.battle.attackMoves.Pound;
import com.example.pikachess.game.battle.attackMoves.Scratch;
import com.example.pikachess.game.battle.attackMoves.TreadmillTrod;
import com.example.pikachess.game.battle.types.Normal;

public class Lotad extends Pikamon {

    private static final int HP = 40;
    private static final int ATTACK = 30;
    private static final int DEFENSE = 30;
    private static final int SPEED = 30;
    private static final int EXP = 60;

    public Lotad(Context context, boolean playerPikamon, int[] canvasDims, int level) {
        super(playerPikamon, canvasDims);
        this.level = level;
        baseEXP = EXP;
        baseHP = HP;
        baseAttack = ATTACK;
        baseDefense = DEFENSE;
        baseSpeed = SPEED;
        exp = 0;

        types.add(new Normal());
        imageID = R.drawable.lotad;
        image = new PikaSprite(context, this, 2);

        calculateStats();
        assignAttacks();
    }

    @Override
    protected void assignAttacks() {
        attacks = new AttackMove[2];
        attacks[0] = new Scratch();
        attacks[1] = new Pound();
    }
}
