package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;

import java.util.function.Consumer;

public class CreasePatternAction extends Action {
    Consumer<Document> consumer;

    public CreasePatternAction(Consumer<Document> consumer) {
        super(() -> {
            CreasePatternTab tab = MainWindowController.instance.getSelectedCpTab();
            if (tab != null) {
                consumer.accept(tab.getDoc());
            }
        });
    }
}
