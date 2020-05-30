package com.example.pikachess.game;

import android.content.Context;

public class NPC extends GameCharacter {


    public NPC(Context context, PikaGame pikaGame, PixelSquare npcSquare) {
        super(context, pikaGame);

        spriteSheet = new CharacterSpriteSheet(context, this, (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor()), (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor()));


        updateCurrentSquare();
    }

    @Override
    protected void updateCharacterState() {

    }

    @Override
    protected void updateCurrentSquare() {

    }
}
