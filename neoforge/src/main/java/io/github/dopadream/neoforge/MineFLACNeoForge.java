package io.github.dopadream.neoforge;

import io.github.dopadream.MineFLAC;
import io.github.dopadream.MineFLACClient;
import io.github.dopadream.neoforge.modifiers.CaveBiomeModifier;
import io.github.dopadream.neoforge.modifiers.SnowyBiomeModifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(MineFLAC.MOD_ID)
public final class MineFLACNeoForge {
    public MineFLACNeoForge(IEventBus modBus) {

        SnowyBiomeModifier.BIOME_MODIFIERS.register(modBus);
        CaveBiomeModifier.BIOME_MODIFIERS.register(modBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            MineFLACClient.init();
        }
    }
}
