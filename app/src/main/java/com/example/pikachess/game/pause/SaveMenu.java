package com.example.pikachess.game.pause;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pikachess.game.Button;
import com.example.pikachess.game.PikaGame;
import com.example.pikachess.game.PikaGameState;
import com.example.pikachess.game.PikaPause;
import com.example.pikachess.game.TextButton;
import com.example.pikachess.game.pause.buttons.BackButton;
import com.example.pikachess.game.pause.buttons.SaveButton;

import java.util.ArrayList;
import java.util.List;

public class SaveMenu {

    private TextButton saveTextButton;
    private TextButton saveConfirmedTextButton;
    private SaveButton saveButton;
    private BackButton backButton;
    private List<Button> buttons;
    private boolean savePressed;

    public SaveMenu(Context context, PikaPause pikaPause, int[] canvasDims) {
        savePressed = false;

        saveTextButton = new TextButton(context, canvasDims);
        saveTextButton.setText("Overwrite save data?");
        saveConfirmedTextButton = new TextButton(context, canvasDims);
        saveConfirmedTextButton.setText("Game has been saved.");

        int buttonWidth = canvasDims[0] / 2 - PikamonMenu.SCREEN_BORDER * 3 / 2;
        int buttonHeight = saveTextButton.getHeight() * 2 / 3;
        int[] textLocation = saveTextButton.getLocation();
        int leftButtonX = textLocation[0] - buttonWidth / 2 - PikamonMenu.SCREEN_BORDER / 2;
        int rightButtonX = textLocation[0] + buttonWidth / 2 + PikamonMenu.SCREEN_BORDER / 2;
        int buttonY = textLocation[1] - PikamonMenu.SCREEN_BORDER / 2 - saveTextButton.getHeight() / 2 - buttonHeight / 2;

        saveButton = new SaveButton(context, pikaPause, new int[] {leftButtonX, buttonY}, buttonWidth, buttonHeight);
        backButton = new BackButton(context, new int[] {rightButtonX, buttonY}, buttonWidth, buttonHeight);
        backButton.setText("No");

        buttons = new ArrayList<>();
        buttons.add(saveTextButton);
        buttons.add(saveButton);
        buttons.add(backButton);
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame) {
        if (!savePressed) {
            for (Button button : buttons) {
                button.onTouchEvent(event, pikaGame);
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN){
            pikaGame.setGameState(PikaGameState.Roam);
            pikaGame.getPikaPause().setPauseState(PauseState.Menu);
            savePressed = false;
        }
    }

    public void draw(Canvas canvas) {
        if (!savePressed) {
            for (Button button : buttons) {
                button.draw(canvas);
            }
        }
        else {
            saveConfirmedTextButton.draw(canvas);
        }
    }

    public void setSavePressed(boolean savePressed) {
        this.savePressed = savePressed;
    }

}
