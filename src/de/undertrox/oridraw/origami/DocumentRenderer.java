package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.CreaseCollectionLineRenderer;
import de.undertrox.oridraw.ui.render.renderer.CreaseCollectionPointRenderer;
import de.undertrox.oridraw.ui.render.renderer.CreasePatternSelectionPointRenderer;
import de.undertrox.oridraw.ui.render.renderer.Renderer;

import javax.print.Doc;

public class DocumentRenderer extends Renderer {
    private CreaseCollectionLineRenderer creases;
    private CreaseCollectionLineRenderer grid;
    private CreaseCollectionPointRenderer points;
    private CreasePatternSelectionPointRenderer pointSelection;
    private Document doc;

    public DocumentRenderer(Transform t, Document doc) {
        super(t);
        this.doc = doc;
        creases = new CreaseCollectionLineRenderer(t, doc.getCp());
        grid = new CreaseCollectionLineRenderer(t, doc.getGrid());
        points = new CreaseCollectionPointRenderer(t, doc.getCp());
        pointSelection = new CreasePatternSelectionPointRenderer(t, doc.getSelection());
    }

    @Override
    protected void draw() {
        grid.render(getCanvas());
        creases.render(getCanvas());
        points.render(getCanvas());
        pointSelection.render(getCanvas());
    }
}
