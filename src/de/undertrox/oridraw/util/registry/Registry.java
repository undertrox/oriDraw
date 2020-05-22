package de.undertrox.oridraw.util.registry;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Registry<T> {
    private Logger logger = Logger.getLogger(Registry.class);
    private Map<RegistryKey, RegistryItem<T>> registry;
    private boolean locked;

    public Registry() {
        logger.debug("Initializing " + getClass().getCanonicalName());
        registry = new HashMap<>();
        locked = false;
    }

    public void register(RegistryKey key, T item) {
        if (locked) {
            throw new RegistryException(getClass().getCanonicalName() + " is locked.");
        }
        if (registry.containsKey(key)) {
            throw new RegistryException(key + " is already registered.");
        }
        registry.put(key, new RegistryItem<>(key, item));
        onRegistered(key, item);
        logger.debug(getClass().getCanonicalName() + ": registered item " + item + " for key " + key);
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
