package com.example.nick.minesweeper;

import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nick on 08/02/2017.
 */

public class MineMap {
    private int numRows;
    private int numCols;
    private int numMines;

    private Block[][] blocks;

    //constructor
    public MineMap(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.blocks = new Block[numRows][numCols];
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                blocks[i][j] = new Block();
            }
        }
        //randomize int arraylist to randomly place mines
        ArrayList<Point> points = new ArrayList<Point>();
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                points.add(new Point(i,j));
            }
        }


        //randomize
        Collections.shuffle(points);

        //place mines
        for(int i = 0; i < numMines; i++){
            blocks[points.get(i).x][points.get(i).y].setMine();
        }

    }

    //get total num mines of row and col of current block
    public int getNumMines(int row, int col){
        int sum = 0;
        //all mines in row
        for(int i = 0; i < numCols; i++){
            if(blocks[row][i].isMine() && !blocks[row][i].isRevealed()){
                sum++;
            }
        }

        //all mines in column
        for(int i = 0; i < numRows; i++){
            if(blocks[i][col].isMine() && !blocks[i][col].isRevealed()){
                sum++;
            }
        }

        return sum;
    }

    //mine getter
    public boolean isMine(int row, int col){
        return blocks[row][col].isMine();
    }

    //revealed setter
    public void setRevealed(int row, int col){
        this.blocks[row][col].setRevealed();
    }

    //revealed getter
    public boolean isRevealed(int row, int col){
        return this.blocks[row][col].isRevealed();
    }

    //bomb setter
    public void setBombFound(int row, int col){
        this.blocks[row][col].setBombFound();
    }

    //bomb getter
    public boolean isBombFound(int row, int col){
        return this.blocks[row][col].isBombFound();
    }




}
