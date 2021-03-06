package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.math.Transform;

public class DocumentRenderer extends Renderer {
    private CreaseCollectionLineRenderer creases;
    private CreaseCollectionLineRenderer grid;
    private CreaseCollectionPointRenderer points;
    private CreasePatternSelectionPointRenderer pointSelection;
    private CreasePatternSelectionLineRenderer lineSelection;
    private Document doc;

    public DocumentRenderer(Transform t, Document doc) {
        super(t);
        this.doc = doc;
        creases = new CreaseCollectionLineRenderer(t, doc.getCp());
        grid = new CreaseCollectionLineRenderer(t, doc.getGrid());
        points = new CreaseCollectionPointRenderer(t, doc.getCp());
        pointSelection = new CreasePatternSelectionPointRenderer(t, doc.getSelection());
        lineSelection = new CreasePatternSelectionLineRenderer(t, doc.getSelection());
    }

    @Override
    protected void draw() {
        if (doc.showGrid()) {
            grid.render(getCanvas());
        }
        creases.render(getCanvas());
        points.render(getCanvas());
        lineSelection.render(getCanvas());
        pointSelection.render(getCanvas());
    }
}
