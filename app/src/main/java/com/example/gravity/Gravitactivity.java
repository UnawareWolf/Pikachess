package com.example.gravity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gravity.chess.ChessView;

public class Gravitactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ChessView(this));
        //setContentView(R.layout.activity_gravitactivity);
    }
    /*
    public void drawSomething(View view) {
        view.invalidate();
    }
    */
}
