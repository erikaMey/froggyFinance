package edu.utsa.cs3443.froggyfinance;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
/**
 *Represents the Lady bug Level scene in the Froggy Finance game.
 *  <p>
 * This level allows the player to move a frog character across the screen using
 * keyboard input. It displays questions when the player reaches specific locations
 * and tracks the player's score.
 * </p>
 *
 * @author erikamey
 */
public class GrasshopperLevel {
    private static final int WIDTH = 768;
    private static final int HEIGHT = 576;
    private static final int PLAYER_SIZE = 40;
    private static double playerX = 100;
    private static double playerY = 100;
    private static Image grasshopperBg;
    private static Image player;
    private static AnimationTimer timer;
    /**
     * Creates the lady Bug Level scene.
     *
     * @param stage The main game stage for scene switching
     * @param fromNextLevel If true, player enters from previous level; sets spawn location
     * @param gameState The current game state tracking score and progress
     * @return fully initialized JavaFX Scene for the Caterpillar level
     */
    public static Scene createGrasshopperScene(Stage stage, boolean fromNextLevel, GameState gameState) {

        URL bgUrl = GrasshopperLevel.class.getResource("/edu/utsa/cs3443/froggyfinance/bug1.png");
        grasshopperBg = null;
        if (bgUrl != null) {
            grasshopperBg = new Image(bgUrl.toExternalForm());
        }else{
            System.out.println(" ERROR: bug1.png not found!");
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
        Scene grasshoperScene = new Scene(root);

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.register(grasshoperScene);

        ScoreBox scoreBox = new ScoreBox();
        scoreBox.showScore(gameState.getCorrectAnswers(), gameState.getWrongAnswers());

        DialogBox dialogBox = new DialogBox();
        DialogManager dialogManager = new DialogManager();
        dialogManager.loadDialogsForLevel("/edu/utsa/cs3443/froggyfinance/Dialog.txt", 2);

        QuestionManager questionManager = new QuestionManager("level1.txt", 2, keyHandler, scoreBox, gameState, dialogBox, dialogManager);
        root.getChildren().add(questionManager.getQuestionBox());
        StackPane.setAlignment(questionManager.getQuestionBox(), Pos.BOTTOM_CENTER);
        StackPane.setMargin(questionManager.getQuestionBox(), new Insets(20, 0, 20, 0));
        root.getChildren().add(scoreBox);
        StackPane.setAlignment(scoreBox, Pos.TOP_LEFT);
        StackPane.setMargin(scoreBox, new Insets(20));

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
                questionManager.showIfInCenter(playerX, playerY, WIDTH, HEIGHT);
                questionManager.update();
            }
        };
        timer.start();

        return grasshoperScene;
    }
    /**
     * Updates the player's position based on keyboard input
     *
     * @param keyHandler The input handler with key press states
     */
    private static void update(KeyHandler keyHandler) {
        if (keyHandler.upPressed) playerY -= 4;
        if (keyHandler.downPressed) playerY += 4;
        if (keyHandler.leftPressed) playerX -= 4;
        if (keyHandler.rightPressed) playerX += 4;

    }
    /**
     * Renders the background and player, clamps player to screen bounds,
     * and checks for level transition triggers.
     *
     * @param gc used for drawing
     * @param stage The primary stage for switching scenes
     * @param fromNextLevel Whether the scene was entered from a next-level transition
     */
    private static void render(GraphicsContext gc, Stage stage, boolean fromNextLevel) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        if (grasshopperBg != null) {
            gc.drawImage(grasshopperBg, 0, 0, WIDTH, HEIGHT);
        }

        if (player != null) {
            gc.drawImage(player, playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        }

        playerX = Math.max(0, Math.min(WIDTH - PLAYER_SIZE, playerX));
        playerY = Math.max(0, Math.min(HEIGHT - PLAYER_SIZE, playerY));

        if (playerX > WIDTH - PLAYER_SIZE - 20 && playerY > HEIGHT - PLAYER_SIZE - 20) {
            System.out.println(" Exiting grasshopper Level...");
            timer.stop();
            LevelManager.nextLevel(stage);
        }
        if (playerX < 20 && playerY < 20) {
            System.out.println("Exiting grasshopper Level...");
            timer.stop();
            LevelManager.previousLevel(stage);
        }
    }
}
