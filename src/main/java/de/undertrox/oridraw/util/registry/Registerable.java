package de.undertrox.oridraw.util.registry;

public class Registerable {
    private RegistryItem<? extends Registerable> entry;

    void setEntry(RegistryItem<? extends Registerable> entry) {
        this.entry = entry;
    }

    public RegistryKey getKey() {
        return getRegistryEntry().getKey();
    }

    public RegistryItem<? extends Registerable> getRegistryEntry() {
        return entry;
    }
}
