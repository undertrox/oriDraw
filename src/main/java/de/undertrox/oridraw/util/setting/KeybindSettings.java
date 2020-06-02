package de.undertrox.oridraw.util.setting;

import de.undertrox.oridraw.util.registry.Registries;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KeybindSettings {
    private List<KeybindSetting> keybinds;
    private ResourceBundle bundle;

    public KeybindSettings() {
        keybinds = new ArrayList<>();
    }

    public void loadFromRegistry() {
        keybinds.clear();
        for (var o : Registries.KEYBIND_REGISTRY.getEntries()) {
            keybinds.add(o.getValue());
        }
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
        keybinds.forEach((k) -> k.setBundle(bundle));
    }

    public void apply(Scene scene){
        for (KeybindSetting keybind : keybinds) {
            scene.getAccelerators().put(keybind.getKeyCombination(), keybind.getAction());
        }
    }

    public List<KeybindSetting> getKeybinds() {
        return keybinds;
    }
}
