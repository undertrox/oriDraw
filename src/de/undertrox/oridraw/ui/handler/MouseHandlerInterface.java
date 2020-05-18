package de.undertrox.oridraw.ui.handler;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface MouseHandlerInterface {
    void onClick(MouseEvent e);

    void onDrag(MouseEvent e);

    void onMove(MouseEvent e);

    default void onScroll(ScrollEvent e) {

    }
}
