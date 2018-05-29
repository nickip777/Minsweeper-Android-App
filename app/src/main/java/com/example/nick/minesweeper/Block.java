package com.example.nick.minesweeper;

/**
 * Created by Nick on 08/02/2017.
 */

public class Block {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isBombFound;

    //constructor
    public Block() {
        this.isMine = false;
        this.isRevealed = false;
        this.isBombFound = false;
    }

    //revealed getter
    public boolean isRevealed() {
        return isRevealed;
    }

    //mine getter
    public boolean isMine() {
        return isMine;
    }

    //mine setter
    public void setMine() {
        this.isMine = true;
    }

    //revealed setter
    public void setRevealed() {
        this.isRevealed = true;
    }

    //bomb found setter
    public void setBombFound(){
        this.isBombFound = true;
    }

    //bomb found getter
    public boolean isBombFound(){
        return isBombFound;
    }


}
