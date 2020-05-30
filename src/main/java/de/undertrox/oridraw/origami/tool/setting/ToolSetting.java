package de.undertrox.oridraw.origami.tool.setting;

import javafx.scene.Node;

public abstract class ToolSetting<T>{
    T state;
    private String id;

    public ToolSetting(String id, T startingState) {
        this.state = startingState;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public abstract Node getControlNode();
}
