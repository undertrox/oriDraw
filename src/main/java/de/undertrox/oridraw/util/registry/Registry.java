package de.undertrox.oridraw.util.registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class Registry<T extends Registrable> {
    private Logger logger = LogManager.getLogger(Registry.class);
    // This map stores all registry entries
    private Map<RegistryKey, RegistryEntry<T>> registryEntries;
    // if this is set to true, no registry entries can be added
    private boolean locked;

    public Registry() {
        logger.debug("Initializing {}", getClass().getSimpleName());
        registryEntries = new HashMap<>();
        locked = false;
    }

    /**
     * Registers an item
     * Throws a RegistryException if the Registry is locked or an Object
     * is already registered for the key.
     * @param key: Registry Key for the item
     * @param item: Object to be registered
     */
    public void register(RegistryKey key, T item) {
        if (locked) {
            throw new RegistryException(getClass().getSimpleName() + " is locked.");
        }
        if (registryEntries.containsKey(key)) {
            throw new RegistryException("'" + key + "' is already registered in this registry.");
        }
        RegistryEntry<T> registryEntry = new RegistryEntry<>(key, item);
        registryEntries.put(key, registryEntry);

        onRegistered(registryEntry);
        logger.debug("{}: registered item '{}' for key '{}'", getClass().getSimpleName(), item, key);
    }

    /**
     * Registers an item
     * Throws a RegistryException if the Registry is locked or an Object
     * is already registered for the key.
     * @param domain: Registry Key domain for the item
     * @param id: Registry Key id for the item
     * @param item: Object to be registered
     */
    public void register(String domain, String id, T item) {
        register(new RegistryKey(domain, id), item);
    }

    public void register(String key, T item) {
        register(RegistryKey.parse(key), item);
    }

    /**
     * Locks the Registry so that no more Objects can be registered.
     * Throws a RegistryException if the Registry is already locked.
     */
    public void lock() {
        if (locked) {
            throw new RegistryException("Registry is already locked.");
        }
        this.locked = true;
        onLocked();
    }

    /**
     * Returns the Object that is registered with the RegistryKey key
     * in this registry. Returns null if no Object is registered for the
     * RegistryKey
     * @param key: RegistryKey to get the Object of
     * @return Object that is registered with key
     */
    public T getItem(RegistryKey key) {
        return registryEntries.get(key).getValue();
    }

    public T getItem(String key) {
        return getItem(RegistryKey.parse(key));
    }

    /**
     * Returns the Object that is registered with the RegistryKey key
     * in this registry. Returns null if no Object is registered for the
     * RegistryKey
     * @param domain: Domain of the Registry key
     * @param id: id of the Registry key
     * @return Object that is registered with key
     */
    public T getItem(String domain, String id) {
        return getItem(new RegistryKey(domain, id));
    }

    /**
     * Returns a Collection of all RegistryEntries that are registered
     * in this registry
     * @return Collection of all RegistryEntries registered in this registry
     */
    public List<RegistryEntry<T>> getEntries() {
        List<RegistryEntry<T>> l = new ArrayList<>(registryEntries.values());
        l.sort(Comparator.comparingInt(RegistryEntry::getId));
        return l;
    }

    /**
     * Called every time a new Object is registered, after the Object has been registered
     * @param entry: RegistryEntry that was just registered
     */
    protected abstract void onRegistered(RegistryEntry<T> entry);

    /**
     * Called after the Registry is locked.
     */
    protected abstract void onLocked();

}
