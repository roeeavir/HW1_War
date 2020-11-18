package com.example.hw1_war_roeeaviran316492644;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Variables
    private TextView score_LBL_left, score_LBL_right, turns_LBL_center;
    private ImageView card_IMG_left, card_IMG_right;
    private ImageButton play_BTN_center;


    WarCardGame game;

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
                turn();
            }
        });
    }

    private void turn(){
        // Changes ImageButton image to play_button
        if(game.getDeck().size() == game.getFullDeckSize())
            play_BTN_center.setImageResource(this.getResources().getIdentifier(
                    "play_button", "drawable", this.getPackageName()));
        // If the deck is empty - game is over and we announce the winner
        if (game.getDeck().isEmpty())
            winner();
        else
            play();// Play game round
    }


    private void play() {
        String[] turn_info = game.playTurn();// Needed turn info - cards id & round winner
        String str;

        int left_Drawable_ID = this.getResources().getIdentifier(turn_info[0],
                "drawable", this.getPackageName());// Gets card id from card object
        int right_Drawable_ID = this.getResources().getIdentifier(turn_info[1],
                "drawable", this.getPackageName());// Gets card id from card object

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
            play_BTN_center.setImageResource(this.getResources().getIdentifier(
                    "finish_line", "drawable", this.getPackageName()));

    }

    private void winner() {
        String str = game.getWinner();// Gets winner's name
        Intent myIntent = new Intent(MainActivity.this, ResultsActivity.class);
        myIntent.putExtra(ResultsActivity.RESULT_WINNER, str);
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
        game = new WarCardGame(0, 0);
    }

    // State functions
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