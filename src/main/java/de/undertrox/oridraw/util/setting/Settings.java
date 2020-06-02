package de.undertrox.oridraw.util.setting;

import java.util.prefs.Preferences;

public class Settings {
    private Preferences prefs;


    private KeybindSettings keybindSettings;

    public void init() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        keybindSettings = new KeybindSettings();
    }

    public Preferences getPrefs() {
        return prefs;
    }
    
    public KeybindSettings getKeybindSettings() {
        return keybindSettings;
    }

    public Preferences getKeybindNode() {
        return prefs.node("keybinds");
    }
}
