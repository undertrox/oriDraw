package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.MainWindowController;

public class ActivateToolAction extends Action {
    CreasePatternToolFactory<?> factory;

    public ActivateToolAction(CreasePatternToolFactory<?> factory) {
        this.factory = factory;
    }


    @Override
    public void action() {
        MainWindowController.instance.getToolButtonForRegistryKey(factory.getRegistryKey()).fire();
    }
}
