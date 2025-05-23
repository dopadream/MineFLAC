package io.github.dopadream.neoforge;

import io.github.dopadream.MineFLAC;
import io.github.dopadream.neoforge.modifiers.CaveBiomeModifier;
import io.github.dopadream.neoforge.modifiers.SnowyBiomeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = MineFLAC.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MineFLACEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(MineFLAC::init);
        event.enqueueWork(CaveBiomeModifier::init);
        event.enqueueWork(SnowyBiomeModifier::init);
    }
}
