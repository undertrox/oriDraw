package de.undertrox.oridraw.ui.handler;

import javafx.scene.input.KeyEvent;

public interface KeyboardHandlerInterface {
    void onKeyPressed(KeyEvent e);

    void onKeyDown(KeyEvent e);

    void onKeyUp(KeyEvent e);
}
