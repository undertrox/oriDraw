package de.undertrox.oridraw.ui.action;


import de.undertrox.oridraw.origami.Document;

public class DeleteSelectedLinesAction extends DocumentAction {

    @Override
    public void action(Document doc) {
        doc.getCp().removeAllOrilines(doc.getSelection().getSelectedLines());
        doc.getCp().getPoints().removeAll(doc.getSelection().getSelectedPoints());
        doc.getCp().getPoints().removeIf(oriPoint -> oriPoint.getLines().isEmpty());
        doc.getSelection().clear();
    }
}
