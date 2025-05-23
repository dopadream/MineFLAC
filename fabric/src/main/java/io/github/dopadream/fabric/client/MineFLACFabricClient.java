package io.github.dopadream.fabric.client;

import io.github.dopadream.MineFLACClient;
import net.fabricmc.api.ClientModInitializer;

public final class MineFLACFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MineFLACClient.init();
    }
}
