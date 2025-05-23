package io.github.dopadream.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.dopadream.MineFLAC;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import static io.github.dopadream.MineFLAC.MOD_ID;
import static io.github.dopadream.MineFLAC.id;

public class SoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> MUSIC_OVERWORLD_SNOWY = SOUND_EVENTS.register("music.overworld.snowy", () -> SoundEvent.createVariableRangeEvent(id("music.overworld.snowy")));
    public static final RegistrySupplier<SoundEvent> MUSIC_OVERWORLD_CAVES = SOUND_EVENTS.register("music.overworld.caves", () -> SoundEvent.createVariableRangeEvent(id("music.overworld.caves")));
    public static final RegistrySupplier<SoundEvent> MUSIC_OVERWORLD_NIGHT = SOUND_EVENTS.register("music.overworld.night", () -> SoundEvent.createVariableRangeEvent(id("music.overworld.night")));
    public static final RegistrySupplier<SoundEvent> MUSIC_OVERWORLD_RAIN = SOUND_EVENTS.register("music.overworld.rain", () -> SoundEvent.createVariableRangeEvent(id("music.overworld.rain")));
    public static final RegistrySupplier<SoundEvent> MUSIC_OVERWORLD_RAIN_NIGHT = SOUND_EVENTS.register("music.overworld.rain_night", () -> SoundEvent.createVariableRangeEvent(id("music.overworld.rain_night")));

    public static final Music CAVE_MUSIC = new Music(MUSIC_OVERWORLD_CAVES, 12000, 24000, false);
    public static final Music NIGHT_MUSIC = new Music(MUSIC_OVERWORLD_NIGHT, 12000, 24000, false);
    public static final Music RAIN_MUSIC = new Music(MUSIC_OVERWORLD_RAIN, 12000, 24000, false);
    public static final Music RAIN_NIGHT_MUSIC = new Music(MUSIC_OVERWORLD_RAIN_NIGHT, 12000, 24000, false);

    private SoundRegistry() {
        throw new UnsupportedOperationException("MineFLAC SoundRegistry contains only static declarations.");
    }

    public static void init() {
        MineFLAC.logWithModId("Registering SoundEvents for");
        SOUND_EVENTS.register();
    }
}
