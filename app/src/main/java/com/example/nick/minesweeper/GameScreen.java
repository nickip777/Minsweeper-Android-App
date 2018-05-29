package com.example.nick.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    public static final String GAME_HIGHSCORE = "GAME_HIGHSCORE";
    private static final String GAME_CONFIG = "GAME_CONFIG";
    private int x,y,z;
    Button buttons[][];
    private int numMinesFound = 0;
    private int numUserClicks = 0;

    private GameConfig optionsConfig;
    private MineMap mineMap;
    private MediaPlayer beep;
    private MediaPlayer go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        optionsConfig = new GameConfig();
        extractDataFromIntent();
        //convert options into manageable ints
        convertConfigToSingle();
        //setup button array
        this.buttons = new Button[optionsConfig.getNumRows()][optionsConfig.getNumCols()];

        //create new game instance
        mineMap = new MineMap(optionsConfig.getNumRows(), optionsConfig.getNumCols(), optionsConfig.getNumMines());

        //update UI
        populateButtons();
        updateUIText();

        //start sound
        go = MediaPlayer.create(GameScreen.this, R.raw.go);
        go.start();
    }

    //populate buttons for game screen
    private void populateButtons() {
        final Animation animScale = AnimationUtils.loadAnimation(this,R.anim.scale);;
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for(int row = 0; row < optionsConfig.getNumRows(); row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for(int col = 0; col < optionsConfig.getNumCols(); col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                //make text not clip
                button.setPadding(0,0,0,0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animScale);
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }


                });
                tableRow.addView(button);
                buttons[row][col]= button;
            }
        }
    }

    //action when button clicked
    private void gridButtonClicked(int row, int col) {
        Button button = buttons[row][col];
        //if block is mine
        if(mineMap.isMine(row,col)){

            //if revealed, set the num over bomb
            if(mineMap.isRevealed(row,col)){
                button.setText("" + mineMap.getNumMines(row,col));
                updateUIText();
                mineMap.setBombFound(row,col);
            }else{
                //when new bomb found
                //sound
                beep = MediaPlayer.create(GameScreen.this, R.raw.beep);
                beep.start();

                //update image
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));
                mineMap.setRevealed(row,col);
                updateUIButtons(row,col);
                numMinesFound++;
                numUserClicks++;
                updateUIText();

                //if required num of mines found end game
                if(numMinesFound == optionsConfig.getNumMines()){
                    //show dialog
                    FragmentManager manager = getSupportFragmentManager();
                    MessageFragment dialog = new MessageFragment();
                    dialog.setCancelable(false);
                    dialog.show(manager, "MessageDialog");

                    //set highscore
                    if(optionsConfig.getNumHighScore(x,y,z) == 0){
                        optionsConfig.setNumHighScore(x,y,z,numUserClicks);
                    }else if(optionsConfig.getNumHighScore(x,y,z) > numUserClicks){
                        optionsConfig.setNumHighScore(x,y,z,numUserClicks);
                    }
                    optionsConfig.setNumGamesPlayed(optionsConfig.getNumGamesPlayed() + 1);
                    Intent intent = new Intent();
                    intent.putExtra(GAME_CONFIG, optionsConfig.getConfig());
                    intent.putExtra(GAME_HIGHSCORE, optionsConfig.getHighscore());

                    setResult(Activity.RESULT_FIRST_USER, intent);
                    //finish();
                }
            }
            //if not revealed
        }else if(!mineMap.isRevealed(row,col)){
            //update UI
            mineMap.setRevealed(row,col);
            button.setText("" + mineMap.getNumMines(row, col));
            updateUIButtons(row,col);
            numUserClicks++;
            updateUIText();
        }


    }

    //update UI buttons
    private void updateUIButtons(int row, int col){
        Button[][] button = buttons;
        for(int i = 0; i < optionsConfig.getNumCols(); i++){
            if(mineMap.isRevealed(row,i) && mineMap.isBombFound(row,i)) {
                button[row][i].setText("" + mineMap.getNumMines(row, i));
            }
            if(mineMap.isRevealed(row,i)){
                if (mineMap.isMine(row,i) && !mineMap.isBombFound(row,i)){
                    continue;
                }
                else{
                    button[row][i].setText("" + mineMap.getNumMines(row, i));
                }
            }
        }
        for(int i = 0; i < optionsConfig.getNumRows(); i++){
            if(mineMap.isRevealed(i,col) && mineMap.isBombFound(i,col)) {
                button[i][col].setText("" + mineMap.getNumMines(i, col));
            }
            if(mineMap.isRevealed(i,col)){
                if (mineMap.isMine(i,col) && !mineMap.isBombFound(i,col)){
                    continue;
                }
                else{
                    button[i][col].setText("" + mineMap.getNumMines(i,col));
                }
            }
        }
    }

    //make intent
    public static Intent makeIntent(Context context, int[] data, int[] highScore){
        Intent intent = new Intent(context, GameScreen.class);
        intent.putExtra(GAME_CONFIG, data);
        intent.putExtra(GAME_HIGHSCORE, highScore);
        return intent;
    }

    //extract the data
    private void extractDataFromIntent(){
        Intent intent = getIntent();
        int[] data = intent.getIntArrayExtra(GAME_CONFIG);
        optionsConfig.setNumRows(data[0]);
        optionsConfig.setNumCols(data[1]);
        optionsConfig.setNumMines(data[2]);
        optionsConfig.unflatten3DArray(intent.getIntArrayExtra(GAME_HIGHSCORE));
        optionsConfig.setNumGamesPlayed(data[3]);
    }

    //update UI for miens found and num clicks
    private void updateUIText(){
        TextView numMinesUI = (TextView) findViewById(R.id.textViewNumMinesFound);
        TextView numClicks = (TextView) findViewById(R.id.textViewNumUserClicks);
        numMinesUI.setText("Found " + numMinesFound + " of " + optionsConfig.getNumMines() + " mines.");
        numClicks.setText("# Scans used " + numUserClicks);
    }

    //convert options to 1,2,3
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
