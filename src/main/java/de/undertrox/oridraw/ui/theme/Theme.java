package de.undertrox.oridraw.ui.theme;

import de.undertrox.oridraw.util.registry.Registrable;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Theme extends Registrable {
    private LineStyle mountain;
    private LineStyle valley;
    private LineStyle edge;
    private LineStyle aux;
    private LineStyle unknown;
    private LineStyle selectionOutline;

    private LineStyle selectedLine;
    private LineStyle toBeSelectedLine;
    private PointStyle defaultPoint;
    private PointStyle selectedPoint;
    private PointStyle toBeSelectedPoint;

    private Paint background;

    public Paint getBackground() {
        return background;
    }

    public LineStyle getSelectionOutline() {
        return selectionOutline;
    }

    public static final Theme DEFAULT = new Theme(
            new LineStyle(Color.RED, 1),
            new LineStyle(Color.BLUE, 1),
            new LineStyle(Color.BLACK, 1),
            new LineStyle(Color.LIGHTGRAY, 1),
            new LineStyle(Color.YELLOW, 1),

            new LineStyle(Color.LIGHTGREEN, 1),
            new LineStyle(Color.LIMEGREEN, 1),
            new LineStyle(Color.GRAY, 1, 5, 5),

            new PointStyle(Color.BLACK, 2, PointStyle.Shape.SQUARE),
            new PointStyle(Color.LIGHTGREEN, 2, PointStyle.Shape.SQUARE),
            new PointStyle(Color.LIMEGREEN, 2, PointStyle.Shape.SQUARE),
            Color.WHITE
    );

    public static final Theme DARK = new Theme(
            new LineStyle(Color.color(1, 0.4, 0.3), 1, 15, 4, 2, 4, 2, 4),
            new LineStyle(Color.color(0.3, 0.5, 1), 1, 15, 5),
            new LineStyle(Color.WHITE, 1),
            new LineStyle(Color.gray(0.3), 1),
            new LineStyle(Color.YELLOW, 1),

            new LineStyle(Color.LIGHTGREEN, 1),
            new LineStyle(Color.LIMEGREEN, 1),

            new LineStyle(Color.GRAY, 1, 5, 5),

            new PointStyle(Color.WHITE, 2, PointStyle.Shape.SQUARE),
            new PointStyle(Color.LIGHTGREEN, 2, PointStyle.Shape.SQUARE),
            new PointStyle(Color.LIMEGREEN, 2, PointStyle.Shape.SQUARE),
            Color.gray(0.15)
    );

    public Theme(LineStyle mountain, LineStyle valley, LineStyle edge, LineStyle aux, LineStyle unknown,
                 LineStyle toBeSelectedLine, LineStyle selectedLine, LineStyle selectionOutline,
                 PointStyle defaultPoint, PointStyle toBeSelectedPoint, PointStyle selectedPoint,
                 Paint background) {
        this.mountain = mountain;
        this.valley = valley;
        this.edge = edge;
        this.aux = aux;
        this.unknown = unknown;
        this.selectedLine = selectedLine;
        this.toBeSelectedLine = toBeSelectedLine;
        this.selectionOutline = selectionOutline;
        this.defaultPoint = defaultPoint;
        this.selectedPoint = selectedPoint;
        this.toBeSelectedPoint = toBeSelectedPoint;
        this.background = background;
    }

    public LineStyle getMountain() {
        return mountain;
    }

    public LineStyle getValley() {
        return valley;
    }

    public LineStyle getEdge() {
        return edge;
    }

    public LineStyle getAux() {
        return aux;
    }

    public LineStyle getUnknown() {
        return unknown;
    }

    public LineStyle getSelectedLine() {
        return selectedLine;
    }

    public LineStyle getToBeSelectedLine() {
        return toBeSelectedLine;
    }

    public PointStyle getDefaultPoint() {
        return defaultPoint;
    }

    public PointStyle getSelectedPoint() {
        return selectedPoint;
    }

    public PointStyle getToBeSelectedPoint() {
        return toBeSelectedPoint;
    }

    public void setMountain(LineStyle mountain) {
        this.mountain = mountain;
    }

    public void setValley(LineStyle valley) {
        this.valley = valley;
    }

    public void setEdge(LineStyle edge) {
        this.edge = edge;
    }

    public void setAux(LineStyle aux) {
        this.aux = aux;
    }

    public void setUnknown(LineStyle unknown) {
        this.unknown = unknown;
    }

    public void setSelectedLine(LineStyle selectedLine) {
        this.selectedLine = selectedLine;
    }

    public void setToBeSelectedLine(LineStyle toBeSelectedLine) {
        this.toBeSelectedLine = toBeSelectedLine;
    }

    public void setDefaultPoint(PointStyle defaultPoint) {
        this.defaultPoint = defaultPoint;
    }

    public void setSelectedPoint(PointStyle selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public void setToBeSelectedPoint(PointStyle toBeSelectedPoint) {
        this.toBeSelectedPoint = toBeSelectedPoint;
    }
}
