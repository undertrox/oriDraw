package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.export.Exporter;

public class DocumentExporterRegistry extends Registry<Exporter<Document>> {

    @Override
    protected void onRegistered(RegistryKey key, Exporter<Document> item) {

    }

    @Override
    protected void onLocked() {

    }
}
