package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.load.Loader;

public class DocumentLoaderRegistry extends Registry<Loader<Document>> {
    @Override
    protected void onRegistered(RegistryEntry<Loader<Document>> entry) {
        // nothing to do
    }

    @Override
    protected void onLocked() {
        // TODO: add code for adding Menu items
    }
}
