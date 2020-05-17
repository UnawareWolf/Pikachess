package com.example.pikachess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pikachess.chess.ChessView;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ChessView(this));
    }
}
