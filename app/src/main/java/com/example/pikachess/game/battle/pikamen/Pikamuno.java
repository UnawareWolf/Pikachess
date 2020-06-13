package com.example.pikachess.game.battle.pikamen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.pikachess.R;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaSprite;
import com.example.pikachess.game.SpriteSheet;
import com.example.pikachess.game.battle.AttackMove;
import com.example.pikachess.game.battle.AttackMoves.FatPat;
import com.example.pikachess.game.battle.AttackMoves.TreadmillTrod;
import com.example.pikachess.game.battle.PikaType;
import com.example.pikachess.game.battle.Pikamon;

import java.util.Random;

public class Pikamuno extends Pikamon {

    private static final int HP = 60;
    private static final int ATTACK = 30;
    private static final int DEFENSE = 30;
    private static final int SPEED = 30;
    private static final int EXP = 60;

    public Pikamuno(Context context, boolean playerPikamon, int[] canvasDims, int level) {
        super(playerPikamon, canvasDims);
        this.level = level;
        baseEXP = EXP;
        baseHP = HP;
        baseAttack = ATTACK;
        baseDefense = DEFENSE;
        baseSpeed = SPEED;

        type = PikaType.Normal;
        imageID = R.drawable.pokemon_fatty_not_resized;
        image = new PikaSprite(context, this);

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
