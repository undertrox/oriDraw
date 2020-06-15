package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.util.io.load.Loader;

public class LoadDocAction extends Action {

    Loader<Document> loader;

    public LoadDocAction(Loader<Document> loader) {
        this.loader = loader;
    }

    @Override
    public void action() {
        MainWindowController.instance.openFile(loader);
    }
}
