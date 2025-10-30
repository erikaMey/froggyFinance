package edu.utsa.cs3443.froggyfinance;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Handles the end-of-level scene in the Froggy Finance game.
 * <p>
 * This class displays an end-level screen where the player can move a character.
 * Reaching the top-left or bottom-right corner of the screen triggers a transition
 * to either the previous or next level.
 *
 * @author erikamey
 */
public class EndLevel {
    private static final int WIDTH = 768;
    private static final int HEIGHT = 576;
    private static final int PLAYER_SIZE = 40;
    private static double playerX = 100;
    private static double playerY = 100;
    private static Image endBg;
    private static Image player;
    private static AnimationTimer timer;

    /**
     * Creates and returns the end-level
     *
     * @param stage the primary stage used to switch scenes
     * @param fromNextLevel indicates whether the scene was entered from a forward transition
     * @return the end-level scene
     */
    public static Scene createEndScene(Stage stage, boolean fromNextLevel) {

        URL bgUrl = EndLevel.class.getResource("/edu/utsa/cs3443/froggyfinance/EndLevel.png");
        endBg = null;
        if (bgUrl != null) {
            endBg = new Image(bgUrl.toExternalForm());
        }else{
            System.out.println(" ERROR: endLevel.png not found!");
        }

        URL frogUrl = ToadLevel.class.getResource("/edu/utsa/cs3443/froggyfinance/mainfrogwalking.png");
        if (frogUrl != null) {
            player = new Image(frogUrl.toExternalForm());
        }else {
            System.out.println(" ERROR: mainfrogwalking.png not found!");
        }

        Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        StackPane root = new StackPane(gameCanvas);
        Scene endScene = new Scene(root);

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.register(endScene);

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
                render(gc, stage, directionFlag);
            }
        };
        timer.start();

        return endScene;
    }

    /**
     * Updates the player's position based on currently pressed keys.
     *
     * @param keyHandler thi input handler
     */
    private static void update(KeyHandler keyHandler) {
        if (keyHandler.upPressed) playerY -= 4;
        if (keyHandler.downPressed) playerY += 4;
        if (keyHandler.leftPressed) playerX -= 4;
        if (keyHandler.rightPressed) playerX += 4;
    }

    /**
     * Renders the current game state, including background and player.
     * Also checks for screen edge collisions to trigger scene transitions.
     *
     * @param gc the graphics context for drawing
     * @param stage the primary stage for scene transitions
     * @param fromNextLevel whether the scene was entered from the next level
     */
    private static void render(GraphicsContext gc, Stage stage, boolean fromNextLevel) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        if (endBg != null) {
            gc.drawImage(endBg, 0, 0, WIDTH, HEIGHT);
        }
        if (player != null) {
            gc.drawImage(player, playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        }
        playerX = Math.max(0, Math.min(WIDTH - PLAYER_SIZE, playerX));
        playerY = Math.max(0, Math.min(HEIGHT - PLAYER_SIZE, playerY));

        if (playerX > WIDTH - PLAYER_SIZE - 20 && playerY > HEIGHT - PLAYER_SIZE - 20) {
            System.out.println(" Exiting end Level...");
            timer.stop();
            LevelManager.nextLevel(stage);
        }
        if (playerX < 20 && playerY < 20) {
            System.out.println("Exiting end Level...");
            timer.stop();
            LevelManager.previousLevel(stage);
        }
    }
}
