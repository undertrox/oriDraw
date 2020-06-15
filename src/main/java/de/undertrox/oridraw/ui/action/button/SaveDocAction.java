package de.undertrox.oridraw.ui.action.button;

import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;

public class SaveDocAction extends Action {

    @Override
    public void action() {
        if (MainWindowController.instance.getSelectedCpTab()!= null) {
            MainWindowController.instance.getSelectedCpTab().saveDocument();
        }
    }
}
