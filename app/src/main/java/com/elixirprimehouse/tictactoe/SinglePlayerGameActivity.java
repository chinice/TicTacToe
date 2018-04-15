package com.elixirprimehouse.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class SinglePlayerGameActivity extends AppCompatActivity {

    //Declare and instantiate variables
    int turn = 1;
    int status = 0;
    int gameover = 0;
    int flagEndGame=0;
    int flag;
    String displayTurn;
    GridLayout grid;
    Button playBoard[][] = new Button[3][3];
    int boardMatrix[][] = new int[3][3];
    double probMatrix[][] = new double[3][3];
    TextView playerTurn;
    String player1Name;
    String player2Name;
    int moveNumber=1;
    int counter = 0;
    int player1Win = 0, player2Win = 0, draw = 0;
    int flipValue=0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);

        playerTurn = (TextView) findViewById(R.id.player);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = "Computer";
        grid = (GridLayout) findViewById(R.id.grid);
        displayTurn=player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j] = (Button) grid.getChildAt(3 * i + j);
                boardMatrix[i][j]=0;
            }
        }
        if(flipValue==1){
            computerIntelligence();
            turn=2;
        }
    }

    /**
     * PlayGame click event handler called when a button on the board is clicked.
     *
     * @param view
     */
    public void playGame(View view){
        int index = grid.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;
        flag = 0;
        if (turn == 1 && gameover == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {

            if(flipValue==0){
                displayTurn=player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                playBoard[i][j].setText("X");
                boardMatrix[i][j]=1;
                turn = 2;
                moveNumber++;
                computerIntelligence();
                turn = 1;
                displayTurn=player1Name + "'s turn (X)";
                moveNumber++;
            }

        } else if (turn == 2 && gameover == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {

            if(flipValue==1){
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
                playBoard[i][j].setText("O");
                boardMatrix[i][j]=1;
                turn = 1;
                moveNumber++;
                computerIntelligence();
                displayTurn=player1Name + "'s turn (O)";
                turn = 2;
                moveNumber++;

            }
        }
        checkWin();

        if (gameover == 1) {
            if (status == 1) {
                builder.setMessage(player1Name + " wins!").setTitle("Game over");
                if(flagEndGame==0){
                    player1Win++;
                    counter++;
                }

            } else if (status == 2) {
                builder.setMessage(player2Name + " wins!").setTitle("Game over");
                if(flagEndGame==0){
                    player2Win++;
                    counter++;
                }

            }
            flagEndGame=1;
            flag = 1;
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    Intent scoreBoardIntent = new Intent(SinglePlayerGameActivity.this, ScoreBoardActivity.class);
                    scoreBoardIntent.putExtra("Player 1 Wins", player1Win);
                    scoreBoardIntent.putExtra("Player 2 Wins", player2Win);
                    scoreBoardIntent.putExtra("Draws", draw);
                    scoreBoardIntent.putExtra("Player 1 Name", player1Name);
                    scoreBoardIntent.putExtra("Player 2 Name", player2Name);
                    if (scoreBoardIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(scoreBoardIntent);
                        finish();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (gameover == 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (!playBoard[i][j].getText().toString().equals("X") && !playBoard[i][j].getText().toString().equals("O")) {
                        flag = 1;
                        break;
                    }
                }
            }
        }
        if (flag == 0) {
            builder.setMessage("Game ended in a draw").setTitle("Game over");
            if(flagEndGame==0){
                counter++;
                draw++;
            }
            flagEndGame=1;
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    Intent scoreBoardIntent = new Intent(SinglePlayerGameActivity.this, ScoreBoardActivity.class);
                    scoreBoardIntent.putExtra("Player 1 Wins", player1Win);
                    scoreBoardIntent.putExtra("Player 2 Wins", player2Win);
                    scoreBoardIntent.putExtra("Draws", draw);
                    scoreBoardIntent.putExtra("Player 1 Name", player1Name);
                    scoreBoardIntent.putExtra("Player 2 Name", player2Name);
                    if (scoreBoardIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(scoreBoardIntent);
                        finish();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    int level = 0;

    /**
     * Intelligence method that determines how the computer plays
     * when playing against a human
     */
    public void computerIntelligence(){
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i = 0,j = 0;
        int moveChoice = 0;
        int flag = 0;
        int flagGameNotOver = 0;

        if(turn == 1){
            turn = 2;
        }
        else{
            turn = 1;
        }
        for(int c = 0; c < 9; c++){
            i = c / 3;
            j = c % 3;
            probMatrix[i][j]=0;
        }

        for(int c=0; c<9;c++) {
            i = c / 3;
            j = c % 3;
            if (boardMatrix[i][j] == 0) {
                flagGameNotOver = 1;
                boardMatrix[i][j] = 1;
                if (flipValue == 1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if (checkWinComp() == 2 && flipValue == 0) {
                    flag = 1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    flag = 1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                }
                if (checkWinComp() == 1 && flipValue == 1) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;

                } else {
                    level++;
                    probMatrix[i][j] = computerAnalyze();
                    level--;

                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j] = 0;

            }
        }
        if(flagGameNotOver == 0){
            return;
        }
        double maxProb = 0;
        if(flag == 0){
            for(int p = 0; p < 3 ; p++){
                for(int q = 0; q < 3; q++){
                    if(maxProb < probMatrix[p][q]){
                        maxProb = probMatrix[p][q];
                    }
                }
            }
            for(int p=0;p<3;p++){
                for(int q = 0;q < 3; q++){
                    if(maxProb == probMatrix[p][q] && boardMatrix[p][q] == 0){
                        moveChoice = 3 * p + q;
                        break;
                    }
                }
            }
        }
        else{
            moveChoice = 3 * i + j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord = moveChoice / 3;
        int yCoord = moveChoice % 3;
        boardMatrix[xCoord][yCoord]=1;
        if(flipValue==0){
            playBoard[xCoord][yCoord].setText("O");
            displayTurn=player1Name+"'s turn (X)";
            playerTurn.setText(displayTurn);
        }
        else{
            playBoard[xCoord][yCoord].setText("X");
            displayTurn=player1Name+"'s turn (O)";
            playerTurn.setText(displayTurn);
        }
    }

    public double computerAnalyze() {
        double sum = 0;
        int counter = 0;
        int flagCheckGameNotOver = 0;
        for(int c = 0; c < 9; c++){
            int i = c / 3;
            int j = c % 3;

            if(boardMatrix[i][j] == 0){
                flagCheckGameNotOver = 1;
                boardMatrix[i][j] = 1;

                if(turn == 1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if(checkWinComp() == 2 && flipValue == 0){
                    sum = 1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;

                    return sum;
                } else if(checkWinComp() == 2 && flipValue == 1){
                    sum = 1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;

                    return sum;
                } else if(checkWinComp() == 1 && flipValue == 1){
                    sum = 0;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;

                    return sum;
                } else if(checkWinComp() == 1 && flipValue == 0){
                    sum = 0;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;

                    return sum;
                } else {
                    counter++;
                    if(turn == 1){
                        turn = 2;
                    } else{
                        turn = 1;
                    }
                    level++;
                    double value = computerAnalyze();
                    level--;
                    sum += value;
                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j] = 0;
                if(turn == 1){
                    turn = 2;
                } else{
                    turn = 1;
                }
            }
        }
        if(flagCheckGameNotOver == 0){
            return 0.5;
        }
        double average = ((double) sum)/ ((double) counter);
        return average;
    }

    /**
     * Check if the Human Player wins the game
     */
    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {
                    gameover = 1;
                    if(flipValue==0)
                        status = 1;
                    else if(flipValue == 1)
                        status = 2;


                } else if (playBoard[i][0].getText().toString().equals("O")) {
                    gameover = 1;
                    if(flipValue == 0)
                        status = 2;
                    else if(flipValue == 1)
                        status = 1;

                }
                if (!playBoard[i][0].getText().toString().equals(" ")) {
                    playBoard[i][0].setTextColor(Color.BLUE);
                    playBoard[i][1].setTextColor(Color.BLUE);
                    playBoard[i][2].setTextColor(Color.BLUE);
                }

            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {
                    gameover = 1;
                    if(flipValue == 0)
                        status = 1;
                    else if(flipValue == 1)
                        status = 2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {
                    gameover = 1;
                    if(flipValue == 0)
                        status = 2;
                    else if(flipValue == 1)
                        status = 1;
                }
                if (!playBoard[0][i].getText().toString().equals(" ")) {
                    playBoard[0][i].setTextColor(Color.BLUE);
                    playBoard[1][i].setTextColor(Color.BLUE);
                    playBoard[2][i].setTextColor(Color.BLUE);
                }
            }
        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {
                gameover = 1;
                if(flipValue == 0)
                    status = 1;
                else if(flipValue == 1)
                    status = 2;


            } else if (playBoard[0][0].getText().toString().equals("O")) {
                gameover = 1;
                if(flipValue == 0)
                    status = 2;
                else if(flipValue == 1)
                    status = 1;

            }
            if (!playBoard[0][0].getText().toString().equals(" ")) {
                playBoard[0][0].setTextColor(Color.BLUE);
                playBoard[1][1].setTextColor(Color.BLUE);
                playBoard[2][2].setTextColor(Color.BLUE);
            }
        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {
                gameover = 1;
                if(flipValue == 0)
                    status = 1;
                else if(flipValue == 1)
                    status = 2;
            } else if (playBoard[0][2].getText().toString().equals("O")) {
                gameover = 1;
                if(flipValue == 0)
                    status = 2;
                else if(flipValue == 1)
                    status = 1;
            }
            if (!playBoard[2][0].getText().toString().equals(" ")) {
                playBoard[2][0].setTextColor(Color.BLUE);
                playBoard[1][1].setTextColor(Color.BLUE);
                playBoard[0][2].setTextColor(Color.BLUE);
            }
        }
    }

    /**
     * Determine the winner of the game
     * @return
     */
    public int checkWinComp() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {

                    if(flipValue == 0)
                        return 1;
                    else if(flipValue == 1)
                        return 2;

                } else if (playBoard[i][0].getText().toString().equals("O")) {

                    if(flipValue == 0)
                        return 2;
                    else if(flipValue == 1)
                        return 1;
                }
            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {

                    if(flipValue == 0)
                        return 1;
                    else if(flipValue == 1)
                        return 2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {

                    if(flipValue == 0)
                        return 2;
                    else if(flipValue == 1)
                        return 1;
                }
            }
        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {

                if(flipValue == 0)
                    return 1;
                else if(flipValue == 1)
                    return 2;

            } else if (playBoard[0][0].getText().toString().equals("O")) {

                if(flipValue == 0)
                    return 2;
                else if(flipValue == 1)
                    return 1;

            }
        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {

                if(flipValue == 0)
                    return 1;
                else if(flipValue == 1)
                    return 2;

            } else if (playBoard[0][2].getText().toString().equals("O")) {

                if(flipValue == 0)
                    return 2;
                else if(flipValue == 1)
                    return 1;
            }
        }
        return 0;
    }

    /**
     * Click event called when the Restart Game button is clicked
     * @param view
     */
    public void startNewGame(View view){
        Intent mainActivity = new Intent(SinglePlayerGameActivity.this, MainActivity.class);
        if(mainActivity.resolveActivity(getPackageManager())!=null){
            startActivity(mainActivity);
            finish();
        }
    }

    /**
     * Function to play randomly.
     * Select what area of the board to mark
     */
    public void randomPlay(){
        int random = (int)(Math.random() * 9);
        int i=random / 3;
        int j=random % 3;
        playBoard[i][j].setText("X");
        boardMatrix[i][j] = 1;
    }

    /**
     * Onclick event handler to reset the board
     * @param view
     */
    public void resetBoard(View view){
        status = 0;
        gameover = 0;
        turn=1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setText(" ");
                playBoard[i][j].setTextColor(Color.WHITE);
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==0){
            if(flagEndGame==1){
                flipValue=1;
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn=player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
        }
        else if(flipValue==1 ){
            if(flagEndGame==1){
                flipValue=0;
                displayTurn=player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
        }
        flagEndGame=0;
        if(flipValue==1){
            randomPlay();
            turn=2;
        }
    }
}
