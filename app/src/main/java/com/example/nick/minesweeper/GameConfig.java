package com.example.nick.minesweeper;

/**
 * Created by Nick on 08/02/2017.
 */

public class GameConfig {
    private int numRows;
    private int numCols;
    private int numMines;
    private int[][][] numHighScore;
    private int numGamesPlayed;

    //CONSTRUCTOR
    public GameConfig() {
        this.numRows = 4;
        this.numCols = 6;
        this.numMines = 6;
        this.numHighScore = new int[3][3][4];
        this.numGamesPlayed = 0;
    }

    //RETURN CONFIG SETTINGS
    public int[] getConfig(){
        int[] data = {this.numRows, this.numCols, this.numMines, this.numGamesPlayed};
        return data;
    }

    //return highscore in flattened array
    public int[] getHighscore(){
        int[] flattenedArray = new int[36];
        int count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 4; k++){
                    flattenedArray[count] = this.numHighScore[i][j][k];
                    count++;
                }
            }
        }
        return flattenedArray;
    }

    //convert flattened array into 3D array for highscore
    public void unflatten3DArray(int[] flattenedArray){
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 4; k++) {
                    this.numHighScore[i][j][k] = flattenedArray[count];
                    count++;
                }
            }
        }

    }

    //SET CONFIG SETTINGS
    public void setConfig(int[] data, int[] highScore){
        this.numRows = data[0];
        this.numCols = data[1];
        this.numMines = data[2];
        this.numGamesPlayed = data[3];
        unflatten3DArray(highScore);
    }

    //num rows setter
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    //num cols setter
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    //num mine setter
    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    //highscore setter
    public void setNumHighScore(int x, int y, int z, int numHighScore) {
        this.numHighScore[x][y][z] = numHighScore;
    }

    //num games played setter
    public void setNumGamesPlayed(int numGamesPlayed) {
        this.numGamesPlayed = numGamesPlayed;
    }

    //num rows getter
    public int getNumRows() {
        return numRows;
    }

    //num cols getter
    public int getNumCols() {
        return numCols;
    }

    //num mines getter
    public int getNumMines() {
        return numMines;
    }

    //highscore getter
    public int getNumHighScore(int x, int y, int z) {
        return numHighScore[x][y][z];
    }

    //num games played getter
    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    //reset the stats
    public void reset(){
        this.numGamesPlayed = 0;
        for(int i =0; i < 3 ;i++){
            for(int j = 0; j< 3; j++){
                for(int k = 0; k < 4; k++){
                    this.numHighScore[i][j][k] = 0;
                }
            }
        }
    }
}
