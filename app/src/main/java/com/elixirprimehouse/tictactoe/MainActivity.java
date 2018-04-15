/*
 * Copyright 2018 ALC 2.0 Project
 *
 *
 * This is a main activity class.
 * This is the first class called when the application starts.
 * It displays the screen allowing the users to select which game to play
 */
package com.elixirprimehouse.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /** Instantiate/ Declare the buttons as global variables **/
    Button singlePlayer;
    Button multiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singlePlayer = (Button) findViewById(R.id.single_player);
        /**
         * onClickListener event for the single player button.
         * Opens the single player screen
         **/
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayerIntent = new Intent(MainActivity.this, SinglePlayerActivity.class);
                if(singlePlayerIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(singlePlayerIntent);
                }
            }
        });

        multiPlayer = (Button) findViewById(R.id.multi_player);
        /**
         * onClickListener event for the multi player button
         * Opens the multi player screen when clicked
         */
        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent multiPlayerIntent = new Intent(MainActivity.this, MultiPlayerActivity.class);
                if(multiPlayerIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(multiPlayerIntent);
                }
            }
        });

    }
}
