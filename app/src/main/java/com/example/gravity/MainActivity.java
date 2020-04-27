package com.example.gravity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mCount = 0;
    private TextView mShowCount;
    private EditText numToAdd;
    private boolean computer;
    private List<Spinner> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerList.add(setUpSpinner(R.id.opponent_spinner, R.array.opponents_array));
        spinnerList.add(setUpSpinner(R.id.player_colour_spinner, R.array.player_colour_array));
        spinnerList.add(setUpSpinner(R.id.computer_level_spinner, R.array.computer_level_array));
        spinnerList.add(setUpSpinner(R.id.background_spinner, R.array.background_array));
    }

    public Spinner setUpSpinner(int spinnerId, int spinnerArray) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, spinnerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return spinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goGravity(View view) {

        for (Spinner spinner : spinnerList) {
            System.out.println(R.id.opponent_spinner);
            System.out.println(spinner.getId());
            System.out.println(spinner.getSelectedItem());
        }

        Intent chessIntent = new Intent(this, Gravitactivity.class);
        Bundle chessBundle = new Bundle();
        for (Spinner spinner : spinnerList) {
            if (spinner.getSelectedItemId() == R.id.opponent_spinner) {
                System.out.println("Hooray");
            }
            chessBundle.putString(String.valueOf(spinner.getId()), spinner.getSelectedItem().toString());
        }
        chessIntent.putExtras(chessBundle);
        startActivity(chessIntent);
    }
}
