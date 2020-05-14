package com.example.pikachess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pikachess.game.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        setContentView(new GameView(this));
    }
}
