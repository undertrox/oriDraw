package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.util.registry.Registrable;

import java.io.IOException;
import java.io.Writer;

public abstract class Exporter<T> extends Registrable {
    /**
     *
     * @return Array of all file extensions this loader can load from (in lowercase)
     */
    public abstract String[] extensions();

    /**
     * exports obj using the Writer writer
     * @param writer: writer to export data into
     * @param obj: Data to export
     * @throws IOException if writing fails
     */
    public abstract void export(T obj, Writer writer) throws IOException;
}
