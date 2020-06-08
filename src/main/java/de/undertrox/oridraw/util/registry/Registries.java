package de.undertrox.oridraw.util.registry;

public class Registries {
    public static final DocumentExporterRegistry DOCUMENT_EXPORTER_REGISTRY = new DocumentExporterRegistry();
    public static final DocumentLoaderRegistry DOCUMENT_LOADER_REGISTRY = new DocumentLoaderRegistry();
    public static final ToolFactoryRegistry TOOL_FACTORY_REGISTRY = new ToolFactoryRegistry();
    public static final KeyBindRegistry KEYBIND_REGISTRY = new KeyBindRegistry();
    public static final ActionRegistry ACTION_REGISTRY = new ActionRegistry();

    public static void lockAll() {
        DOCUMENT_EXPORTER_REGISTRY.lock();
        DOCUMENT_LOADER_REGISTRY.lock();
        TOOL_FACTORY_REGISTRY.lock();
        ACTION_REGISTRY.lock();
    }

    private Registries() {}
}
