package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.registry.Registrable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public abstract class Action extends Registrable {
    private KeyCombination combination;
    private MenuItem menuItem;


    public Action() {
        combination = KeyCombination.NO_MATCH;
    }

    public void setKeyCombination(KeyCombination combination) {
        this.combination = combination;
        if (menuItem != null) {
            menuItem.setAccelerator(combination);
        }
    }

    public KeyCombination getKeyCombination() {
        return combination;
    }

    public Runnable getRunnable() {
        return this::run;
    }

    public MenuItem getMenuItem() {
        if (menuItem == null)
            createMenuItem();
        return menuItem;
    }

    public abstract void action();

    public void run() {
        action();
    }

    public MenuItem createMenuItem() {
        MenuItem item = new MenuItem(LocalizationHelper.getString(getRegistryKey().getDomain() + ".actions." + getRegistryKey().getId() + ".name"));
        item.setOnAction((event -> run()));
        item.setAccelerator(getKeyCombination());
        menuItem = item;
        return item;
    }
}
