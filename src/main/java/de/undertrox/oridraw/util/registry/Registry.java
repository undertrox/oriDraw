package de.undertrox.oridraw.util.registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Registry<T extends Registerable> {
    private Logger logger = LogManager.getLogger(Registry.class);
    private Map<RegistryKey, RegistryItem<T>> registry;
    private boolean locked;

    public Registry() {
        logger.debug("Initializing " + getClass().getSimpleName());
        registry = new HashMap<>();
        locked = false;
    }

    public void register(RegistryKey key, T item) {
        if (locked) {
            throw new RegistryException(getClass().getSimpleName() + " is locked.");
        }
        if (registry.containsKey(key)) {
            throw new RegistryException("'" + key + "' is already registered.");
        }
        RegistryItem<T> registryItem = new RegistryItem<>(key, item);
        registry.put(key, registryItem);
        item.setEntry(registryItem);

        onRegistered(key, item);
        logger.debug(getClass().getSimpleName() + ": registered item " + item + " for key '" + key + "'");
    }

    public void register(String domain, String id, T item) {
        register(new RegistryKey(domain, id), item);
    }

    public void lock() {
        if (locked) {
            throw new RegistryException("Registry is already locked.");
        }
        this.locked = true;
        onLocked();
    }

    public T getEntry(RegistryKey key) {
        return registry.get(key).getValue();
    }

    public T getEntry(String domain, String id) {
        return getEntry(new RegistryKey(domain, id));
    }

    public Collection<RegistryItem<T>> getItems() {
        return registry.values();
    }


    protected abstract void onRegistered(RegistryKey key, T item);

    protected abstract void onLocked();

}
