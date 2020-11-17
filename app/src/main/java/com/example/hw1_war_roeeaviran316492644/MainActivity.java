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

    private int leftScore = -1; //Marks start of game
    private int rightScore = 0;

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
                if (leftScore == -1)// before the first round - we shuffle the deck
                    shuffleDeck();
                if (deck.isEmpty())// If the deck is empty - game is over and we announce the winner
                    winner();
                else
                    playWar();// Play game round
            }
        });
    }

    private void shuffleDeck() {// Function for initializing and shuffling deck
        deck = new ArrayList<>();
        String str;
        int drawableResourceId;
        for (char c : CARD_TYPES) {// inserts 52 cards into the deck
            for (int i = MIN_CARD_SCORE; i < MAX_DIFFERENT_CARD_SCORES + MIN_CARD_SCORE; i++) {
                str = "poker_" + c + i;
                drawableResourceId = this.getResources().getIdentifier(str, "drawable",
                        this.getPackageName());// Gets ImageView id from ImageView name
                deck.add(new WarCard(drawableResourceId, i));
            }
        }
        leftScore++; // Sets leftScore to 0
        Collections.shuffle(deck);// Shuffles deck randomly
    }

    private void playWar() {
        WarCard left_Card = deck.remove(0);// Gets card object from deck
        WarCard right_Card = deck.remove(0);
        int left_Drawable_ID = left_Card.getCard_ID();// Gets card id from card object
        int right_Drawable_ID = right_Card.getCard_ID();

        int left_Drawable_Value = left_Card.getCard_Value();// Gets card value from card object
        int right_Drawable_Value = right_Card.getCard_Value();

        card_IMG_left.setImageResource(left_Drawable_ID); // Sets images
        card_IMG_right.setImageResource(right_Drawable_ID);

        turns_LBL_center.setText("Turns left: " + deck.size() / 2);// Number of turns remaining

        if (left_Drawable_Value < right_Drawable_Value) {// Checks and updates round winner
            rightScore++;
            score_LBL_right.setText("" + rightScore);
            turns_LBL_center.setText(turns_LBL_center.getText() + "\nRight Player +1");
        } else if (left_Drawable_Value > right_Drawable_Value) {
            leftScore++;
            score_LBL_left.setText("" + leftScore);
            turns_LBL_center.setText(turns_LBL_center.getText() + "\nLeft Player +1");
        } else
            turns_LBL_center.setText(turns_LBL_center.getText() + "\nDraw");
        if (deck.isEmpty())// Updates ImageView to finish line
            play_BTN_center.setImageResource(this.getResources().getIdentifier(
                    "finish_line", "drawable", this.getPackageName()));

    }

    private void winner() {
        Intent myIntent = new Intent(MainActivity.this, ResultsActivity.class);
        if (leftScore > rightScore)// Checks winner
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Left Player");
        else if (leftScore < rightScore)
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Right Player");
        else
            myIntent.putExtra(ResultsActivity.RESULT_WINNER, "Corona");
        startActivity(myIntent);// Opens winner activity
        finish();// Exits this activity (exits app)
    }

    private void findViews() {// Initializes views
        score_LBL_left = findViewById(R.id.score_LBL_left);
        score_LBL_right = findViewById(R.id.score_LBL_right);
        card_IMG_left = findViewById(R.id.card_IMG_left);
        card_IMG_right = findViewById(R.id.card_IMG_right);
        play_BTN_center = findViewById(R.id.play_BTN_center);
        turns_LBL_center = findViewById(R.id.turns_LBL_center);
    }

    // Log functions
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