package com.example.hw1_war_roeeaviran316492644.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_war_roeeaviran316492644.Activities.MainActivity;
import com.example.hw1_war_roeeaviran316492644.Activities.ResultsActivity;
import com.example.hw1_war_roeeaviran316492644.Models.WarCardGame;
import com.example.hw1_war_roeeaviran316492644.R;

public class MainViewController {

    // Variables
    private AppCompatActivity activity;

    private TextView score_LBL_left, score_LBL_right, turns_LBL_center;
    private ImageView card_IMG_left, card_IMG_right;
    private ImageButton play_BTN_center;

    WarCardGame game;

    public MainViewController(AppCompatActivity activity) {
        this.activity = activity;
        findViews(activity);
        initViews();
    }

    private void initViews() {
        play_BTN_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn();
            }
        });
    }

    private void turn() {
        // Changes ImageButton image to play_button
        if (game.getDeck().size() == game.getFullDeckSize())
            play_BTN_center.setImageResource(activity.getResources().getIdentifier(
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

        card_IMG_left.setImageResource(left_Drawable_ID); // Sets images
        card_IMG_right.setImageResource(right_Drawable_ID);

        if (turn_info[2].equals("Right")) {// Checks and updates round winner
            score_LBL_right.setText("" + game.getRightScore());
            turn_info[2] += " +1";
        } else if (turn_info[2].equals("Left")) {
            score_LBL_left.setText("" + game.getLeftScore());
            turn_info[2] += " +1";
        }

        // Number of turns remaining and round winner
        str = "Turns left:\t\t" + game.getDeck().size() / 2 + "\n" + turn_info[2];
        turns_LBL_center.setText(str);

        if (game.getDeck().isEmpty())// Updates ImageView to finish line
            play_BTN_center.setImageResource(activity.getResources().getIdentifier(
                    "finish_line", "drawable", activity.getPackageName()));

    }

    private void winner() {
        play_BTN_center.setEnabled(false);// Prevents results activity to open more than once
        String str = game.getWinner();// Gets winner's name
        Intent myIntent = new Intent(activity, ResultsActivity.class);
        myIntent.putExtra(ResultsActivity.RESULT_WINNER, str);
        activity.startActivity(myIntent);// Opens winner activity
        activity.finish();// Exits this activity (exits app)
    }



    private void findViews(Activity activity) {// Initializes views
        score_LBL_left = activity.findViewById(R.id.score_LBL_left);
        score_LBL_right = activity.findViewById(R.id.score_LBL_right);
        card_IMG_left = activity.findViewById(R.id.card_IMG_left);
        card_IMG_right = activity.findViewById(R.id.card_IMG_right);
        play_BTN_center = activity.findViewById(R.id.play_BTN_center);
        turns_LBL_center = activity.findViewById(R.id.turns_LBL_center);
        game = new WarCardGame(0, 0);
    }


}
