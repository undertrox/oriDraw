package de.undertrox.oridraw.ui.action;

import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.registry.Registrable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

import java.util.ArrayList;
import java.util.List;

public class Action extends Registrable {
    private Runnable runnable;
    private List<KeyCombination> combinations;
    private MenuItem menuItem;


    public Action(Runnable runnable) {
        this.runnable = runnable;
        combinations = new ArrayList<>();
    }

    public void addKeyCombination(KeyCombination combination) {
        combinations.add(combination);
    }

    public List<KeyCombination> getCombinations() {
        return new ArrayList<>(combinations);
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public MenuItem getMenuItem() {
        if (menuItem == null)
            createMenuItem();
        return menuItem;
    }

    public void run() {
        runnable.run();
    }

    public MenuItem createMenuItem() {
        MenuItem item = new MenuItem(LocalizationHelper.getString(getRegistryKey().getDomain() + ".actions." + getRegistryKey().getId() + ".name"));
        item.setOnAction((event -> run()));
        menuItem = item;
        return item;
    }
}
