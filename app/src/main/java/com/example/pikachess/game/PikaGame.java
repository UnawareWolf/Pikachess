package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.battle.PikaBattle;
import com.example.pikachess.game.battle.Pikamon;
import com.example.pikachess.game.battle.pikamen.Pikamuno;

import java.util.ArrayList;
import java.util.List;
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
    private JoystickButton joystickButton;
    private PixelMap pixelMap;
    private Random rand;
    private PikaBattle pikaBattle;
    private boolean newGame;
    private int canvasWidth;
    private int canvasHeight;
    private int resizedSquareSize;
    private double bitmapResizeFactor;
    private double[] startingShift;
    private Context context;


    public PikaGame(Context context, GameView gameView) {
        this.context = context;
        canvasWidth = gameView.getWidth();
        canvasHeight = gameView.getHeight();
        resizedSquareSize = canvasWidth / SQUARES_ACROSS_SCREEN;
        gameState = PikaGameState.Roam;

        pixelMap = new PixelMap(context);

        rand = new Random();

        //background = new GameBackground(context, canvasWidth);
        background = new GameBackground(context, this);
        bitmapResizeFactor = background.getBitmapResizeFactor();
        startingShift = background.getStartingShift();

        mainCharacter = new PlayerCharacter(context, this);
        initialiseNPCs(context);
        //npc_1 = new NPC(context, this);

        float joystickY = (float) (gameView.getHeight() - CONTROL_PANEL_HEIGHT * 1.5);
        joystickButton = new JoystickButton(context, (float) (CONTROL_PANEL_HEIGHT / 1.5), joystickY);
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
            joystickButton.release(mainCharacter);
            pikaBattle = new PikaBattle(context, new Pikamuno(), new Pikamuno(), canvasWidth, canvasHeight);
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
            background.draw(canvas);
            mainCharacter.draw(canvas);
            drawNPCs(canvas);
            joystickButton.draw(canvas);
        }
        else if (gameState == PikaGameState.Battle) {
            pikaBattle.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent event) {
        if (gameState == PikaGameState.Roam) {
            joystickButton.onTouchEvent(event, mainCharacter);
        }
        else if (gameState == PikaGameState.Battle && event.getAction() == MotionEvent.ACTION_DOWN) {
            //gameState = PikaGameState.Roam;
            pikaBattle.onTouch();
        }
    }

    public double[] getStartingShift() {
        return startingShift;
    }

    public int getCanvasWidth() {
        return this.canvasWidth;
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

}
