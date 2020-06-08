package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;
import de.undertrox.oridraw.util.io.load.Loader;

public class DocumentLoaderRegistry extends Registry<Loader<Document>> {
    @Override
    protected void onRegistered(RegistryEntry<Loader<Document>> entry) {
        // nothing to do
    }

    @Override
    protected void onLocked() {
        for (RegistryEntry<Loader<Document>> entry : getEntries()) {
        Action action = new Action(() ->
            MainWindowController.instance.openFile(entry.getValue())
        );
        Registries.ACTION_REGISTRY.register(new RegistryKey(Constants.REGISTRY_DOMAIN, "action_import_" + entry.getKey().getId()),
                action, MainWindowController.instance.importMenu);
    }
    }
}
