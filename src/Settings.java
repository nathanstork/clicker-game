import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings implements ActionListener {
    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 250;

    private static JFrame settingsFrame;
    private static JPanel settingsPanel;
    private static JLabel difficultyLabel;
    private static JRadioButton radioButton0;
    private static JRadioButton radioButton1;
    private static JRadioButton radioButton2;
    private static JRadioButton radioButton3;
    private static JButton resetHighScore;
    private static JButton okButton;

    private static File saveFile;
    private static Scanner scanner;
    private static PrintWriter writer;

    private ButtonGroup difficultyGroup;

    private static int difficulty;

    // Constructor method
    Settings() {
        settingsFrame = new JFrame("Settings");
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(7, 1));

        difficultyLabel = new JLabel("Difficulty:");
        radioButton0 = new JRadioButton("Easy (2 sec)");
        radioButton0.addActionListener(this);
        radioButton1 = new JRadioButton("Medium (1 sec)");
        radioButton1.addActionListener(this);
        radioButton2 = new JRadioButton("Hard (0.75 sec)");
        radioButton2.addActionListener(this);
        radioButton3 = new JRadioButton("Ultra (0.5 sec)");
        radioButton3.addActionListener(this);

        resetHighScore = new JButton("Reset high score");
        resetHighScore.addActionListener(this);
        okButton = new JButton("OK");
        okButton.addActionListener(this);

        saveFile = new File("src/save.txt");
        //System.out.println(new File(".").getAbsoluteFile()); // handy for debugging

        difficultyGroup = new ButtonGroup();

        difficultyGroup.add(radioButton0);
        difficultyGroup.add(radioButton1);
        difficultyGroup.add(radioButton2);
        difficultyGroup.add(radioButton3);

        getSettings();
    }

    private void getSettings() {
        try {
            scanner = new Scanner(saveFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) { // ignore lines with // in front of it
                    if (line.contains("highscore_0 = ")) {
                        String value = line.substring("highscore_0 = ".length());
                        ClickerGame.highScore0 = Integer.parseInt(value);
                    } else if (line.contains("highscore_1 = ")) {
                        String value = line.substring("highscore_1 = ".length());
                        ClickerGame.highScore1 = Integer.parseInt(value);
                    } else if (line.contains("highscore_2 = ")) {
                        String value = line.substring("highscore_2 = ".length());
                        ClickerGame.highScore2 = Integer.parseInt(value);
                    } else if (line.contains("highscore_3 = ")) {
                        String value = line.substring("highscore_3 = ".length());
                        ClickerGame.highScore3 = Integer.parseInt(value);
                    } else if (line.contains("difficulty = ")) {
                        String value = line.substring("difficulty = ".length());
                        difficulty = Integer.parseInt(value);
                        initHighScore(difficulty);
                    } else {
                        System.out.println("Error in reading file.\nFile layout might be incorrect.");
                    }
                    System.out.println(line);
                }
            }

            scanner.close(); // Close the scanner so that other processes can also access the save file
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nCould not find save file.");
            System.out.println("Download the save.txt file again from GitHub.");
        }
    }

    private void initHighScore(int d) {
        ClickerGame.highScore = ClickerGame.getHighScore(d);
    }

    // Writes the current settings back to the save.txt file
    public static void saveSettings() {
        try {
            scanner = new Scanner(saveFile);
            ArrayList<String> lines = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) {
                    if (line.contains("highscore_0 = ")) {
                        line = "highscore_0 = " + ClickerGame.getHighScore(0);
                    } else if (line.contains("highscore_1 = ")) {
                        line = "highscore_1 = " + ClickerGame.getHighScore(1);
                    } else if (line.contains("highscore_2 = ")) {
                        line = "highscore_2 = " + ClickerGame.getHighScore(2);
                    } else if (line.contains("highscore_3 = ")) {
                        line = "highscore_3 = " + ClickerGame.getHighScore(3);
                    } else if (line.contains("difficulty = ")) {
                        line = "difficulty = " + difficulty;
                    }
                }
                lines.add(line);
            }

            writer = new PrintWriter(saveFile);
            for (String line : lines) {
                writer.println(line);
            }

            writer.close(); // Close the PrintWriter, so that other processes can also access the save file
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nCould not find save file.");
        }
    }

    private static void setDifficulty(int d) {
        difficulty = d;
        Game.setDelay(d);
        ClickerGame.highScore = ClickerGame.getHighScore(d);
        ClickerGame.setHighScore(ClickerGame.getHighScore(d), d);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isVisible() { // returns true if the settings window is visible
        return settingsFrame.isVisible();
    }

    public void buildGUI() {
        settingsPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        settingsPanel.add(difficultyLabel);
        settingsPanel.add(radioButton0);
        settingsPanel.add(radioButton1);
        settingsPanel.add(radioButton2);
        settingsPanel.add(radioButton3);
        settingsPanel.add(resetHighScore);
        settingsPanel.add(okButton);

        settingsFrame.add(settingsPanel);

        // This if-statement selects the corresponding difficulty option
        if (difficulty == 0) {
            radioButton0.setSelected(true);
        } else if (difficulty == 1) {
            radioButton1.setSelected(true);
        } else if (difficulty == 2) {
            radioButton2.setSelected(true);
        } else if (difficulty == 3) {
            radioButton3.setSelected(true);
        }

        settingsFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setResizable(false);
        settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        settingsFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JRadioButton radioSource = (JRadioButton) e.getSource();
            switch (radioSource.getText()) {
                case "Easy (2 sec)":
                    setDifficulty(0);
                    break;
                case "Medium (1 sec)":
                    setDifficulty(1);
                    break;
                case "Hard (0.75 sec)":
                    setDifficulty(2);
                    break;
                case "Ultra (0.5 sec)":
                    setDifficulty(3);
                    break;
            }
        } catch (ClassCastException cce) { // if the source can't become a JRadioButton
            JButton buttonSource = (JButton) e.getSource();
            switch (buttonSource.getText()) {
                case "Reset high score":
                    ClickerGame.setHighScore(0, difficulty);
                    break;
                case "OK":
                    saveSettings();
                    settingsFrame.dispose();
                    break;
            }
        }
    }
}
