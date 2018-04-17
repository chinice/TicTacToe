/*
 * Copyright 2018 ALC 2.0 Project
 *
 *
 * This is a SinglePlayer activity class.
 * It is called when the Single Player button is clicked in the MainActivity class
 */
package com.elixirprimehouse.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SinglePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
    }

    /**
     * Click event handler for the start game button
     * @param view
     */
    public void startGame(View view){
        EditText player1NameBox = (EditText) findViewById(R.id.player1);
        String player1NameText = player1NameBox.getText().toString();

        //check if the Player enters a name
        if(player1NameText.equals("")){
            //If not, use the default name
            player1NameText="Player 1";
            player1NameBox.setText("Player 1");
        }

        Intent singlePlayerGameIntent = new Intent(SinglePlayerActivity.this, SinglePlayerGameActivity.class);
        singlePlayerGameIntent.putExtra("Player 1",player1NameText);
        if(singlePlayerGameIntent.resolveActivity(getPackageManager())!=null){
            startActivity(singlePlayerGameIntent);
            finish();
        }
    }
}
