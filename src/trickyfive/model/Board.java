package trickyfive.model;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Integer;

public class Board {
    private int size;
    private Player currPlayer;
    private ArrayList<ArrayList<Player>> boardArray;
    private ArrayList<Integer> currP34Count;
    private ArrayList<Integer> prev2Players34Count;

    public Board(int size) {
        this.size = size;
        this.currPlayer = Player.X;

        createCountingArrays();
        createBoardArray();
    }

    private void createCountingArrays() {
        this.currP34Count = new ArrayList<Integer>();
        // 0 - count of 3 adj signs for curr sign
        // 1 - count of 4 adj signs for curr sign
        this.prev2Players34Count = new ArrayList<Integer>();
        // 0 - count of 3 adj signs for X
        // 1 - count of 4 adj signs for X
        // 2 - count of 3 adj signs for O
        // 3 - count of 4 adj signs for O

        for (int i = 0; i < 2; i++) {
            this.currP34Count.add(0);
        }
        for (int i = 0; i < 4; i++) {
            this.prev2Players34Count.add(0);
        }
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

    public int getSize() {
        return this.size;
    }

    public Player getCurrPlayer() {
        return this.currPlayer;
    }

    public Player getCellPlayer(int row, int col) {
        return this.boardArray.get(row).get(col);
    }

    public void moveToNextPlayer() {
        if (this.currPlayer == Player.X) {
            this.currPlayer = Player.O;
        } else {
            this.currPlayer = Player.X;
        }
    }

    private void resetCurrP34Count() {
        currP34Count.set(0, 0);
        currP34Count.set(1, 0);
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

    private boolean deleteRandomCells() {
        ArrayList<String> playerCellsStr = getAllPlayerCellsStr();
        int size = playerCellsStr.size();
        Random rand = new Random();
        int randNum;
        String[] randCellStrSplit;
        int row;
        int col;

        int currPlayerIndex1 = this.currPlayer == Player.X ? 0 : 2;
        int currPlayerIndex2 = this.currPlayer == Player.X ? 1 : 3;

        if (size > 1
                && this.currP34Count.get(0) != this.prev2Players34Count
                        .get(currPlayerIndex1)) {
            randNum = rand.nextInt(size);
            randCellStrSplit = playerCellsStr.get(randNum).split(";");

            row = Integer.parseInt(randCellStrSplit[0]);
            col = Integer.parseInt(randCellStrSplit[1]);
            this.boardArray.get(row).set(col, Player.EMPTY);

            resetCurrP34Count();

            checkWinnerInRow();
            checkWinnerInCol();
            checkWinnerInLTRDiagonal();
            checkWinnerInRTLDiagonal();

            this.prev2Players34Count.set(currPlayerIndex1, this.currP34Count.get(0));
            this.prev2Players34Count.set(currPlayerIndex2, this.currP34Count.get(1));

            return true;
        } else if (size > 2
                && this.currP34Count.get(1) != this.prev2Players34Count
                        .get(currPlayerIndex2)) {
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

            resetCurrP34Count();

            checkWinnerInRow();
            checkWinnerInCol();
            checkWinnerInLTRDiagonal();
            checkWinnerInRTLDiagonal();

            this.prev2Players34Count.set(currPlayerIndex1, this.currP34Count.get(0));
            this.prev2Players34Count.set(currPlayerIndex2, this.currP34Count.get(1));

            return true;
        }

        return false;
    }

    private void incrCurrP34Count(int adjSignsCount) {
        if (adjSignsCount == 3) {
            this.currP34Count.set(0, currP34Count.get(0) + 1);
        } else if (adjSignsCount == 4) {
            this.currP34Count.set(1, currP34Count.get(1) + 1);
        }
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
                    if (count >= 5) {
                        return this.currPlayer;
                    } else if (count == 4 || count == 3) {
                        incrCurrP34Count(count);
                    }
                    count = 0;
                }
            }

            if (count >= 5) {
                return this.currPlayer;
            } else if (count == 4 || count == 3) {
                incrCurrP34Count(count);
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
                    if (count >= 5) {
                        return this.currPlayer;
                    } else if (count == 4 || count == 3) {
                        incrCurrP34Count(count);
                    }
                    count = 0;
                }
            }

            if (count >= 5) {
                return this.currPlayer;
            } else if (count == 4 || count == 3) {
                incrCurrP34Count(count);
            }
        }

        return Player.EMPTY;
    }

    private Player checkWinnerInLTRDiagonal() {
        for (int i = 0, j = this.size - 3; i < this.size - 2 && j >= 0;) {
            int count = 0;

            for (int k = i, l = j; k < this.size && l < this.size; ++k, ++l) {
                Player currCellPlayer = this.boardArray.get(k).get(l);
                if (currCellPlayer == this.currPlayer && count == 0) {
                    count = 1;
                } else if (currCellPlayer == this.currPlayer) {
                    ++count;
                } else {
                    if (count >= 5) {
                        return this.currPlayer;
                    } else if (count == 4 || count == 3) {
                        incrCurrP34Count(count);
                    }
                    count = 0;
                }
            }

            if (count >= 5) {
                return this.currPlayer;
            } else if (count == 4 || count == 3) {
                incrCurrP34Count(count);
            }

            if (j != 0) {
                --j;
            } else {
                ++i;
                j = 0;
            }
        }

        return Player.EMPTY;
    }

    private Player checkWinnerInRTLDiagonal() {
        for (int i = 0, j = 2; i < this.size - 2 && j < this.size;) {
            int count = 0;

            for (int k = i, l = j; k < this.size && l > -1; ++k, --l) {
                Player currCellPlayer = this.boardArray.get(k).get(l);
                if (currCellPlayer == this.currPlayer && count == 0) {
                    count = 1;
                } else if (currCellPlayer == this.currPlayer) {
                    ++count;
                } else {
                    if (count >= 5) {
                        return this.currPlayer;
                    } else if (count == 4 || count == 3) {
                        incrCurrP34Count(count);
                    }
                    count = 0;
                }
            }

            if (count >= 5) {
                return this.currPlayer;
            } else if (count == 4 || count == 3) {
                incrCurrP34Count(count);
            }

            if (j != this.size - 1) {
                ++j;
            } else {
                ++i;
                j = this.size - 1;
            }
        }

        return Player.EMPTY;
    }

    public Player checkWinner() {
        resetCurrP34Count();

        if (checkWinnerInRow() != Player.EMPTY ||
                checkWinnerInCol() != Player.EMPTY ||
                checkWinnerInLTRDiagonal() != Player.EMPTY ||
                checkWinnerInRTLDiagonal() != Player.EMPTY) {
            return this.currPlayer;
        }

        deleteRandomCells();
        return Player.EMPTY;
    }

    public boolean checkGameEnd() {
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                if (this.boardArray.get(i).get(j) == Player.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean placePlayerInCell(int row, int col) {
        if (this.boardArray.get(row).get(col) == Player.EMPTY) {
            this.boardArray.get(row).set(col, this.currPlayer);
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