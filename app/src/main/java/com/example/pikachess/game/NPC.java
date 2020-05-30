package com.example.pikachess.game;

import android.content.Context;

public class NPC extends GameCharacter {

    int startingX;
    int startingY;

    public NPC(Context context, PikaGame pikaGame, PixelSquare npcSquare) {
        super(context, pikaGame);

        startingX = (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor());
        startingY = (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor());
        int halfWidth = canvasWidth / 2;

        //spriteSheet = new CharacterSpriteSheet(context, this, (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor()), (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor()));
        spriteSheet = new CharacterSpriteSheet(context, this, startingX, startingY);

        //spriteSheet = new CharacterSpriteSheet(context, this, canvasWidth / 2, canvasWidth / 2);


        updateCurrentSquare();
    }

    @Override
    protected void updateCharacterState() {

    }

    public void update(PlayerCharacter mainCharacter) {
        updateCharacterMotionAndPosition(mainCharacter);
    }

    protected void updateCharacterMotionAndPosition(PlayerCharacter mainCharacter) {
        int xNew = startingX - (int) mainCharacter.getXMoved();
        int yNew = startingY - (int) mainCharacter.getYMoved();
        
        spriteSheet.setRect(xNew, yNew);
    }

    @Override
    protected void updateCurrentSquare() {

    }
}
