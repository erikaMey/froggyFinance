package edu.utsa.cs3443.froggyfinance;

import javafx.scene.input.KeyCode;
import javafx.scene.Scene;

/**
 * Handles keyboard input for directional and specific key presses in a JavaFX application.
 * <p>
 * This class listens for key press and release events on a JavaFX {@link Scene},
 * and updates boolean flags accordingly to indicate which keys are currently pressed.
 * <p>
 * Keys monitored: UP, DOWN, LEFT, RIGHT, A, B.
 *
 * @author erikamey
 */
public class KeyHandler {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean aPressed;
    public boolean bPressed;
    public boolean spacePressed;

    /**
     * Registers key press and release listeners to the specified JavaFX
     *
     * @param scene scene to register key event handlers on
     */
    public void register(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP) upPressed = true;
            if (code == KeyCode.DOWN) downPressed = true;
            if (code == KeyCode.LEFT) leftPressed = true;
            if (code == KeyCode.RIGHT) rightPressed = true;
            if (code == KeyCode.A) aPressed = true;
            if (code == KeyCode.B) bPressed = true;
            if(code == KeyCode.SPACE) spacePressed = true;
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP) upPressed = false;
            if (code == KeyCode.DOWN) downPressed = false;
            if (code == KeyCode.LEFT) leftPressed = false;
            if (code == KeyCode.RIGHT) rightPressed = false;
            if (code == KeyCode.A) aPressed = false;
            if (code == KeyCode.B) bPressed = false;
            if(code == KeyCode.SPACE) spacePressed = false;
        });
    }
}
