package io.github.dopadream.fabric;

import io.github.dopadream.MineFLAC;
import net.fabricmc.api.ModInitializer;

public final class MineFLACFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MineFLAC.init();
    }
}
