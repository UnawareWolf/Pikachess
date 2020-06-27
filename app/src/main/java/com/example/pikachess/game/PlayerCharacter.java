package com.example.pikachess.game;

import android.content.Context;

import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.battle.pikamen.Pikamuno;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class PlayerCharacter extends GameCharacter{

    private int xOnScreen, yOnScreen;
    private boolean newSquare;
    private boolean encounterAllowed;
    private List<Pikamon> pikamen;
    private boolean running;

    public PlayerCharacter(Context context, PikaGame pikaGame) {
        super(context, pikaGame);
        encounterAllowed = true;
        newSquare = true;

        speed = gridSquareSize / 20d;

        xOnScreen = canvasWidth / 2;
        yOnScreen = canvasWidth / 2;

        spriteSheet = new CharacterSpriteSheet(context, this, xOnScreen, yOnScreen);
        pikamen = new ArrayList<>();

        updateCurrentSquare();
        addPikamon();
//        addPikamon();
//        pikamen.get(1).setLevel(2);
    }

    public void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

    public CharacterState getCharacterState() {
        return this.characterState;
    }

    @Override
    public void update() {
//        updateCurrentSquare();
        updateCharacterMotionAndPosition();
        updateCharacterState();
        setEncounterAllowed();
        updateCurrentSquare();

    }

    @Override
    public void updateCharacterState() {
        if (distTravelled == 0) {
            characterState = stateAccordingToJoystick;
            if (!stateFromJoystickIsWalkable()) {
                characterState = getStationaryState();
            }
        }
    }

    @Override
    protected PixelSquare getCurrentSquareFromPixelMap() {
        return pixelMap.getSquareFromBackgroundLocation(getXOnMap(), getYOnMap(), bitmapResizeFactor);
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public CharacterState getCharacterStateFromTouch(JoystickButton joystickButton, float xTouch, float yTouch) {
        CharacterState stateAccordingToTouch;
        if (!joystickButton.getDeadZoneCircle().contains(xTouch, yTouch)) {
            float xDif = Math.round(xTouch) - joystickButton.getOuterCircle().getX();
            float yDif = Math.round(yTouch) - joystickButton.getOuterCircle().getY();
            if (joystickButton.getMovementCircle().contains(xTouch, yTouch)) {
                if (abs(xDif) >= abs(yDif)) {
                    if (xDif >= 0) {
                        stateAccordingToTouch = CharacterState.StationaryRight;
                    } else {
                        stateAccordingToTouch = CharacterState.StationaryLeft;
                    }
                } else {
                    if (yDif >= 0) {
                        stateAccordingToTouch = CharacterState.StationaryDown;
                    } else {
                        stateAccordingToTouch = CharacterState.StationaryUp;
                    }
                }
            }
            else {
                if (abs(xDif) >= abs(yDif)) {
                    if (xDif >= 0) {
                        stateAccordingToTouch = CharacterState.MovingRight;
                    } else {
                        stateAccordingToTouch = CharacterState.MovingLeft;
                    }
                } else {
                    if (yDif >= 0) {
                        stateAccordingToTouch = CharacterState.MovingDown;
                    } else {
                        stateAccordingToTouch = CharacterState.MovingUp;
                    }
                }
            }
        }
        else {
            stateAccordingToTouch = getStationaryState();
        }
        return stateAccordingToTouch;
    }

    public void setStateAccordingToJoystick(CharacterState state) {
        stateAccordingToJoystick = state;
    }

    private void setEncounterAllowed() {
        if (distTravelled == 0 && newSquare && rand.nextInt(PikaGame.ENCOUNTER_CHANCE - 1) == 0
                && currentSquare.isEncounterSquare() && pikamen.get(0).getHp() > 0) {
            characterState = getStationaryState();
            encounterAllowed = true;
            newSquare = false;
        }
        else {
            encounterAllowed = false;
        }
        if (distTravelled != 0){
            newSquare = true;
        }
    }

    private double getXOnMap() {
        return xOnScreen + xMoved - startingShift[0];
    }

    private double getYOnMap() {
        return yOnScreen + yMoved - startingShift[1];
    }

    public boolean isEncounterAllowed() {
        return encounterAllowed;
    }

    public void addPikamon() {
        Pikamuno pikamuno = new Pikamuno(context, true, new int[]{canvasWidth, canvasHeight}, 5);
        pikamen.add(pikamuno);
    }

    public List<Pikamon> getPikamen() {
        return pikamen;
    }

    public void setRunning(boolean running) {
        this.running = running;
        speed = running ? gridSquareSize / 10d : gridSquareSize / 20d;
    }

}
