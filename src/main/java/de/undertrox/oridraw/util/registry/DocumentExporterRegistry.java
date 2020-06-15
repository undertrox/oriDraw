package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;
import de.undertrox.oridraw.ui.action.ExportDocAction;
import de.undertrox.oridraw.util.io.export.Exporter;

public class DocumentExporterRegistry extends Registry<Exporter<Document>> {

    @Override
    protected void onRegistered(RegistryEntry<Exporter<Document>> entry) {
        // nothing to do
    }

    @Override
    protected void onLocked() {
        for (RegistryEntry<Exporter<Document>> entry : getEntries()) {
            Action action = new ExportDocAction(entry.getValue());
            Registries.ACTION_REGISTRY.register(new RegistryKey(Constants.REGISTRY_DOMAIN, "export_" + entry.getKey().getId()),
                    action, MainWindowController.instance.exportMenu);
        }
    }
}
