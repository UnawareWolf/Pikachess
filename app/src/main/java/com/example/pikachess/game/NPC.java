package com.example.pikachess.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class NPC extends GameCharacter {

    private PlayerCharacter mainCharacter;

    private int startingX;
    private int startingY;
    private int xNew;
    private int yNew;
    private int walkStateIndex;

    List<CharacterState> walkStates;

    public NPC(Context context, PikaGame pikaGame, PixelSquare npcSquare) {
        super(context, pikaGame);

        walkStates = new ArrayList<>();
        initialiseWalkInSquareStates();
        walkStateIndex = 0;
        stateAccordingToJoystick = walkStates.get(walkStateIndex);
        //characterState = CharacterState.MovingDown;

        startingX = (int) npcSquare.getXOnScreen(pikaGame.getBitmapResizeFactor());
        startingY = (int) npcSquare.getYOnScreen(pikaGame.getBitmapResizeFactor());
        xNew = startingX;
        yNew = startingY;

        spriteSheet = new CharacterSpriteSheet(context, this, startingX - (int) startingShift[0], startingY - (int) startingShift[1]);

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
        //setRandomNextState();
        nextWalkInSquareState();

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
                case 4:
                    stateAccordingToJoystick = CharacterState.StationaryLeft;
                    break;
                case 5:
                    stateAccordingToJoystick = CharacterState.StationaryUp;
                    break;
                case 6:
                    stateAccordingToJoystick = CharacterState.StationaryRight;
                    break;
                default:
                    stateAccordingToJoystick = CharacterState.StationaryDown;
            }
        }
    }

    private void initialiseWalkInSquareStates() {
        walkStates.add(CharacterState.MovingLeft);
        walkStates.add(CharacterState.MovingLeft);
        walkStates.add(CharacterState.MovingDown);
        walkStates.add(CharacterState.MovingDown);
        walkStates.add(CharacterState.MovingDown);
        walkStates.add(CharacterState.MovingRight);
        walkStates.add(CharacterState.MovingRight);
        walkStates.add(CharacterState.MovingRight);
        walkStates.add(CharacterState.MovingUp);
        walkStates.add(CharacterState.MovingUp);
        walkStates.add(CharacterState.MovingUp);
        walkStates.add(CharacterState.MovingLeft);
    }

    private void nextWalkInSquareState() {
        if (distTravelled == 0) {
            stateAccordingToJoystick = walkStates.get(walkStateIndex);
//            if (walkStateIndex == walkStates.size() - 1) {
//                walkStateIndex = 0;
//            }
//            else {
//                walkStateIndex++;
//            }
        }
    }

    private void updateWalkStateIndex() {
        if (walkStateIndex == walkStates.size() - 1) {
            walkStateIndex = 0;
        }
        else {
            walkStateIndex++;
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
                updateWalkStateIndex();
            }
//            else if (stateFromJoystickIsStationary()) {
//                characterState = stateAccordingToJoystick;
//            }
            else {
                characterState = stateAccordingToJoystick;
                characterState = getStationaryState();
            }
        }
    }

    @Override
    protected PixelSquare getCurrentSquareFromPixelMap() {
        return pixelMap.getSquareFromBackgroundLocation(xNew + mainCharacter.getXMoved(), yNew + mainCharacter.getYMoved(), bitmapResizeFactor);
    }
}
