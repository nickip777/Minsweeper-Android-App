package com.example.nick.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionsScreen extends AppCompatActivity {
    private static final String TAG = "Options Screen Activity";
    private static final String GAME_CONFIG = "GAME_CONFIG";
    public static final String GAME_HIGHSCORE = "GAME_HIGHSCORE";
    private GameConfig optionsConfig;
    private int x,y,z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        this.optionsConfig = new GameConfig();
        extractDataFromIntent();
        //convert options to 1,2,3 for easy manage
        convertConfigToSingle();
        setupNumMinesBtns();
        setNumRowsBtns();
        setNumColsBtns();

        //update stats
        updateScore();

        //setup reset button
        setupButtonReset();
    }

    //reset the scores
    private void setupButtonReset() {
        Button btn = (Button) findViewById(R.id.buttonReset);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                optionsConfig.reset();
                updateScore();
            }
        });
    }

    //update the scores
    private void updateScore() {
        TextView textHighScore = (TextView) findViewById(R.id.textViewSetBestScore);
        TextView textNumGamesPlayed = (TextView) findViewById(R.id.textViewSetNumGamesPlayed);
        textHighScore.setText("" + optionsConfig.getNumHighScore(x,y,z));
        textNumGamesPlayed.setText("" + optionsConfig.getNumGamesPlayed());
    }


    //BUTTONS FOR ROWS MINES AND COLUMNS *******************************************************
    //each button press updates current config option file
    private void setNumColsBtns() {
        final Button btn1 = (Button) findViewById(R.id.buttonCol6);
        final Button btn2 = (Button) findViewById(R.id.buttonCol10);
        final Button btn3 = (Button) findViewById(R.id.buttonCol15);
        //update config UI from previous setting
        if(optionsConfig.getNumCols() == 6){
            colBtnChangeColor(btn1,btn2,btn3);
        }else if(optionsConfig.getNumCols() == 10){
            colBtnChangeColor(btn2, btn1,btn3);
        }else{
            colBtnChangeColor(btn3,btn2,btn1);
        }
        changeSelectedButtonCols(btn1, btn2, btn3, 6);
        changeSelectedButtonCols(btn2, btn1, btn3, 10);
        changeSelectedButtonCols(btn3, btn2, btn1, 15);
    }

    //on click button update UI and config
    private void changeSelectedButtonCols(final Button btn1, final Button btn2, final Button btn3, final int numSelected) {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsConfig.setNumCols(numSelected);
                convertConfigToSingle();
                updateScore();
                colBtnChangeColor(btn1, btn2, btn3);
            }
        });
    }

    //change color
    private void colBtnChangeColor(Button btn1, Button btn2, Button btn3) {

        btn1.setBackgroundColor(Color.CYAN);
        btn2.setBackgroundResource(android.R.drawable.btn_default);
        btn3.setBackgroundResource(android.R.drawable.btn_default);
    }

    //on click button for UI and config
    private void changeSelectedButtonRows(final Button btn1, final Button btn2, final Button btn3, final int numSelected) {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsConfig.setNumRows(numSelected);
                convertConfigToSingle();
                updateScore();
                rowBtnChangeColor(btn1, btn2, btn3);
            }
        });
    }

    //change row color
    private void rowBtnChangeColor(Button btn1, Button btn2, Button btn3) {
        btn1.setBackgroundColor(Color.CYAN);
        btn2.setBackgroundResource(android.R.drawable.btn_default);
        btn3.setBackgroundResource(android.R.drawable.btn_default);
    }

    //set num rows
    private void setNumRowsBtns() {
        final Button btn1 = (Button) findViewById(R.id.buttonRow4);
        final Button btn2 = (Button) findViewById(R.id.buttonRow5);
        final Button btn3 = (Button) findViewById(R.id.buttonRow6);
        //update config UI from previous setting
        if(optionsConfig.getNumRows() == 4){
            rowBtnChangeColor(btn1,btn2,btn3);
        }else if(optionsConfig.getNumRows() == 5){
            rowBtnChangeColor(btn2, btn1,btn3);
        }else{
            rowBtnChangeColor(btn3,btn2,btn1);
        }

        changeSelectedButtonRows(btn1, btn2, btn3, 4);
        changeSelectedButtonRows(btn2, btn1, btn3, 5);
        changeSelectedButtonRows(btn3, btn2, btn1, 6);


    }

    //set color
    private void setColor3(Button btn1, Button btn2, Button btn3) {
        btn1.setBackgroundResource(android.R.drawable.btn_default);
        btn2.setBackgroundResource(android.R.drawable.btn_default);
        btn3.setBackgroundResource(android.R.drawable.btn_default);
    }

    //set num mines
    private void setupNumMinesBtns() {

        final Button btn1 = (Button) findViewById(R.id.buttonMine6);
        final Button btn2 = (Button) findViewById(R.id.buttonMine10);
        final Button btn3 = (Button) findViewById(R.id.buttonMine15);
        final Button btn4 = (Button) findViewById(R.id.buttonMine20);
        //update config UI from previous setting
        if(optionsConfig.getNumMines() == 6){
            btn1.setBackgroundColor(Color.CYAN);
            setColor3(btn2, btn4, btn3);
        }else if(optionsConfig.getNumMines() == 10){
            btn2.setBackgroundColor(Color.CYAN);
            setColor3(btn1, btn4, btn3);
        }else if(optionsConfig.getNumMines() == 15){
            btn3.setBackgroundColor(Color.CYAN);
            setColor3(btn1, btn4, btn2);
        }else if(optionsConfig.getNumMines() == 20){
            btn4.setBackgroundColor(Color.CYAN);
            setColor3(btn1, btn2, btn3);
        }


        changeSelectedMines(btn1, btn3, btn4, btn2, 6);
        changeSelectedMines(btn2, btn1, btn4, btn3, 10);
        changeSelectedMines(btn3, btn1, btn4, btn2, 15);
        changeSelectedMines(btn4, btn1, btn3, btn2, 20);

    }

    //on click UI update and config
    private void changeSelectedMines(final Button btn1, final Button btn3, final Button btn4, final Button btn2, final int numSelected) {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsConfig.setNumMines(numSelected);
                convertConfigToSingle();
                updateScore();
                btn1.setBackgroundColor(Color.CYAN);
                setColor3(btn2, btn4, btn3);
            }
        });
    }



    //METHODS FOR ACTION BAR************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back_button:
                Log.d(TAG, "User Clicked Back (Action Bar)");
                Intent intent = new Intent();
                intent.putExtra(GAME_CONFIG, optionsConfig.getConfig());
                intent.putExtra(GAME_HIGHSCORE, optionsConfig.getHighscore());
                setResult(Activity.RESULT_OK, intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context, int[] data, int[] highScore){
        Intent intent = new Intent(context, OptionsScreen.class);
        intent.putExtra(GAME_CONFIG, data);
        intent.putExtra(GAME_HIGHSCORE, highScore);
        return intent;
    }

    private void extractDataFromIntent(){
        Intent intent = getIntent();
        int[] data = intent.getIntArrayExtra(GAME_CONFIG);
        int[] highScore = intent.getIntArrayExtra(GAME_HIGHSCORE);
        Log.d(TAG, "" + data[0]+data[1]+data[2]+data[3]);
        optionsConfig.setConfig(data, highScore);
    }

    //convert options to easy use(1,2,3)
    private void convertConfigToSingle(){
        if(optionsConfig.getNumRows() == 4){
            this.x = 0;
        }else if(optionsConfig.getNumRows() == 5){
            this.x = 1;
        }else{
            this.x = 2;
        }

        if(optionsConfig.getNumCols() == 6){
            this.y = 0;
        }else if(optionsConfig.getNumCols() == 10){
            this.y = 1;
        }else{
            this.y = 2;
        }

        if(optionsConfig.getNumMines() == 6){
            this.z = 0;
        }else if(optionsConfig.getNumMines() == 10){
            this.z = 1;
        }else if(optionsConfig.getNumMines() == 15){
            this.z = 2;
        }else{
            this.z = 3;
        }
    }


}
