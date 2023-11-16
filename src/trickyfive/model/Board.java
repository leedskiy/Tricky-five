package trickyfive.model;

import java.util.ArrayList;

public class Board {
    private int size;
    private Player currPlayer;
    private ArrayList<ArrayList<Player>> boardArray;

    public Board(int size) {
        this.size = size;
        this.currPlayer = Player.X;
        createBoardArray();
    }

    private void createBoardArray() {
        this.boardArray = new ArrayList<ArrayList<Player>>();
        for (int i = 0; i < this.size; ++i) {
            this.boardArray.add(new ArrayList<Player>(this.size));

            for (int j = 0; j < this.size; ++j) {
                this.boardArray.get(i).add(Player.EMPTY);
            }
        }
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Player getCellPlayer(int row, int col) {
        return boardArray.get(row).get(col);
    }

    private void moveToNextPlayer() {
        if (this.currPlayer == Player.X) {
            this.currPlayer = Player.O;
        } else {
            this.currPlayer = Player.X;
        }
    }

    public boolean placePlayerInCell(int row, int col) {
        if (this.boardArray.get(row).get(col) == Player.EMPTY) {
            this.boardArray.get(row).set(col, this.currPlayer);
            moveToNextPlayer();
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String boardArrayElements = "";

        for (int i = 0; i < this.size; ++i) {
            if (i == 0) {
                for (int j = 0; j < this.size; ++j) {
                    boardArrayElements += "     " + j;
                }
                boardArrayElements += "\n";
            }

            boardArrayElements += i + "\t";

            for (int j = 0; j < this.size; ++j) {
                Player boardElem = this.boardArray.get(i).get(j);
                if (boardElem == Player.EMPTY) {
                    boardArrayElements += boardElem + " ";
                } else {
                    boardArrayElements += boardElem + "     ";
                }
            }

            boardArrayElements += "\n";
        }

        return boardArrayElements;
    }
}