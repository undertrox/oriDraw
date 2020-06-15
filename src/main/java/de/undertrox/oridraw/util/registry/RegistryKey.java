package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.Constants;

import java.util.Objects;

public class RegistryKey {
    private String domain;
    private String id;

    public String getDomain() {
        return domain;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return domain + ":" + id;
    }

    /**
     * creates a new RegistryKey object from a string of the format domain:id
     * neither domain nor id can contain colons or be blank
     * @param key: Key to be parsed
     * @return parsed RegistryKey
     */
    public static RegistryKey parse(String key) {
        String[] s = key.split(":");
        // If the key contains no colon, the standard registry domain will be assumed
        if (s.length == 1) {
            return new RegistryKey(Constants.REGISTRY_DOMAIN, key);
        } else if (s.length == 2) {
            return new RegistryKey(s[0], s[1]);
        }
        throw new RegistryException("Invalid Registry name: neither domain nor id can contain ':'");
    }

    /**
     * Creates a new RegistryKey. If the domain or id contain a colon,
     * this will throw a RegistryException
     * @param domain: Domain of the RegistryKey. Usually this is the name of the program that
     *              registers it
     * @param id: Id of the RegistryKey. Usually the name of the Object that will be registered
     */
    public RegistryKey(String domain, String id) {
        if (domain.contains(":") || id.contains(":")) {
            throw new RegistryException("Invalid Registry name: neither domain nor id can contain ':'");
        }
        domain = domain.strip();
        id = id.strip();
        if (domain.isEmpty() || id.isEmpty()) {
            throw new RegistryException("Invalid Registry name: neither domain nor id can be blank.");
        }
        this.domain = domain;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistryKey that = (RegistryKey) o;
        return getDomain().equals(that.getDomain()) &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomain(), getId());
    }
}
