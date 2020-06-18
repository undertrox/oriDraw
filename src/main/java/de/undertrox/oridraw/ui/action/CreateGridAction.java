package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.Document;

public class CreateGridAction extends DocumentAction {
    @Override
    public void action(Document doc) {
        doc.getCp().addOriLines(doc.getGrid().getOriLines());
    }
}
