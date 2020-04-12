package com.example.gravity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private EditText numToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);

        //mShowCount = (TextView) findViewById(R.id.show_count);
    }

    public void jeeloWorld(View view) {
        Toast toast = Toast.makeText(this, R.string.jeelo_world, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        numToAdd = findViewById(R.id.numberToAdd);
        if (numToAdd != null) {
            int theNumber = Integer.parseInt(numToAdd.getText().toString());
            mCount = mCount + theNumber;
            mShowCount.setText(Integer.toString(mCount));
        }
    }

    public void goGravity(View view) {
        startActivity(new Intent(this, Gravitactivity.class));
    }

}
