package com.example.hw1_war_roeeaviran316492644;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ResultsActivity extends AppCompatActivity {

    private final int DELAY = 500;
    public static final String RESULT_WINNER = "RESULT WINNER";

    RelativeLayout results_REL_background;
    private TextView results_LBL_winner;
    private Button results_BTN_exit;

    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        findViews();

        String winner = getIntent().getStringExtra(RESULT_WINNER);
        results_LBL_winner.setText(winner + " Wins!!!");// Sets winner label
        results_REL_background.setBackgroundResource(R.drawable.background_peace);


        initViews();
    }

    private void initViews() {
        results_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } // Exits activity
        });
    }

    private void findViews() {
        results_LBL_winner = findViewById(R.id.results_LBL_winner);
        results_BTN_exit = findViewById(R.id.results_BTN_exit);
        results_REL_background = findViewById(R.id.results_REL_background);
        r = new Random();
    }

    private void changeColor(){
        int color = Color.argb(255, r.nextInt(256),
                r.nextInt(256), r.nextInt(256));
        results_LBL_winner.setTextColor(color);
        results_BTN_exit.setBackgroundColor(color);
    }

    private Timer carousalTimer;

    private void startCounting() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeColor();
                    }
                });
            }
        }, 0, DELAY);
    }

    private void stopCounting() {
        carousalTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startCounting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCounting();
    }
}