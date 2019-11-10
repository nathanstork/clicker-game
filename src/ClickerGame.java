import javax.swing.*;
import java.awt.*;

public class ClickerGame {
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 550;
    public final int BUTTON_WIDTH = 100;
    public final int BUTTON_HEIGHT = 50; // height of used buttons and also height of panels at bottom of the screen

    private static Game game;

    private JFrame frame;
    private JPanel controlPanel;
    private JPanel textPanel;
    private JLabel scoreTextLabel;
    private JLabel highScoreTextLabel;
    private JButton settingsButton;
    private JPanel startStopPanel;
    private JPanel settingsPanel;
    private static JLabel scoreLabel;
    private static JLabel highScoreLabel;
    private static JButton startButton;

    private static int score;
    public static int highScore;
    public static int highScore0;
    public static int highScore1;
    public static int highScore2;
    public static int highScore3;

    // Constructor method
    public ClickerGame() {
        game = new Game(); // initializes the game
        frame = new JFrame("Clicker Game by Nathan Stork");

        controlPanel = new JPanel();
        textPanel = new JPanel();

        score = 0;

        scoreTextLabel = new JLabel("Score: ");
        scoreLabel = new JLabel(String.valueOf(score));
        highScoreTextLabel = new JLabel("High score: ");
        highScoreLabel = new JLabel(String.valueOf(highScore));

        startButton = new JButton("Start");
        startButton.addActionListener(game);
        startStopPanel = new JPanel();
        startStopPanel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(game);
        settingsPanel = new JPanel();
        settingsPanel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    }

    void buildGUI() {
        // Game's properties
        game.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_WIDTH));
        game.setBackground(new Color(50, 50, 50));
        game.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        game.addMouseListener(game);

        // TextPanel's properties
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(BUTTON_HEIGHT, 20, BUTTON_HEIGHT, 20);
        textPanel.setPreferredSize(new Dimension(SCREEN_WIDTH - 2 * BUTTON_WIDTH, BUTTON_HEIGHT));
        textPanel.setBackground(Color.GRAY);
        textPanel.add(scoreTextLabel, constraints);
        textPanel.add(scoreLabel, constraints);
        textPanel.add(highScoreTextLabel, constraints);
        textPanel.add(highScoreLabel, constraints);

        // Button panel's properties
        startStopPanel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startStopPanel.setLayout(new GridLayout());
        startStopPanel.add(startButton);

        settingsPanel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        settingsPanel.setLayout(new GridLayout());
        settingsPanel.add(settingsButton);

        // ControlPanel's properties
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, BUTTON_HEIGHT));
        controlPanel.add(startStopPanel, BorderLayout.WEST);
        controlPanel.add(textPanel, BorderLayout.CENTER);
        controlPanel.add(settingsPanel, BorderLayout.EAST);

        // Frame's properties
        frame.add(game);
        frame.add(controlPanel);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void setScore(int s) {
        score = s;
        scoreLabel.setText(String.valueOf(score));
    }

    public static int getScore() {
        return score;
    }

    public static void setHighScore(int s, int d) {
        if (d == 0) {
            highScore0 = s;
            highScore = highScore0;
        } else if (d == 1) {
            highScore1 = s;
            highScore = highScore1;
        } else if (d == 2) {
            highScore2 = s;
            highScore = highScore2;
        } else if (d == 3) {
            highScore3 = s;
            highScore = highScore3;
        }

        highScoreLabel.setText(String.valueOf(highScore));
    }

    public static int getHighScore(int d) {
        if (d == 0) {
            return highScore0;
        } else if (d == 1) {
            return highScore1;
        } else if (d == 2) {
            return highScore2;
        } else if (d == 3) {
            return highScore3;
        } else {
            return -1; // error value
        }
    }

    public static void enableButton() {
        startButton.setEnabled(true);
    }

    public static void disableButton() {
        startButton.setEnabled(false);
    }

    public static void setButtonText(int state) { // switches the startButton's text
        switch (state) {
            case 0:
                startButton.setText("Start");
                break;
            case 1:
                startButton.setText("Ready...");
                break;
            case 2:
                startButton.setText("");
                break;
            case 3:
                startButton.setText("Retry");
                break;
        }
    }

    public static void main(String[] args) {
        ClickerGame clicker = new ClickerGame();
        clicker.buildGUI();
    }
}
