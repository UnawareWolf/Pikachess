package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class PikaGame {

    private static final int CONTROL_PANEL_HEIGHT = 480;
    public static final int GRID_SQUARE_SIZE = 16;
    public static final int SQUARES_ACROSS_SCREEN = 15;
    private PikaGameState gameState;
    private PlayerCharacter mainCharacter;
    private List<NPC> npcList;
    private GameBackground background;
    private JoystickButton joystickButton;
    private PixelMap pixelMap;
    private boolean newGame;
    private int canvasWidth;
    private int resizedSquareSize;
    private double bitmapResizeFactor;
    private double[] startingShift;


    public PikaGame(Context context, GameView gameView) {
        this.canvasWidth = gameView.getWidth();
        resizedSquareSize = canvasWidth / SQUARES_ACROSS_SCREEN;

        pixelMap = new PixelMap(context);

        //background = new GameBackground(context, canvasWidth);
        background = new GameBackground(context, this);
        bitmapResizeFactor = background.getBitmapResizeFactor();
        startingShift = background.getStartingShift();

        mainCharacter = new PlayerCharacter(context, this);
        initialiseNPCs(context);
        //npc_1 = new NPC(context, this);
        gameState = PikaGameState.Roam;

        float joystickY = (float) (gameView.getHeight() - CONTROL_PANEL_HEIGHT * 1.5);
        joystickButton = new JoystickButton(context, (float) (CONTROL_PANEL_HEIGHT / 1.5), joystickY);
    }

    public void update() {
        mainCharacter.update();
        updateNPCs();
        background.update(mainCharacter);
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
        //canvas.drawBitmap(background, 0, 0, null);
        background.draw(canvas);
        mainCharacter.draw(canvas);
        drawNPCs(canvas);
        joystickButton.draw(canvas);
    }

    public void onTouchEvent(MotionEvent event) {
        joystickButton.onTouchEvent(event, mainCharacter);
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
            npc.update(mainCharacter);
        }
    }

}
