package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.export.Exporter;

public class DocumentExporterRegistry extends Registry<Exporter<Document>> {

    @Override
    protected void onRegistered(RegistryEntry<Exporter<Document>> entry) {
        // nothing to do
    }

    @Override
    protected void onLocked() {
        // TODO: add code for adding Menu items
    }
}
