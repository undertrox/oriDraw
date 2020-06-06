package de.undertrox.oridraw.util.setting;

import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.registry.Registrable;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.KeyCombination;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class KeybindSetting extends Registrable {
    private Runnable action;
    private KeyCombination keyCombination;

    public final SimpleStringProperty localizationKey;

    public final SimpleStringProperty keybind;


    public void saveTo(Preferences node) {
        node.put(getRegistryKey().toString(), keyCombination.getName());
    }

    private KeybindSetting(Runnable action, KeyCombination keyCombination, RegistryKey key) {
        this.action = action;
        this.keyCombination = keyCombination;
        keybind = new SimpleStringProperty(keyCombination.getDisplayText());
        localizationKey = new SimpleStringProperty(key.getDomain() + ".settings.keybinds." + key.getId());
    }

    public Runnable getAction() {
        return action;
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    public void setKeyCombination(KeyCombination keyCombination) {
        this.keyCombination = keyCombination;
        this.keybind.set(keyCombination.getDisplayText());
    }

    public static KeybindSetting fromPref(Preferences node, RegistryKey key, Runnable action, KeyCombination defaultKC) {
        String kc = node.get(key.toString(), defaultKC.getName());
        KeyCombination keyCombination;
        if (!kc.isBlank()) {
            keyCombination = KeyCombination.valueOf(kc);
        } else {
            keyCombination = KeyCombination.NO_MATCH;
        }
        node.put(key.toString(), kc);
        return new KeybindSetting(action, keyCombination, key);
    }

    public String getLocalizationKey() {
        return localizationKey.get();
    }

    public SimpleStringProperty localizationKeyProperty() {
        return localizationKey;
    }

    public String getKeybind() {
        return keybind.get();
    }

    public SimpleStringProperty keybindProperty() {
        return keybind;
    }
}