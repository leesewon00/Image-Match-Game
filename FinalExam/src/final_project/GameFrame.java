package final_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static final_project.MessageManager.*;

public class GameFrame extends JFrame {
    private int N;
    private String type;
    private ImageManager imageManager;
    private JButton[] cardButtons;
    private boolean[] isFlipped;
    private JButton firstFlipped;
    private JButton secondFlipped;
    private int matchCount;

    public GameFrame(String type, int N) {
        super("in game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* start game */
        createMenuBar();
        setUpValue(type, N);
        createCardButtons(getContentPane());
        /* start game */

        setLayout(new GridLayout(N, N, 1, 1));
        setSize(800, 800);
        setVisible(true);
        AudioManager.loadAudio("src/final_project/musics/cruising-down-8bit-lane-159615.wav");
    }

    private void setUpValue(String type, int N) {
        this.N = N;
        this.type = type;
        this.matchCount = 0;
        this.cardButtons = new JButton[N * N];
        this.isFlipped = new boolean[N * N];

        this.imageManager = new ImageManager(N);
        imageManager.setGameImages(type, N * N);
        imageManager.shuffleImages();
        this.isFlipped = imageManager.getIsFlipped();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("options");
        JMenuItem settings = new JMenuItem("settings");
        JMenuItem exit = new JMenuItem("exit");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.stopAudio();
                setVisible(false);
                new ImageMatchGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(settings);
        menu.addSeparator();
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void createCardButtons(Container gameContainer) {
        for (int i = 0; i < N * N; i++) {
            cardButtons[i] = createButton(i);
            gameContainer.add(cardButtons[i]);
        }
    }

    private JButton createButton(int index) {
        JButton cardButton = new JButton();
        cardButton.setIcon(null);
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipCard(cardButton, index);
            }
        });
        return cardButton;
    }

    private void flipCard(JButton cardButton, int index) {
        isFlipped[index] = true;
        // update Icon (back -> front)
        cardButton.setIcon(imageManager.getImageIconList()[imageManager.getImageIdxList().get(index)]);

        if (firstFlipped == null) {
            // first trial
            firstFlipped = cardButton;
        } else {
            // second trial
            secondFlipped = cardButton;
            checkMatch();
        }
    }

    private void checkMatch() {
        if (firstFlipped.getIcon().equals(secondFlipped.getIcon())) {
            // success
            matchCount++;
            firstFlipped = null;
            secondFlipped = null;
            if (matchCount == N * N / 2) {
                printSuccessMessage(N + " by " + N + " cleared!");
                restartGame();
            }
        } else {
            // fail
            new TimerThread().start();
        }
    }

    class TimerThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(300);

                // revert previous action
                firstFlipped.setIcon(null);
                secondFlipped.setIcon(null);
                isFlipped[Arrays.asList(cardButtons).indexOf(firstFlipped)] = false;
                isFlipped[Arrays.asList(cardButtons).indexOf(secondFlipped)] = false;
                firstFlipped = null;
                secondFlipped = null;
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    private void restartGame() {
        AudioManager.stopAudio();
        int result = JOptionPane.showConfirmDialog(null, "GO TO NEXT LEVEL",
                "Confirm", JOptionPane.YES_NO_OPTION);
        setVisible(false);
        if (result == JOptionPane.CLOSED_OPTION) {
            new GameFrame(type, N);
        }
        if (result == JOptionPane.YES_OPTION) {
            if (N == 6) {
                new GameFrame(type, N);
            }
            if (N == 2 || N == 4) {
                new GameFrame(type, N + 2);
            }
        }
        if (result == JOptionPane.NO_OPTION) {
            new ImageMatchGame();
        }
    }
}
