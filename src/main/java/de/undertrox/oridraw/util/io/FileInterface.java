package de.undertrox.oridraw.util.io;

import de.undertrox.oridraw.util.registry.Registrable;

public abstract class FileInterface extends Registrable {
    String[] extensions;
    String[] filterExtensions;

    /**
     *
     * @return Array of all file extensions this loader can load from (in lowercase)
     */
    protected abstract String[] extensions();

    public String[] getExtensions() {
        if (extensions == null) {
            extensions = extensions();
        }
        return extensions;
    }

    public String[] getFilterExtensions() {
        if (filterExtensions == null) {
            filterExtensions = new String[getExtensions().length];
            for (int i = 0; i < getExtensions().length; i++) {
                filterExtensions[i] = "*." + getExtensions()[i];
            }
        }
        return filterExtensions;
    }
}
