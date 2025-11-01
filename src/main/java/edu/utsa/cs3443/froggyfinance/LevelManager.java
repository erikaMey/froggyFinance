package edu.utsa.cs3443.froggyfinance;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The LevelManager class manages the level flow for the game.
 * It tracks the current level, handles transitions between levels,
 * and resets the game to its starting point.
 *
 * <p>This class works with JavaFX Scene and Stage to switch
 * between different game scenes representing each level.</p>
 *
 * @author erikamey
 */
public class LevelManager {
    private static int currentLevel = 0;
    private static final int TOTAL_LEVELS = 8;
    private static final GameState gameState = new GameState();

    /**
     * Scene} for the given level index.
     *
     * @param level the index of the level (0-based)
     * @param stage The JavaFX stage to render the scene on
     * @param fromNextLevel True if the player is coming from a previous level; false if going back.
     * @return The JavaFX Scene for the specified level.
     */
    public static Scene getLevelScene(int level, Stage stage, boolean fromNextLevel) {
        switch (level) {
            case 0: return GamePanel.createGameScene(stage, fromNextLevel);
            case 1: return ToadLevel.createToadScene(stage, fromNextLevel, gameState);
            case 2: return GrasshopperLevel.createGrasshopperScene(stage, fromNextLevel, gameState);
            case 3: return CaterpillarLevel.createCaterpillarScene(stage, fromNextLevel, gameState);
            case 4: return FireflyLevel.createFireflyScene(stage, fromNextLevel, gameState);
            case 5: return DragonflyLevel.createDragonflyScene(stage, fromNextLevel, gameState);
            case 6: return PrayingMantisLevel.createPrayingMantisScene(stage, fromNextLevel, gameState);
            case 7: return EndLevel.createEndScene(stage, fromNextLevel);
            default: return GamePanel.createGameScene(stage, true);
        }
    }

    /**
     * Advances the game to the next level, if one exists
     *
     * @param stage stage The JavaFX stage to update with the new scene
     */
    public static void nextLevel(Stage stage) {
        if (currentLevel < TOTAL_LEVELS - 1) {
            currentLevel++;
            stage.setScene(getLevelScene(currentLevel, stage, true));
        }
    }

    /**
     * Moves the game back to the previous level, if not at the first level.
     *
     * @param stage stage The JavaFX stage to update with the new scene
     */
    public static void previousLevel(Stage stage) {
        if (currentLevel > 0) {
            currentLevel--;
            stage.setScene(getLevelScene(currentLevel, stage, false));
        }
    }

    /**
     * Resets the game to the first level and restarts the scene
     *
     * @param stage stage The JavaFX stage to resetOpenDialogs
     */
    public static void reset(Stage stage) {
        currentLevel = 0;
        stage.setScene(getLevelScene(currentLevel, stage, true));
    }
}

