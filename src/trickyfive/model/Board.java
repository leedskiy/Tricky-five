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

    @Override
    public String toString() {
        String boardArrayElements = "";

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                boardArrayElements += "[" + i + ", " + j + "] = " + this.boardArray.get(i).get(j) + "\n";
            }
        }

        return boardArrayElements;
    }
}