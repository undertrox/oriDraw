package de.undertrox.oridraw.util.io.export;

import java.io.Writer;

public abstract class Exporter<T> {
    public abstract void export(T obj, Writer writer) throws Exception;
}
