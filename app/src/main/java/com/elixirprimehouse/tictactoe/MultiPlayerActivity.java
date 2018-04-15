/*
 * Copyright 2018 ALC 2.0 Project
 *
 *
 * This is a multi-player activity class.
 * It displays the multi-player screen to enable users enter their names and start the game.
 */
package com.elixirprimehouse.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MultiPlayerActivity extends AppCompatActivity {
    String player1NameText;
    String player2NameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);
    }

    /**
     * Start game event handler, called when the start game button is clicked
     * It gets the names of both players (Player 1 and Player 2) and pass it to the next screen
     * @param view
     */
    public void startGame(View view){
        EditText player1NameBox = (EditText) findViewById(R.id.player1);
        EditText player2NameBox = (EditText) findViewById(R.id.player2);
        player1NameText = player1NameBox.getText().toString();
        player2NameText = player2NameBox.getText().toString();

        //Check if Player 1 name is entered
        if(player1NameText.equals("")){
            //if not, use the default name 'Player 1'
            player1NameText = "Player 1";
            player1NameBox.setText(R.string.player_1_default_name);
        }
        //Check if Player 2 name is entered
        if(player2NameText.equals("")){
            //If not, use the default name 'Player 2'
            player2NameText = "Player 2";
            player2NameBox.setText(R.string.player_2_default_name);
        }

        Intent multiPlayerGame = new Intent(MultiPlayerActivity.this, MultiPlayerGameActivity.class);
        multiPlayerGame.putExtra("Player 1",player1NameText);
        multiPlayerGame.putExtra("Player 2",player2NameText);

        if(multiPlayerGame.resolveActivity(getPackageManager())!=null){
            startActivity(multiPlayerGame);
            finish();
        }
    }
}
