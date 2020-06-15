package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.util.io.export.Exporter;

public class ExportDocAction extends Action {

    Exporter<Document> exporter;

    public ExportDocAction(Exporter<Document> exporter) {
        this.exporter = exporter;
    }

    @Override
    public void action() {
        CreasePatternTab tab = MainWindowController.instance.getSelectedCpTab();
        if (tab != null) {
            tab.exportDocument(exporter);
        }
    }
}
