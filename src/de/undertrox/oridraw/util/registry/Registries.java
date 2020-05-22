package de.undertrox.oridraw.util.registry;

public class Registries {
    public static final DocumentExporterRegistry documentExporterRegistry = new DocumentExporterRegistry();
    public static final DocumentLoaderRegistry documentLoaderRegistry = new DocumentLoaderRegistry();

    public static void lockAll() {
        documentExporterRegistry.lock();
        documentLoaderRegistry.lock();
    }
}
