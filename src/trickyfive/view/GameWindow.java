package trickyfive.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import trickyfive.model.Board;
import trickyfive.model.Player;

public class GameWindow extends Window {
    private MainWindow mainWindow;
    private JLabel label;
    private ArrayList<JButton> gameBoardButtons;
    private int size;
    private Board gameBoard;

    public GameWindow(MainWindow mainWindow, int size) {
        this.mainWindow = mainWindow;
        this.mainWindow.getAllWindows().add(this);
        this.label = new JLabel();
        this.gameBoardButtons = new ArrayList<JButton>();

        this.size = size;
        this.gameBoard = new Board(this.size);

        JButton restartGameButton = new JButton();
        restartGameButton.setText("Restart");
        restartGameButton.addActionListener(e -> startNewGame());
        restartGameButton.setBorder(new EmptyBorder(13, 13, 13, 13));

        JPanel topPanel = new JPanel();
        updateLabel();
        topPanel.add(this.label);
        topPanel.add(restartGameButton);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));
        initializeBoardButtons(boardPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void updateButtonsText() {
        int j = 0;
        int k = 0;

        for (int i = 0; i < this.gameBoardButtons.size(); ++i) {
            Player cellPlayer = this.gameBoard.getCellPlayer(j, k);
            if (cellPlayer == Player.EMPTY) {
                this.gameBoardButtons.get(i).setText("");
            } else {
                this.gameBoardButtons.get(i).setText(cellPlayer.toString());
            }

            if (j < this.size) {
                if (k < this.size - 1) {
                    ++k;
                } else {
                    ++j;
                    k = 0;
                }
            }
        }
    }

    private void updateLabel() {
        this.label.setText("Turn of player with sign \"" + this.gameBoard.getCurrPlayer() + "\"");
    }

    private void startNewGame() {
        Window newWindow = new GameWindow(this.mainWindow, this.size);
        newWindow.setVisible(true);
        this.dispose();
        this.mainWindow.getAllWindows().remove(this);
    }

    private void displayGameEnd(Player player) {
        if (this.gameBoard.checkGameEnd()) {
            JOptionPane.showMessageDialog(this, "It is a draw!");
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! Winner is the player with sign: \"" + player + "\"");
        }

        startNewGame();
    }

    private JButton createBoardButton(int i, int j) {
        final JButton button = new JButton();

        button.addActionListener(e -> {
            if (this.gameBoard.placePlayerInCell(i, j)) {
                Player player = this.gameBoard.checkWinner();
                updateButtonsText();

                this.gameBoard.moveToNextPlayer();
                updateLabel();

                if (player != Player.EMPTY || this.gameBoard.checkGameEnd()) {
                    displayGameEnd(player);
                }
            }
        });

        this.gameBoardButtons.add(button);
        return button;
    }

    private void initializeBoardButtons(JPanel boardPanel) {
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                boardPanel.add(createBoardButton(i, j));
            }
        }
    }

    @Override
    protected void doOnExit() {
        super.doOnExit();
        this.mainWindow.getAllWindows().remove(this);
    }
}