package com.example.pikachess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.List;

public class ChessMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<Spinner> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_menu);
        spinnerList.add(setUpSpinner(R.id.opponent_spinner, R.array.opponents_array));
        spinnerList.add(setUpSpinner(R.id.player_colour_spinner, R.array.player_colour_array));
        spinnerList.add(setUpSpinner(R.id.computer_level_spinner, R.array.computer_level_array));
        spinnerList.add(setUpSpinner(R.id.background_spinner, R.array.background_array));
        spinnerList.add(setUpSpinner(R.id.load_game_spinner, R.array.load_game_array));
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
        Intent chessIntent = new Intent(this, ChessActivity.class);
        Bundle chessBundle = new Bundle();
        for (Spinner spinner : spinnerList) {
            chessBundle.putString(String.valueOf(spinner.getId()), spinner.getSelectedItem().toString());
        }
        chessIntent.putExtras(chessBundle);
        startActivity(chessIntent);
    }
}
