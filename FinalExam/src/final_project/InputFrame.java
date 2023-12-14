package final_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static final_project.MessageManager.*;

public class InputFrame extends JFrame {
    private Container inputContainer;
    private ButtonGroup ThemeRadioButtonGroup;
    private JRadioButton iuThemeRadioButton;
    private JRadioButton animalThemeRadioButton;
    private JTextField textField;
    private static final int MAX_N = 6;
    private int N; // size of n by n img board

    public InputFrame() {
        super("InputFrame");
        inputContainer = getContentPane();
        inputContainer.setLayout(new BorderLayout(3, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create components
        createTitleLabel();
        createThemeButton();
        createStartButton();

        setSize(800, 400);
        setVisible(true);
    }

    private void createTitleLabel() {
        JLabel title = new JLabel("ImageMatchGame");
        title.setFont(new Font("ImageMatchGame", Font.ITALIC | Font.BOLD, 20));
        inputContainer.add(title, BorderLayout.NORTH);
    }

    private void createStartButton() {
        // make text field and start button
        textField = new JTextField("enter under 7 even number\nex) 2, 4, 6", 20);
        textField.setFont(new Font("textField", Font.ITALIC | Font.BOLD, 10));
        textField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String string = JOptionPane.showInputDialog("enter under 7 even number\nex) 2, 4, 6");
                textField.setText(string);
            }
        });
        JButton startButton = new JButton("READY");
        startButton.setFont(new Font("textField", Font.ITALIC | Font.BOLD, 20));

        // add listeners
        startButton.addMouseListener(new MyMouseListener());
        startButton.addActionListener(new MyActionListener());

        // attach to container
        inputContainer.add(textField, BorderLayout.EAST);
        inputContainer.add(startButton, BorderLayout.SOUTH);
    }

    private void createThemeButton() {
        // make radio buttons
        ThemeRadioButtonGroup = new ButtonGroup();

        ImageIcon iuIcon = new ImageIcon("src/final_project/images/dlwlrma1.jpg");
        iuThemeRadioButton = new JRadioButton("iu theme", iuIcon, true);
        iuThemeRadioButton.setBorderPainted(true);
        ThemeRadioButtonGroup.add(iuThemeRadioButton);

        ImageIcon swIcon = new ImageIcon("src/final_project/images/animal1.jpg");
        animalThemeRadioButton = new JRadioButton("animal theme", swIcon);
        animalThemeRadioButton.setBorderPainted(true);
        ThemeRadioButtonGroup.add(animalThemeRadioButton);

        // attach to container
        inputContainer.add(iuThemeRadioButton, BorderLayout.WEST);
        inputContainer.add(animalThemeRadioButton, BorderLayout.CENTER);
    }

    class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton startButton = (JButton) e.getSource();
            startButton.setBackground(Color.green);
            startButton.setText("GO");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton startButton = (JButton) e.getSource();
            startButton.setBackground(UIManager.getColor("Button.background"));
            startButton.setText("READY");
        }
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                N = Integer.parseInt(textField.getText());
            } catch (RuntimeException err) {
                // fail
                printFailedMessage("enter under 7 even number\nex) 2, 4, 6");
                return;
            }
            if (N % 2 == 1 || N > MAX_N) {
                // fail
                printFailedMessage("enter under 7 even number\nex) 2, 4, 6");
            } else {
                // success
                if (ThemeRadioButtonGroup.isSelected(iuThemeRadioButton.getModel())) {
                    // iu case
                    printSuccessMessage(N + " by " + N + " iu theme game start");
                    setVisible(false);
                    new GameFrame("iu", N);
                } else {
                    // animal case
                    printSuccessMessage(N + " by " + N + " animal theme game start");
                    setVisible(false);
                    new GameFrame("animal", N);
                }
            }
        }
    }
}
