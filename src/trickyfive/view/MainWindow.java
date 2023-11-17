package trickyfive.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends Window {

    private ArrayList<Window> windows;

    public MainWindow() {
        windows = new ArrayList<Window>();

        JLabel label = new JLabel();
        label.setBorder(new EmptyBorder(20, 0, 20, 0));
        label.setText("Choose the size of the game board");

        JButton button6x6 = createButton("6 x 6", 6);
        button6x6.setBorder(new EmptyBorder(13, 13, 13, 13));

        JButton button10x10 = createButton("10 x 10", 10);
        button10x10.setBorder(new EmptyBorder(13, 13, 13, 13));

        JButton button14x14 = createButton("14 x 14", 14);
        button14x14.setBorder(new EmptyBorder(13, 13, 13, 13));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(label);
        getContentPane().add(button6x6);
        getContentPane().add(button10x10);
        getContentPane().add(button14x14);

        this.setVisible(true);
    }

    private ActionListener getActionListener(int size) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = new GameWindow(MainWindow.this, size);
                windows.add(window);
            }
        };
    }

    private JButton createButton(String text, int size) {
        JButton button = new JButton(text);
        button.addActionListener(getActionListener(size));
        return button;
    }

    public ArrayList<Window> getAllWindows() {
        return windows;
    }
}
