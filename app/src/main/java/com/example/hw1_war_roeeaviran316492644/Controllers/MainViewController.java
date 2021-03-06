package com.example.hw1_war_roeeaviran316492644.Controllers;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_war_roeeaviran316492644.Activities.ResultsActivity;
import com.example.hw1_war_roeeaviran316492644.Models.WarCardGame;
import com.example.hw1_war_roeeaviran316492644.R;

public class MainViewController { // Main Activity Controller Class

    // Variables
    private AppCompatActivity activity;

    private TextView main_LBL_leftScore, main_LBL_rightScore, main_LBL_center;
    private ImageView main_IMG_leftCard, main_IMG_rightCard;
    private ImageButton main_BTN_centerPlay;

    WarCardGame game;

    public MainViewController(AppCompatActivity activity) {
        this.activity = activity;
        findViews();
        initViews();
    }

    private void initViews() {
        main_BTN_centerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn();
            }
        });
    }

    private void turn() {
        // Changes ImageButton image to play_button
        if (game.getDeck().size() == game.getFullDeckSize())
            main_BTN_centerPlay.setImageResource(activity.getResources().getIdentifier(
                    "play_button", "drawable", activity.getPackageName()));
        // If the deck is empty - game is over and we announce the winner
        if (game.getDeck().isEmpty())
            winner();
        else
            play();// Play game round
    }


    private void play() {
        game.playTurn();// Updates turn_info
        String str;
        String[] turn_info = game.getTurn_info();// Needed turn info - cards id & round winner

        int left_Drawable_ID = activity.getResources().getIdentifier(turn_info[0],
                "drawable", activity.getPackageName());// Gets card id from card object
        int right_Drawable_ID = activity.getResources().getIdentifier(turn_info[1],
                "drawable", activity.getPackageName());// Gets card id from card object

        main_IMG_leftCard.setImageResource(left_Drawable_ID); // Sets images
        main_IMG_rightCard.setImageResource(right_Drawable_ID);

        if (turn_info[2].equals("Right")) {// Checks and updates round winner
            updateMain_LBL_rightScore("" + game.getRightScore());
            turn_info[2] += " +1";
        } else if (turn_info[2].equals("Left")) {
            updateMain_LBL_leftScore("" + game.getLeftScore());
            turn_info[2] += " +1";
        }

        // Number of turns remaining and round winner
        str = "Turns left:\t\t" + game.getDeck().size() / 2 + "\n" + turn_info[2];
        updateMain_LBL_center(str);

        if (game.getDeck().isEmpty())// Updates ImageView to finish line
            main_BTN_centerPlay.setImageResource(activity.getResources().getIdentifier(
                    "finish_line", "drawable", activity.getPackageName()));

    }

    private void winner() {
        main_BTN_centerPlay.setEnabled(false);// Prevents results activity to open more than once
        String str = game.getWinner();// Gets winner's name
        Intent myIntent = new Intent(activity, ResultsActivity.class);
        myIntent.putExtra(ResultsActivity.RESULT_WINNER, str);
        activity.startActivity(myIntent);// Opens winner activity
        activity.finish();// Exits this activity (exits app)
    }


    private void findViews() {// Initializes views
        main_LBL_leftScore = activity.findViewById(R.id.main_LBL_leftScore);
        main_LBL_rightScore = activity.findViewById(R.id.main_LBL_rightScore);
        main_IMG_leftCard = activity.findViewById(R.id.main_IMG_leftCard);
        main_IMG_rightCard = activity.findViewById(R.id.main_IMG_rightCard);
        main_BTN_centerPlay = activity.findViewById(R.id.main_BTN_centerPlay);
        main_LBL_center = activity.findViewById(R.id.main_LBL_center);
        game = new WarCardGame(0, 0);
    }

    public void updateMain_LBL_center(String str) {
        main_LBL_center.setText(str);
    }

    public void updateMain_LBL_leftScore(String str) {
        main_LBL_leftScore.setText(str);
    }

    public void updateMain_LBL_rightScore(String str) {
        main_LBL_rightScore.setText(str);
    }


}
