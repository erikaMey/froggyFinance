package edu.utsa.cs3443.froggyfinance;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.net.URL;

/**
 * Represents the main game panel for the Froggy Finance game.
 * <p>
 * This class sets up and manages the game's scene, rendering loop, player movement,
 * background drawing, and level transition based on player position.
 *
 * @author erikamey
 */
public class GamePanel {
    private static Image playerImage;
    private static final int TILE_SIZE = 48;
    private static final int SCREEN_COLS = 16;
    private static final int SCREEN_ROWS = 12;
    private static final int WIDTH = TILE_SIZE * SCREEN_COLS;
    private static final int HEIGHT = TILE_SIZE * SCREEN_ROWS;
    private static final int PLAYER_SIZE = 40;
    private static final double SPEED = 4;
    private static double playerX = 100;
    private static double playerY = 100;
    private static Stage primaryStage;
    private static KeyHandler keyHandler;
    private static AnimationTimer timer;


    /**
     * Creates and returns the main game, including background rendering,
     * player sprite setup, input handling, and animation timer.
     *
     * @param stage the primary stage
     * @param fromNextLevel flag indicating whether the scene is loading from another level
     * @return the game scene
     */
    public static Scene createGameScene(Stage stage, boolean fromNextLevel) {
        BackgroundMusicPlayer.playLoop("background1.wav");
        primaryStage = stage;
        Image bgImage = new Image(GamePanel.class.getResource("/edu/utsa/cs3443/froggyfinance/tryMap.png").toExternalForm());
        Canvas bgCanvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext bgGc = bgCanvas.getGraphicsContext2D();
        bgGc.drawImage(bgImage, 0, 0, WIDTH, HEIGHT);

        Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().addAll(bgCanvas, gameCanvas);

        Scene scene = new Scene(root);
        ScoreBox scoreBox = new ScoreBox();

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.register(scene);

        URL frogUrl = GamePanel.class.getResource("/edu/utsa/cs3443/froggyfinance/mainfrogwalking.png");
        if (frogUrl != null) {
            playerImage = new Image(frogUrl.toExternalForm());
        } else {
           System.out.println(" mainfrogwalking.png not found!");
        }
        if (fromNextLevel) {
            playerX = 100;
            playerY = 100;
        } else {
            playerX = WIDTH - 100;
            playerY = HEIGHT - 100;
        }

        final boolean directionFlag = fromNextLevel;
       timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(keyHandler);
                render(gc);
            }
        };
       timer.start();

        return scene;
    }

    /**
     * Updates the game state based on user input
     *
     * @param keyHandler he key input handler tracking pressed keys
     */
    private static void update(KeyHandler keyHandler) {
        if (keyHandler.upPressed) playerY -= 4;
        if (keyHandler.downPressed) playerY += 4;
        if (keyHandler.leftPressed) playerX -= 4;
        if (keyHandler.rightPressed) playerX += 4;


        if (playerX > WIDTH - 50 && playerY > HEIGHT - 50) {

            System.out.print("Toad Level");
            timer.stop();
            LevelManager.nextLevel(primaryStage);
        }


        playerX = clamp(playerX, 0, WIDTH - PLAYER_SIZE);
        playerY = clamp(playerY, 0, HEIGHT - PLAYER_SIZE);
    }

    /**
     * Renders the current game state, including the player.
     *
     * @param gc the graphics context to draw on
     */
    private static void render(GraphicsContext gc) {

        gc.clearRect(0, 0, WIDTH, HEIGHT);


        if (playerImage != null) {
            gc.drawImage(playerImage, playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        } else {
            gc.setFill(Color.HOTPINK);
            gc.fillRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        }
    }

    /**
     * Ensures a value stays within a specified range.
     *
     * @param value the current value
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return the clamped value
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

}

