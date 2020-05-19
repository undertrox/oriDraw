package de.undertrox.oridraw.util.io.load;

import org.apache.log4j.Logger;

import java.io.Reader;

public abstract class Loader<T> {
    protected Logger logger = Logger.getLogger(Loader.class);

    public abstract T load(Reader r) throws Exception;
}
