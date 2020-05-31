package com.example.pikachess.game;

import android.content.Context;

import java.util.Random;

import static java.lang.Math.abs;

public class NPC extends GameCharacter {

    private PlayerCharacter mainCharacter;

    private int startingX;
    private int startingY;
    private int xNew;
    private int yNew;
    private Random rand;

    public NPC(Context context, PikaGame pikaGame, PixelSquare npcSquare) {
        super(context, pikaGame);

        //characterState = CharacterState.MovingDown;
        rand = new Random();
        startingX = (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor());
        startingY = (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor());
        xNew = startingX;
        yNew = startingY;
        int halfWidth = canvasWidth / 2;

        //spriteSheet = new CharacterSpriteSheet(context, this, (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor()), (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor()));
        spriteSheet = new CharacterSpriteSheet(context, this, startingX - (int) startingShift[0], startingY - (int) startingShift[1]);

        //spriteSheet = new CharacterSpriteSheet(context, this, canvasWidth / 2, canvasWidth / 2);
        mainCharacter = pikaGame.getMainCharacter();

        updateCurrentSquare();
    }

//    @Override
//    protected void updateCharacterState() {
//        if (distTravelled == 0) {
//            characterState = nextState;
//            if (!stateFromJoystickIsWalkable()) {
//                characterState = getStationaryState();
//            }
//        }
//    }

//    @Override
//    protected void updateCurrentSquare() {
//
//    }

    @Override
    public void update() {
        //stateAccordingToJoystick = CharacterState.MovingDown;
        setRandomNextState();

        updateCharacterMotionAndPosition();
        updateSpritePosition();
        updateCharacterState();
        updateCurrentSquare();

        //setRandomNextState();

    }

    private void setRandomNextState() {
        if (distTravelled == 0) {
            int n = rand.nextInt(4);
            switch (n) {
                case 0:
                    stateAccordingToJoystick = CharacterState.MovingLeft;
                    break;
                case 1:
                    stateAccordingToJoystick = CharacterState.MovingUp;
                    break;
                case 2:
                    stateAccordingToJoystick = CharacterState.MovingRight;
                    break;
                case 3:
                    stateAccordingToJoystick = CharacterState.MovingDown;
                    break;
                default:
                    stateAccordingToJoystick = CharacterState.StationaryDown;
            }
        }
    }

    protected void updateSpritePosition() {
        xNew = startingX + (int) xMoved - (int) mainCharacter.getXMoved();
        yNew = startingY + (int) yMoved - (int) mainCharacter.getYMoved();

        spriteSheet.setRect(xNew + (int) startingShift[0], yNew + (int) startingShift[1]);
    }

    @Override
    protected void updateCharacterState() {
        if (distTravelled == 0) {
            if (stateFromJoystickIsWalkable()) {
                characterState = stateAccordingToJoystick;
            }
            else {
                characterState = getStationaryState();
            }
        }
    }

//    protected void updateCurrentSquare(PlayerCharacter mainCharacter) {
//        if (currentSquare != null) {
//            currentSquare.setWalkable(true);
//        }
//        currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
//        currentSquare.setWalkable(false);
//    }


//    @Override
//    protected void updateCurrentSquare() {
////        if (currentSquare!= null) {
////            if (distTravelled == 0) {
////                nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
////                nextSquare.setWalkable(false);
////            }
////            else if ((int) abs(distTravelled) == gridSquareSize / 2) {
////                currentSquare.setWalkable(true);
////                currentSquare = nextSquare;
////            }
////        }
////        else {
////            currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
////            currentSquare.setWalkable(false);
////        }
//
////        if (currentSquare!= null) {
////            if (distTravelled == 0) {
////                if (lastSquare != null) {
////                    lastSquare.setWalkable(false);
////                }
////                nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
////                nextSquare.setWalkable(false);
////            }
////            else if ((int) abs(distTravelled) == gridSquareSize / 2) {
////                lastSquare.setWalkable(true);
////                lastSquare = currentSquare;
////            }
//////            currentSquare.setWalkable(true);
//////            currentSquare = nextSquare;
////            currentSquare.setWalkable(true);
////            currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
////            currentSquare.setWalkable(false);
////        }
////        else {
////            currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
////            currentSquare.setWalkable(false);
////        }
//
//        if (currentSquare != null) {
//            currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
//            currentSquare.setWalkable(false);
//            if (distTravelled == 0) {
//
//
//                if (lastSquare != currentSquare) {
//                    lastSquare.setWalkable(true);
//                    lastSquare = nextSquare;
//                }
//
//                nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
//                nextSquare.setWalkable(false);
//            }
//
//        }
//        else {
//            currentSquare = pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
//            lastSquare = currentSquare;
//            nextSquare = pixelMap.getNextSquare(currentSquare, characterState);
//            currentSquare.setWalkable(false);
//        }
//    }

    @Override
    protected PixelSquare getCurrentSquareFromPixelMap() {
        return pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
    }
}
