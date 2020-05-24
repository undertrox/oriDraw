package de.undertrox.oridraw.util.registry;

public class RegistryItem<T> {
    private T value;
    private RegistryKey key;

    public RegistryItem(RegistryKey key, T value) {
        this.value = value;
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public RegistryKey getKey() {
        return key;
    }
}
