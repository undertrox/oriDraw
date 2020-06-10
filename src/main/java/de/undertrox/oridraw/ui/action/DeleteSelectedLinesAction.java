package de.undertrox.oridraw.ui.action;


public class DeleteSelectedLinesAction extends CreasePatternAction {

    public DeleteSelectedLinesAction() {
        super(doc -> {
            doc.getCp().removeAllOrilines(doc.getSelection().getSelectedLines());
            doc.getCp().getPoints().removeAll(doc.getSelection().getSelectedPoints());
            doc.getCp().getPoints().removeIf(oriPoint -> oriPoint.getLines().isEmpty());
            doc.getSelection().clear();
        });
    }
}
