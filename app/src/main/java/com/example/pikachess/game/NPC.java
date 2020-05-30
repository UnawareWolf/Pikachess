package com.example.pikachess.game;

import android.content.Context;

public class NPC extends GameCharacter {

    private int startingX;
    private int startingY;
    private int xNew;
    private int yNew;

    public NPC(Context context, PikaGame pikaGame, PixelSquare npcSquare) {
        super(context, pikaGame);

        startingX = (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor());
        startingY = (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor());
        xNew = startingX;
        yNew = startingY;
        int halfWidth = canvasWidth / 2;

        //spriteSheet = new CharacterSpriteSheet(context, this, (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor()), (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor()));
        spriteSheet = new CharacterSpriteSheet(context, this, startingX - (int) startingShift[0], startingY - (int) startingShift[1]);

        //spriteSheet = new CharacterSpriteSheet(context, this, canvasWidth / 2, canvasWidth / 2);

        updateCurrentSquare(pikaGame.getMainCharacter());
        System.out.println();
    }

    @Override
    protected void updateCharacterState() {

    }

    @Override
    protected void updateCurrentSquare() {

    }

    public void update(PlayerCharacter mainCharacter) {
        updateCharacterMotionAndPosition(mainCharacter);
        updateCurrentSquare(mainCharacter);
    }

    protected void updateCharacterMotionAndPosition(PlayerCharacter mainCharacter) {
        xNew = startingX - (int) mainCharacter.getXMoved();
        yNew = startingY - (int) mainCharacter.getYMoved();

        spriteSheet.setRect(xNew + (int) startingShift[0], yNew + (int) startingShift[1]);
    }

    protected void updateCurrentSquare(PlayerCharacter mainCharacter) {
        if (currentSquare != null) {
            currentSquare.setWalkable(true);
        }
        currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
        currentSquare.setWalkable(false);
    }
}
