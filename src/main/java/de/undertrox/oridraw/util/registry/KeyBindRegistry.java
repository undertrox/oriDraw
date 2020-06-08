package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.util.setting.KeybindSetting;

public class KeyBindRegistry extends Registry<KeybindSetting> {
    @Override
    protected void onRegistered(RegistryEntry<KeybindSetting> entry) {
        entry.getValue().getAction().addKeyCombination(entry.getValue().getKeyCombination());
    }

    @Override
    protected void onLocked() {
    }
}
