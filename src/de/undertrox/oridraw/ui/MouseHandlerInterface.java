package de.undertrox.oridraw.ui;

import javafx.scene.input.MouseEvent;

public interface MouseHandlerInterface {
    void onClick(MouseEvent e);

    void onDrag(MouseEvent e);

    void onMove(MouseEvent e);

}
