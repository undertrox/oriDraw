package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.load.Loader;

public class DocumentLoaderRegistry extends Registry<Loader<Document>> {
    @Override
    protected void onRegistered(RegistryKey key, Loader<Document> item) {

    }

    @Override
    protected void onLocked() {

    }
}
