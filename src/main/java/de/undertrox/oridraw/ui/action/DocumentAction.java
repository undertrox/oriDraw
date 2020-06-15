package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;

public abstract class DocumentAction extends Action {

    public abstract void action(Document doc);

    public void action() {
        CreasePatternTab tab = MainWindowController.instance.getSelectedCpTab();
        if (tab != null) {
            action(tab.getDoc());
        }
    }
}
