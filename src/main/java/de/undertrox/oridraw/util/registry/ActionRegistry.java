package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.ui.action.Action;
import javafx.scene.control.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ActionRegistry extends Registry<Action> {
    private Logger logger = LogManager.getLogger();
    @Override
    protected void onRegistered(RegistryEntry<Action> entry) {

    }

    public void register(RegistryKey key, Action action, Menu menuParent) {
        super.register(key, action);
        menuParent.getItems().add(action.createMenuItem());
    }

    public void register(String domain, String key, Action action, Menu menuParent) {
        register(new RegistryKey(domain, key), action, menuParent);
    }

    @Override
    public void register(RegistryKey key, Action item) {
        logger.warn("ActionRegistry#register(key, item) should not be used, please use ActionRegistry#register(key,item,menuParent) instead");
        super.register(key, item);
    }

    @Override
    protected void onLocked() {

    }
}
