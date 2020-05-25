package de.undertrox.oridraw.util.registry;

public class RegistryEntry<T extends Registrable> {
    private T value;
    private RegistryKey key;
    private static int maxID = 0;
    private int id;

    public RegistryEntry(RegistryKey key, T value) {
        this.value = value;
        this.key = key;
        value.setRegistryKey(key);
        this.id = maxID;
        maxID++;
    }

    /**
     * @return Registry ID of the Entry. this ID is only permanent for the duration of one session, it
     * might not be persistent across multiple sessions
     */
    public int getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

    public RegistryKey getKey() {
        return key;
    }
}
