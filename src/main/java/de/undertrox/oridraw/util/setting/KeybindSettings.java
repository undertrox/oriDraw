package de.undertrox.oridraw.util.setting;

import de.undertrox.oridraw.util.registry.Registries;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class KeybindSettings {
    private List<KeybindSetting> keybinds;

    public KeybindSettings() {
        keybinds = new ArrayList<>();
    }

    public void loadFromRegistry() {
        keybinds.clear();
        for (var o : Registries.KEYBIND_REGISTRY.getEntries()) {
            keybinds.add(o.getValue());
        }
    }

    public void apply(Scene scene){
        for (KeybindSetting keybind : keybinds) {
            scene.getAccelerators().put(keybind.getKeyCombination(), keybind.getAction().getRunnable());
        }
    }

    public List<KeybindSetting> getKeybinds() {
        return keybinds;
    }
}
