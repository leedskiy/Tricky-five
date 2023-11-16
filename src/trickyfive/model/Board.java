package trickyfive.model;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Integer;

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
        return this.currPlayer;
    }

    public Player getCellPlayer(int row, int col) {
        return this.boardArray.get(row).get(col);
    }

    private void moveToNextPlayer() {
        if (this.currPlayer == Player.X) {
            this.currPlayer = Player.O;
        } else {
            this.currPlayer = Player.X;
        }
    }

    private ArrayList<String> getAllPlayerCellsStr() {
        ArrayList<String> arrList = new ArrayList<String>();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (boardArray.get(i).get(j) == this.currPlayer) {
                    arrList.add(i + ";" + j);
                }
            }
        }

        return arrList;
    }

    private boolean deleteRandomCells(int count) {
        ArrayList<String> playerCellsStr = getAllPlayerCellsStr();
        int size = playerCellsStr.size();
        Random rand = new Random();
        int randNum;
        String[] randCellStrSplit;
        int row;
        int col;

        if (size > 1 && count == 3) {
            randNum = rand.nextInt(size);
            randCellStrSplit = playerCellsStr.get(randNum).split(";");

            row = Integer.parseInt(randCellStrSplit[0]);
            col = Integer.parseInt(randCellStrSplit[1]);
            this.boardArray.get(row).set(col, Player.EMPTY);

            return true;
        } else if (size > 2 && count == 4) {
            randNum = rand.nextInt(size);
            randCellStrSplit = playerCellsStr.get(randNum).split(";");

            row = Integer.parseInt(randCellStrSplit[0]);
            col = Integer.parseInt(randCellStrSplit[1]);
            this.boardArray.get(row).set(col, Player.EMPTY);

            playerCellsStr.remove(randNum);
            size = playerCellsStr.size();
            randNum = rand.nextInt(size);
            randCellStrSplit = playerCellsStr.get(randNum).split(";");

            row = Integer.parseInt(randCellStrSplit[0]);
            col = Integer.parseInt(randCellStrSplit[1]);

            this.boardArray.get(row).set(col, Player.EMPTY);

            return true;

        }

        return false;
    }

    private Player checkWinnerInRow() {
        for (int i = 0; i < this.size; ++i) {
            int count = 0;

            for (int j = 0; j < this.size; ++j) {
                Player currCellPlayer = this.boardArray.get(i).get(j);
                if (currCellPlayer == this.currPlayer && count == 0) {
                    count = 1;
                } else if (currCellPlayer == this.currPlayer) {
                    ++count;
                } else {
                    if (count == 5) {
                        return this.currPlayer;
                    } else if (count == 3 || count == 4) {
                        System.out.println(this); // temporary
                        deleteRandomCells(count);
                        System.out.println("deleteRandomCells Row"); // temporary
                        return Player.EMPTY;
                    }
                    count = 0;
                }
            }

            if (count == 5) {
                return this.currPlayer;
            } else if (count == 3 || count == 4) {
                deleteRandomCells(count);
                return Player.EMPTY;
            }
        }

        return Player.EMPTY;
    }

    private Player checkWinnerInCol() {
        for (int i = 0; i < this.size; ++i) {
            int count = 0;

            for (int j = 0; j < this.size; ++j) {
                Player currCellPlayer = this.boardArray.get(j).get(i);
                if (currCellPlayer == this.currPlayer && count == 0) {
                    count = 1;
                } else if (currCellPlayer == this.currPlayer) {
                    ++count;
                } else {
                    if (count == 5) {
                        return this.currPlayer;
                    } else if (count == 3 || count == 4) {
                        deleteRandomCells(count);
                        System.out.println("deleteRandomCells Col");
                        return Player.EMPTY;
                    }
                    count = 0;
                }
            }

            if (count == 5) {
                return this.currPlayer;
            } else if (count == 3 || count == 4) {
                deleteRandomCells(count);
                return Player.EMPTY;
            }
        }

        return Player.EMPTY;
    }

    public Player checkWinner() {
        if (checkWinnerInRow() != Player.EMPTY ||
                checkWinnerInCol() != Player.EMPTY) {
            System.out.println(this.currPlayer + " is the winner"); // temporary
            return this.currPlayer;
        }

        return Player.EMPTY;
    }

    public boolean placePlayerInCell(int row, int col) {
        if (this.boardArray.get(row).get(col) == Player.EMPTY) {
            this.boardArray.get(row).set(col, this.currPlayer);
            checkWinner(); // temporary
            moveToNextPlayer();
            // System.out.println(this); // temporary
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
                    boardArrayElements += "  " + boardElem + "   ";
                }
            }

            boardArrayElements += "\n";
        }

        return boardArrayElements;
    }
}