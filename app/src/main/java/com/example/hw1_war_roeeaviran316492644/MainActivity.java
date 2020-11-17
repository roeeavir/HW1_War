package com.example.hw1_war_roeeaviran316492644;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Variables
    private final int MAX_DIFFERENT_CARD_SCORES = 13; // There are 13 different card scores
    private final int MIN_CARD_SCORE = 2; // 2 is the lowest score a card can have
    private final char[] CARD_TYPES = {'c', 'd', 'h', 'l'}; // Stands for Clubs, Diamonds, Hearts, Leaves

    private TextView score_LBL_left, score_LBL_right, turns_LBL_center;
    private ImageView card_IMG_left, card_IMG_right;
    private ImageButton play_BTN_center;

    private ArrayList<WarCard> deck;

    private int leftScore = -1, rightScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

    }

    private void initViews() {
        play_BTN_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftScore == -1)
                    shuffleDeck();
                if (deck.isEmpty())
                    winner();
                else
                    playWar();
            }
        });
    }

    private void shuffleDeck() {
        deck = new ArrayList<WarCard>();
        String str = "";
        int drawableResourceId;
        for (char c : CARD_TYPES) {
            for (int i = MIN_CARD_SCORE; i < MAX_DIFFERENT_CARD_SCORES + MIN_CARD_SCORE; i++) {
                str = "poker_" + c + i;
                drawableResourceId = this.getResources().getIdentifier(str, "drawable",
                        this.getPackageName());
                deck.add(new WarCard(drawableResourceId, i));
            }
        }
        leftScore++;
        Collections.shuffle(deck);
    }

    private void playWar() {
        WarCard left_Card = deck.remove(0);
        WarCard right_Card = deck.remove(0);
        int left_Drawable_ID = left_Card.getCard_ID();
        int right_Drawable_ID = right_Card.getCard_ID();

        int left_Drawable_Value = left_Card.getCard_Value();
        int right_Drawable_Value = right_Card.getCard_Value();

        card_IMG_left.setImageResource(left_Drawable_ID);
        card_IMG_right.setImageResource(right_Drawable_ID);

        turns_LBL_center.setText("Turns left: " + deck.size() / 2);

        if (left_Drawable_Value < right_Drawable_Value) {
            rightScore++;
            score_LBL_right.setText("" + rightScore);
            turns_LBL_center.setText(turns_LBL_center.getText()+"\nRight Player +1");
        } else if (left_Drawable_Value > right_Drawable_Value) {
            leftScore++;
            score_LBL_left.setText("" + leftScore);
            turns_LBL_center.setText(turns_LBL_center.getText()+"\nLeft Player +1");
        }
        else
            turns_LBL_center.setText(turns_LBL_center.getText()+"\nDraw");
        if (deck.isEmpty())
            play_BTN_center.setImageResource( this.getResources().getIdentifier(
                    "finish_line", "drawable", this.getPackageName()));

    }

    private void winner() {
        Intent myIntent = new Intent(MainActivity.this, ResultsActivity.class);
        if (leftScore > rightScore)
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Left Player");
        else if (leftScore < rightScore)
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Right Player");
        else
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Corona");
        startActivity(myIntent);
        finish();
    }

    private void findViews() {
        score_LBL_left = findViewById(R.id.score_LBL_left);
        score_LBL_right = findViewById(R.id.score_LBL_right);
        card_IMG_left = findViewById(R.id.card_IMG_left);
        card_IMG_right = findViewById(R.id.card_IMG_right);
        play_BTN_center = findViewById(R.id.play_BTN_center);
        turns_LBL_center = findViewById(R.id.turns_LBL_center);
    }


    @Override
    protected void onPause() {
        Log.d("pttt", "Pause");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.d("pttt", "Start");
        super.onStart();

        Log.d("pttt", "Resume/Start Timer");
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "Resume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "Destroy");
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        Log.d("pttt", "Stop");
        super.onStop();

        Log.d("pttt", "Timer Stop");

        //Pause timer
    }

}