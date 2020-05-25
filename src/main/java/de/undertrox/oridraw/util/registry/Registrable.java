package de.undertrox.oridraw.util.registry;

public class Registrable {
    private RegistryKey registryKey;

    void setRegistryKey(RegistryKey registryKey) {
        if (this.registryKey != null) {
            throw new RegistryException(String.format(
                    "Tried to register the same Object twice, once with key '%s' and once with key '%s'",
                    this.registryKey, registryKey));
        }
        this.registryKey = registryKey;
    }

    public RegistryKey getRegistryKey() {
        return registryKey;
    }
}
