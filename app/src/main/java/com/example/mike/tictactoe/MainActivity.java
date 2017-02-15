package com.example.mike.tictactoe;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private boolean currentPlayerIsX = true;

    //Logical tic-tac-toe-grid {0 empty, 1 X, 2 O}
    private int[] cells = { 0,0,0,  //0, 1, 2
                            0,0,0,  //3, 4, 5
                            0,0,0   //6, 7 ,8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGame();
        return;
    }

    //Start new game, reset grid, set cell states
    private void initGame(){
        currentPlayerIsX = true;
        for (int i = 0; i < 9;i++){
            cells[i] = 0;
        }
        for (View button : ((GridLayout) findViewById(R.id.cell_grid)).getTouchables() ){
            ((ImageButton) button).setImageResource(R.drawable.empty_cell);
            button.setOnClickListener(gridCellClick);
        }
        return;
    }

    //game play methods

    //player clicks a square
    private View.OnClickListener gridCellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cellIndex = 0;
            int playerID = currentPlayerIsX ? 1 : 2;
            switch (v.getId()){
                case R.id.topLeftCell:
                    cellIndex = 0;
                    setCell(cellIndex, R.id.topLeftCell);
                    break;
                case R.id.topCenterCell:
                    cellIndex = 1;
                    setCell(cellIndex, R.id.topCenterCell);
                    break;
                case R.id.topRightCell:
                    cellIndex = 2;
                    setCell(cellIndex, R.id.topRightCell);
                    break;
                case R.id.middleLeftCell:
                    cellIndex = 3;
                    setCell(cellIndex, R.id.middleLeftCell);
                    break;
                case R.id.middleCenterCell:
                    cellIndex = 4;
                    setCell(cellIndex, R.id.middleCenterCell);
                    break;
                case R.id.middleRightCell:
                    cellIndex = 5;
                    setCell(cellIndex, R.id.middleRightCell);
                    break;
                case R.id.bottomLeftCell:
                    cellIndex = 6;
                    setCell(cellIndex, R.id.bottomLeftCell);
                    break;
                case R.id.bottomCenterCell:
                    cellIndex = 7;
                    setCell(cellIndex, R.id.bottomCenterCell);
                    break;
                case R.id.bottomRightCell:
                    cellIndex = 8;
                    setCell(cellIndex, R.id.bottomRightCell);
                    break;
            }

            if (cells[cellIndex] != 0) {
                endTurn();
                checkWinState();
            }

            return;
        }
    };

    //set cell to players value X or O
    private void setCell(int cellIndex, int viewId){
        if (cells[cellIndex] == 0){
            cells[cellIndex] = currentPlayerIsX ? 1 : 2;
            View button = findViewById(viewId);
            ((ImageButton) button).setImageResource(currentPlayerIsX ? R.drawable.x : R.drawable.o);
        }
    }

    //check for a winner or cat's game
    private void checkWinState(){
        int winnerInt = 0;
        boolean hasWinner = false;
        //Horizontal win conditions
        if (cells[0] != 0 && cells[0] == cells[1] && cells[0] == cells[2]) {
            winnerInt = cells[0];
            hasWinner = true;
        }
        if (!hasWinner && cells[3] != 0 && cells[3] == cells[4] && cells[3] == cells[5]) {
            winnerInt = cells[3];
            hasWinner = true;
        }
        if (!hasWinner && cells[6] != 0 && cells[6] == cells[7] && cells[6] == cells[8]) {
            winnerInt = cells[6];
            hasWinner = true;
        }
        // Vertical win conditions
        if (!hasWinner && cells[0] != 0 && cells[0] == cells[3] && cells[0] == cells[6]) {
            winnerInt = cells[0];
            hasWinner = true;
        }
        if (!hasWinner && cells[1] != 0 && cells[1] == cells[4] && cells[1] == cells[7]) {
            winnerInt = cells[1];
            hasWinner = true;
        }
        if (!hasWinner && cells[2] != 0 && cells[2] == cells[5] && cells[2] == cells[8]) {
            winnerInt = cells[2];
            hasWinner = true;
        }
        // Diagonal win conditions
        if (!hasWinner && cells[4] != 0 &&
                ((cells[4] == cells[0] && cells[4] == cells[8]) ||
                (cells[4] == cells[2] && cells[4] == cells[6]))
            ) {
            winnerInt = cells[4];
            hasWinner = true;
        }

        if (winnerInt != 0 || sumArray(cells) == 13){
            notifyWinner(winnerInt);
        }
        return;
    }

    //change controlling player at the end of a turn.
    private void endTurn(){
        currentPlayerIsX = !currentPlayerIsX;
        return;
    }

    //Win/Tie dialog and post dialog actions
    private void notifyWinner(int winnerInt){
        String winner = "C";
        String alertMessage = "";
        int messageID = R.string.value_not_set;
        if (winnerInt > 0){
            winner = winnerInt == 1 ? "X" : "O";
        }
        switch (winner){
            case "X":
                messageID = R.string.x_wins;
                break;
            case "O":
                messageID = R.string.o_wins;
                break;
            case "C":
                messageID = R.string.cats_game;
                break;
        }

        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(R.string.play_again)
                .setMessage(messageID)
                .setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        initGame();
                    }
                })
                .setNegativeButton(R.string.close_app, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //helper method
    private int sumArray(int [] numericArray){
        int result = 0;
        for (int i = 0; i < numericArray.length; i++){
            result += numericArray[i];
        }
        return result;
    }
}
