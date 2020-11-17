package com.example.hw1_war_roeeaviran316492644;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ResultsActivity extends AppCompatActivity {

    public static final String RESULT_WINNER = "RESULT WINNER";

    private TextView results_LBL_winner;
    private Button results_BTN_exit, results_BTN_color;

    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        findViews();

        String winner = getIntent().getStringExtra(RESULT_WINNER);
        results_LBL_winner.setText(winner + " Wins!!!");// Sets winner label

        initViews();
    }

    private void initViews() {
        results_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } // Exits activity
        });
        results_BTN_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Random colors for winner label
                int color = Color.argb(255, r.nextInt(256),
                        r.nextInt(256), r.nextInt(256));
                results_LBL_winner.setTextColor(color);
            }
        });

    }

    private void findViews() {
        results_LBL_winner = findViewById(R.id.results_LBL_winner);
        results_BTN_exit = findViewById(R.id.results_BTN_exit);
        results_BTN_color = findViewById(R.id.results_BTN_color);
        r = new Random();
    }
}