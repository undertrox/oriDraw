package de.undertrox.oridraw.ui.action.button;

import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;

public class NewDocAction extends Action {
    @Override
    public void action() {
        MainWindowController.instance.createNewFileTab(null);
        MainWindowController.instance.mainTabPane.getSelectionModel().selectLast();
    }
}
