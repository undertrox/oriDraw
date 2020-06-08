package de.undertrox.oridraw.ui.action;

public class DeleteSelectedLinesAction extends CreasePatternAction {

    public DeleteSelectedLinesAction() {
        super(doc -> {
            doc.getCp().getOriLines().removeAll(doc.getSelection().getSelectedLines());
            doc.getCp().getPoints().removeAll(doc.getSelection().getSelectedPoints());
            doc.getSelection().clear();
        });
    }
}
