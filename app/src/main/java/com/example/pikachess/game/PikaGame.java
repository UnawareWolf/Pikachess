package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.R;
import com.example.pikachess.game.battle.GamePad;
import com.example.pikachess.game.battle.PikaBattle;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.battle.pikamen.Pikamuno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PikaGame {

    private static final int CONTROL_PANEL_HEIGHT = 480;
    public static final int GRID_SQUARE_SIZE = 16;
    public static final int SQUARES_ACROSS_SCREEN = 15;
    public static final int ENCOUNTER_CHANCE = 8; // 1 / ENCOUNTER_CHANCE = chance of an encounter.
    private PikaGameState gameState;
    private PlayerCharacter mainCharacter;
    private List<NPC> npcList;
    private GameBackground background;
//    private JoystickButton joystickButton;
//    private ControllerButton aButton, bButton;
    private PixelMap pixelMap;
    private Random rand;
    private PikaBattle pikaBattle;
    private boolean newGame;
//    private int canvasWidth;
//    private int canvasHeight;
    private int[] canvasDims;
    private int resizedSquareSize;
    private double bitmapResizeFactor;
    private double[] startingShift;
    private Context context;
    private TextBox fattyHealTextBox;
    private GamePad gamePad;
    private PikaPause pikaPause;

    public PikaGame(Context context, GameView gameView) {
        this.context = context;
//        canvasWidth = gameView.getWidth();
//        canvasHeight = gameView.getHeight();
        canvasDims = new int[2];
        canvasDims[0] = gameView.getWidth();
        canvasDims[1] = gameView.getHeight();
        resizedSquareSize = canvasDims[0] / SQUARES_ACROSS_SCREEN;
        gameState = PikaGameState.Roam;

        pixelMap = new PixelMap(context);

        rand = new Random();
        fattyHealTextBox = new TextBox(context, new int[]{canvasDims[0] / 4, 2 * canvasDims[1] / 3}, "Sure, I can heal your Pokes.");

        gamePad = new GamePad(context, canvasDims);

        background = new GameBackground(context, this);
        bitmapResizeFactor = background.getBitmapResizeFactor();
        startingShift = background.getStartingShift();

        mainCharacter = new PlayerCharacter(context, this);
        initialiseNPCs(context);

        pikaPause = new PikaPause(context, canvasDims);
    }

    public void update() {


        if (gameState == PikaGameState.Roam) {
            mainCharacter.update();
            pixelMap.update();
            updateNPCs();
            pixelMap.update();
            background.update(mainCharacter);
            updateEncounterGameState();
        }
        else if (gameState == PikaGameState.Battle) {
            if (pikaBattle.getBattleOver()) {
                gameState = PikaGameState.Roam;
            }
            //joystickButton.release(mainCharacter);
            //mainCharacter.updateCharacterState();
//            mainCharacter.updateCurrentSquare();
        }
    }

    private void updateEncounterGameState() {
        if (mainCharacter.isEncounterAllowed()) {
            gameState = PikaGameState.Battle;
            gamePad.release(mainCharacter);
            pikaBattle = new PikaBattle(context, mainCharacter.getPikamen().get(0), new Pikamuno(context, false, canvasDims, 2), canvasDims);
        }
    }

    public int getPixelsAcrossSquare() {
        return resizedSquareSize;
    }

    public void setGameState(PikaGameState gameState) {
        this.gameState = gameState;
    }

    public PikaGameState getGameState() {
        return this.gameState;
    }

    public PlayerCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    public void drawGame(Canvas canvas) {
        if (gameState == PikaGameState.Roam) {
            drawRoamContent(canvas);
            gamePad.draw(canvas);
        }
        else if (gameState == PikaGameState.Battle) {
            pikaBattle.draw(canvas);
        }
        else if (gameState == PikaGameState.Talk) {
            drawRoamContent(canvas);
            fattyHealTextBox.draw(canvas);
        }
        else if (gameState == PikaGameState.Menu) {
            drawRoamContent(canvas);
            pikaPause.draw(canvas);
        }
    }

    private void drawRoamContent(Canvas canvas) {
        background.draw(canvas);
        mainCharacter.draw(canvas);
        drawNPCs(canvas);
    }

    public void onTouchEvent(MotionEvent event) {
        if (gameState == PikaGameState.Roam) {
            gamePad.onTouchEvent(event, this, mainCharacter, pixelMap);
        }
        else if (gameState == PikaGameState.Battle && event.getAction() == MotionEvent.ACTION_DOWN) {
            pikaBattle.onTouch(event);
        }
        else if (gameState == PikaGameState.Talk && event.getAction() == MotionEvent.ACTION_DOWN) {
            mainCharacter.getPikamen().get(0).restoreHP();
            gameState = PikaGameState.Roam;
        }
        else if (gameState == PikaGameState.Menu && event.getAction() == MotionEvent.ACTION_DOWN) {
            pikaPause.onTouchEvent(event, this);
//            gameState = PikaGameState.Roam;
//            for (NPC npc : npcList) {
//                npc.getSpriteSheet().setPause(false);
//            }
        }
    }

    public double[] getStartingShift() {
        return startingShift;
    }

    public int getCanvasWidth() {
        return this.canvasDims[0];
    }

    public int getCanvasHeight() {
        return this.canvasDims[1];
    }

    public double getBitmapResizeFactor() {
        return bitmapResizeFactor;
    }

    public PixelMap getPixelMap() {
        return pixelMap;
    }

    private void initialiseNPCs(Context context) {
        npcList = new ArrayList<>();
        for (PixelSquare npcSquare : pixelMap.getNPCSquares()) {
            npcList.add(new NPC(context, this, npcSquare));
        }
    }

    private void drawNPCs(Canvas canvas) {
        for (NPC npc : npcList) {
            npc.draw(canvas);
        }
    }

    private void updateNPCs() {
        for (NPC npc : npcList) {
            npc.update();
            pixelMap.update();
        }
    }

    public List<NPC> getNPCs() {
        return npcList;
    }

}
