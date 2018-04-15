/*
 * Copyright 2018 ALC 2.0 Project
 *
 *
 * This is a ScoreBoard activity class.
 * This class handles the display of the scores when a game has ended
 */
package com.elixirprimehouse.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        //Get the records from the previous screen
        Intent intent = getIntent();
        String player1Wins = String.valueOf(intent.getExtras().getInt("Player 1 Wins"));
        String player2Wins = String.valueOf(intent.getExtras().getInt("Player 2 Wins"));
        String draws = String.valueOf(intent.getExtras().getInt("Draws"));
        String player1Name = intent.getExtras().getString("Player 1 Name");
        String player2Name = intent.getExtras().getString("Player 2 Name");

        //instantiate the views
        TextView player1NameView = (TextView) findViewById(R.id.p1name);
        TextView player2NameView = (TextView) findViewById(R.id.p2name);
        TextView player1WinsView = (TextView) findViewById(R.id.p1wins);
        TextView player2WinsView = (TextView) findViewById(R.id.p2wins);
        TextView player1Result = (TextView) findViewById(R.id.p1result);
        TextView player2Result = (TextView) findViewById(R.id.p2result);

        TextView drawsView = (TextView) findViewById(R.id.draws);

        //Check for the winner of the game
        if(Integer.parseInt(player1Wins)>Integer.parseInt(player2Wins)){
            player1Result.setTextColor(Color.GREEN);
            player2Result.setTextColor(Color.RED);

            player1Result.setText("Winner");
            player2Result.setText("Loser");

            drawsView.setText(player1Name);

        } else if(Integer.parseInt(player1Wins)<Integer.parseInt(player2Wins)){

            player2Result.setTextColor(Color.GREEN);
            player1Result.setTextColor(Color.RED);

            player1Result.setText("Loser");
            player2Result.setText("Winner");

            drawsView.setText(player2Name);

        } else {
            //set the text to draw if the game ended in a draw
            player1Result.setText("Draw");
            player2Result.setText("Draw");

            drawsView.setText("Draw");

        }

        player1NameView.setText(player1Name);
        player2NameView.setText(player2Name);
        player1WinsView.setText(player1Wins);
        player2WinsView.setText(player2Wins);
    }

    /**
     * Method to start a new game
     * @param view
     */
    public void beginNewGame(View view){
        Intent mainActivityIntent = new Intent(ScoreBoardActivity.this, MainActivity.class);
        if(mainActivityIntent.resolveActivity(getPackageManager())!=null){
            startActivity(mainActivityIntent);
            finish();
        }
    }
}
