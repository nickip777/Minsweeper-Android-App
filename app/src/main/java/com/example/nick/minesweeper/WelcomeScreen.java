package com.example.nick.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {
    public static final int REQUEST_CODE_OPTIONS = 1024;
    public static final String TAG = "Welcome Screen Activity";
    public static final int REQUEST_CODE_GAME = 1025;
    public static final String GAME_CONFIG = "GAME_CONFIG";
    public static final String GAME_HIGHSCORE = "GAME_HIGHSCORE";
    private GameConfig optionsConfig;
    private int x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionsConfig = new GameConfig();
        pullSharedPreferencesGame();
        convertConfigToSingle();
        setupStartGameBtn();
        setupOptionsBtn();
        setupHelpBtn();
    }

    //HELP BUTTON
    private void setupHelpBtn() {
        Button btn = (Button) findViewById(R.id.buttonHelp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpScreen.makeIntent(WelcomeScreen.this); //creates the helpscreen activity
                startActivity(intent); // doesn't receive anything
            }
        });
    }

    //OPTIONS BUTTON
    private void setupOptionsBtn() {
        Button btn = (Button) findViewById(R.id.buttonOptions);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PASS CONFIG
                Intent intent = OptionsScreen.makeIntent(WelcomeScreen.this, optionsConfig.getConfig(), optionsConfig.getHighscore()); // creates the options screen passing the previously saved options
                startActivityForResult(intent, REQUEST_CODE_OPTIONS); //receives the options selected for saving.
            }
        });
    }

    //START GAME
    private void setupStartGameBtn() {
        Button btn = (Button) findViewById(R.id.buttonStartGame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass config
                Intent intent = GameScreen.makeIntent(WelcomeScreen.this, optionsConfig.getConfig(), optionsConfig.getHighscore()); //creates the game passing the options to make the buttons
                startActivityForResult(intent, REQUEST_CODE_GAME); //receives the score, to see if it beats the highscore.
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPTIONS: //from options
                if (resultCode == Activity.RESULT_OK) {
                    optionsConfig.setConfig(data.getIntArrayExtra(GAME_CONFIG), data.getIntArrayExtra(GAME_HIGHSCORE));
                    convertConfigToSingle(); //changes to shorthand
                    saveSharedPreferenceGame(); //saves
                    Log.d(TAG, "the row" + optionsConfig.getNumRows() + " the col " + optionsConfig.getNumCols() + " the mines " + optionsConfig.getNumMines());
                }
                break;
            case REQUEST_CODE_GAME:
                if(resultCode == Activity.RESULT_FIRST_USER){  //first users score
                    optionsConfig.setConfig(data.getIntArrayExtra(GAME_CONFIG), data.getIntArrayExtra(GAME_HIGHSCORE));
                    convertConfigToSingle(); //changes to shorthand
                    saveSharedPreferenceGame(); //saves
                    Log.d(TAG,"" + optionsConfig.getNumRows() + optionsConfig.getNumCols() + optionsConfig.getNumMines() + optionsConfig.getNumHighScore(x,y,z) + optionsConfig.getNumGamesPlayed());
                }
                break;
        }

    }

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

    //pull saved info and store into game collection
    private void pullSharedPreferencesGame() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        optionsConfig.setNumRows(prefs.getInt("NumRows", 4));
        optionsConfig.setNumCols(prefs.getInt("NumCols", 6));
        optionsConfig.setNumMines(prefs.getInt("NumMines", 6));
        optionsConfig.setNumGamesPlayed(prefs.getInt("NumGames",0));
        int[] scores = new int[36];
        for(int i = 0; i < 36;i ++){
            scores[i] = prefs.getInt("IntVal" + i, 0);
        }
        optionsConfig.unflatten3DArray(scores);
        Log.d(TAG, "Shared Preferences pulled");
    }

    //save games into shared preferences
    private void saveSharedPreferenceGame() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        //clear editor each time to ensure accuracy
        editor.clear();
        editor.putInt("NumRows", optionsConfig.getNumRows());
        editor.putInt("NumCols", optionsConfig.getNumCols());
        editor.putInt("NumMines", optionsConfig.getNumMines());
        editor.putInt("NumGames", optionsConfig.getNumGamesPlayed());
        int count = 0;
        for(int i:optionsConfig.getHighscore()){
            editor.putInt("IntVal"+count, i);
            count++;
        }
        editor.apply();
    }


}
