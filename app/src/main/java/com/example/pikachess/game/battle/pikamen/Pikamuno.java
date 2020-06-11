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
import com.example.pikachess.game.battle.Pikamon;

import java.util.Random;

public class Pikamuno extends Pikamon {

    private static final int HP = 60;
    //private static final int ATTACK_DAMAGE = 25;
    private static final int SPEED_AVERAGE = 30;

    public Pikamuno(Context context, boolean playerPikamon, int[] canvasDims) {
        super(playerPikamon, canvasDims);
        this.playerPikamon = playerPikamon;
        rand = new Random();
        hp = HP;
        //attackDamage = ATTACK_DAMAGE;
        speed = SPEED_AVERAGE - 6 + rand.nextInt(13);
        imageID = R.drawable.pokemon_fatty_not_resized;
        image = new PikaSprite(context, this);
        assignAttacks();
    }

    @Override
    protected void assignAttacks() {
        attacks = new AttackMove[2];
        attacks[0] = new FatPat();
        attacks[1] = new TreadmillTrod();
    }
}
