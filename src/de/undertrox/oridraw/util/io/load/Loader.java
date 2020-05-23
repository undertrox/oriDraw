package de.undertrox.oridraw.util.io.load;

import de.undertrox.oridraw.util.registry.Registerable;
import org.apache.log4j.Logger;

import java.io.Reader;

public abstract class Loader<T> extends Registerable {
    protected Logger logger = Logger.getLogger(Loader.class);


    public abstract String[] extensions();

    public abstract T load(Reader r) throws Exception;
}
