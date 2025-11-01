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
 *Represents the Firefly Level scene in the Froggy Finance game.
 *  <p>
 * This level allows the player to move a frog character across the screen using
 * keyboard input. It displays questions when the player reaches specific locations
 * and tracks the player's score.
 * </p>
 *
 * @author erikamey
 */
public class FireflyLevel {
    private static final int WIDTH = 768;
    private static final int HEIGHT = 576;
    private static final int PLAYER_SIZE = 40;
    private static double playerX = 100;
    private static double playerY = 100;
    private static Image fireflyBg;
    private static Image player;
    private static AnimationTimer timer;
    /**
     * Creates the Firefly Level scene.
     *
     * @param stage The main game stage for scene switching
     * @param fromNextLevel If true, player enters from previous level; sets spawn location
     * @param gameState The current game state tracking score and progress
     * @return fully initialized JavaFX Scene for the Caterpillar level
     */
    public static Scene createFireflyScene(Stage stage, boolean fromNextLevel, GameState gameState) {

        URL bgUrl = FireflyLevel.class.getResource("/edu/utsa/cs3443/froggyfinance/fireflyLevel.png");
        fireflyBg = null;
        if (bgUrl != null) {
            fireflyBg = new Image(bgUrl.toExternalForm());
        }else{
            System.out.println(" ERROR: fireflyLevel.png not found!");
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
        Scene fireflyScene = new Scene(root);

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.register(fireflyScene);

        ScoreBox scoreBox = new ScoreBox();
        scoreBox.showScore(gameState.getCorrectAnswers(), gameState.getWrongAnswers());

        DialogBox dialogBox = new DialogBox();
        DialogManager dialogManager = new DialogManager();
        dialogManager.loadDialogsForLevel("/edu/utsa/cs3443/froggyfinance/Dialog.txt", 4);

        QuestionManager questionManager = new QuestionManager("level1.txt", 4, keyHandler, scoreBox, gameState, dialogBox, dialogManager);
        root.getChildren().add(questionManager.getQuestionBox());
        StackPane.setAlignment(questionManager.getQuestionBox(), Pos.BOTTOM_CENTER);
        StackPane.setMargin(questionManager.getQuestionBox(), new Insets(20, 0, 20, 0));
        root.getChildren().add(scoreBox);
        StackPane.setAlignment(scoreBox, Pos.TOP_LEFT);
        StackPane.setMargin(scoreBox, new Insets(20));
        root.getChildren().add(dialogBox);
        StackPane.setAlignment(dialogBox, Pos.TOP_RIGHT);
        dialogBox.setTranslateX(210);
        dialogBox.setTranslateY(10);
        StackPane.setMargin(dialogBox, new Insets(20, 250, 200, 150));
        dialogBox.maxWidthProperty().bind(root.widthProperty().multiply(0.9));


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

        return fireflyScene;
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

        if (fireflyBg != null) {
            gc.drawImage(fireflyBg, 0, 0, WIDTH, HEIGHT);
        }

        if (player != null) {
            gc.drawImage(player, playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        }

        playerX = Math.max(0, Math.min(WIDTH - PLAYER_SIZE, playerX));
        playerY = Math.max(0, Math.min(HEIGHT - PLAYER_SIZE, playerY));

        if (playerX > WIDTH - PLAYER_SIZE - 20 && playerY > HEIGHT - PLAYER_SIZE - 20) {
            System.out.println(" Exiting firefly Level...");
            timer.stop();
            LevelManager.nextLevel(stage);
        }
        if (playerX < 20 && playerY < 20) {
            System.out.println("Exiting firefly Level...");
            timer.stop();
            LevelManager.previousLevel(stage);
        }

    }

}


