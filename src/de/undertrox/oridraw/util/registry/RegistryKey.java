package de.undertrox.oridraw.util.registry;

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

    public RegistryKey(String domain, String id) {
        if (domain.contains(":") || id.contains(":")) {
            throw new RegistryException("Invalid Registry name: neither domain nor id can contain :");
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
