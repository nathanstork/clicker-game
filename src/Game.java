import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Game extends JPanel implements MouseListener, ActionListener {
    private final int SQUARE_SIZE = 50;

    private static Settings settings; // instance of the settings
    private Timer timer;
    public Random random;

    private static int delay; // timer delay, implies a game over
    private int squareX;
    private int squareY;
    public boolean running;
    public boolean ready; // allows game to go on stand by after the startButton has been pressed
    public boolean gameOver;

    // Constructor method
    public Game() {
        settings = new Settings();
        random = new Random();

        setDelay(settings.getDifficulty());

        running = false;
        ready = false;
        gameOver = false;
    }

    public static void setDelay(int d) {
        if (d == 0) {
            delay = 2000; // 2 second delay
        } else if (d == 1) {
            delay = 1000; // 1 second delay
        } else if (d == 2) {
            delay = 750; // 0.75 second delay
        } else if (d == 3) {
            delay = 500; // 0.5 second delay
        }
    }

    private void checkHighScore() {
        int score = ClickerGame.getScore();
        int highScore = ClickerGame.getHighScore(settings.getDifficulty());

        if (score > highScore) {
            ClickerGame.setHighScore(score, settings.getDifficulty());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) {
            g.setColor(Color.GREEN);
            squareX = random.nextInt(this.getWidth() - SQUARE_SIZE);
            squareY = random.nextInt(this.getHeight() - SQUARE_SIZE);
            g.fillRect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(Color.WHITE);
            String s = "Game over!";
            int x = (this.getWidth() - g.getFontMetrics().stringWidth(s)) / 2;
            int y = (this.getHeight() - g.getFontMetrics().stringWidth(s)) / 2;
            g.drawString(s, x, y);
            gameOver = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean firstTry = false; // if it's the first try we shouldn't assign a point yet

        if (ready) { // if the game is ready to start and the player clicked this panel
            timer.start();
            repaint();
            running = true;
            ready = false;
            firstTry = true; // if ready is true, then it's the first try
            ClickerGame.setButtonText(2);
            System.out.println("Start!");
        }

        if (running) { // if the game is running
            int x = e.getX();
            int y = e.getY();

            if (x >= squareX && y >= squareY && x <= squareX + SQUARE_SIZE && y <= squareY + SQUARE_SIZE) {
                if (!firstTry) {
                    ClickerGame.setScore(ClickerGame.getScore() + 1);
                }
                timer.restart();
                squareX = 0; // resets the x-coordinate of the square
                squareY = 0; // resets the y-coordinate of the square
                repaint();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean buttonPressed = false; // to imply whether a button has been pressed

        if (e.getActionCommand() != null) { // to prevent NullPointerException when the timer calls this method
            if (e.getActionCommand().equals("Start") || e.getActionCommand().equals("Retry")) {
                timer = new Timer(delay, this); // initializes the timer
                ClickerGame.setScore(0);
                ClickerGame.disableButton(); // disables the startButton
                ClickerGame.setButtonText(1);
                repaint();
                ready = true; // set game on standby and ready to start
                buttonPressed = true; // to imply that the timer has not called the method
                //System.out.println("Ready...");
            } else if (e.getActionCommand().equals("Settings")) {
                if (!settings.isVisible()) {
                    settings.buildGUI();
                }
                buttonPressed = true; // to imply that the timer has not called the method
            }
        }

        if (!buttonPressed) { // When this part is called, it's game over!
            timer.stop();
            checkHighScore();
            running = false;
            gameOver = true;
            repaint();
            ClickerGame.setButtonText(3);
            ClickerGame.enableButton();
            Settings.saveSettings();
            System.out.println("Game over!");
        }
    }

    // Unused MouseListener methods
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}

