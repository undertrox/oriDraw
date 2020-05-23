package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.util.registry.Registerable;

import java.io.Writer;

public abstract class Exporter<T> extends Registerable {
    public abstract String[] extensions();

    public abstract void export(T obj, Writer writer) throws Exception;
}
