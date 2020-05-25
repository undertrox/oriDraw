package de.undertrox.oridraw.util.io.load;

import de.undertrox.oridraw.util.registry.Registrable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

public abstract class Loader<T> extends Registrable {
    protected Logger logger = LogManager.getLogger(Loader.class);

    /**
     *
     * @return Array of all file extensions this loader can load from (in lowercase)
     */
    public abstract String[] extensions();

    /**
     * Loads from the Reader r
     * @param r: reader to load data from
     * @return Loaded data
     * @throws IOException if reading fails
     */
    public abstract T load(Reader r) throws IOException;
}
