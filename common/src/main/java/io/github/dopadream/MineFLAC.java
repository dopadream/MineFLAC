package io.github.dopadream;

import dev.architectury.registry.level.biome.BiomeModifications;
import io.github.dopadream.registry.BiomeTags;
import io.github.dopadream.registry.SoundRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

import static com.mojang.text2speech.Narrator.LOGGER;

public final class MineFLAC {
    public static final String MOD_ID = "mineflac";

    public static Music SNOWY_MUSIC = Musics.createGameMusic(SoundRegistry.MUSIC_OVERWORLD_SNOWY);
    public static WeightedList<Music> weightedSnowyMusic = WeightedList.<Music>builder()
            .add(SNOWY_MUSIC, 1)
            .build();

    public static Music CAVES_MUSIC = Musics.createGameMusic(SoundRegistry.MUSIC_OVERWORLD_SNOWY);
    public static WeightedList<Music> weightedCavesMusic = WeightedList.<Music>builder()
            .add(CAVES_MUSIC, 1)
            .build();

    public static void init() {

        BiomeModifications.addProperties(
                (biomeContext) -> biomeContext.hasTag(BiomeTags.HAS_SNOWY_MUSIC),
                (selectionContext, modificationContext) -> {
                    System.out.println("Setting music for: " + selectionContext.getKey().toString());
                    modificationContext.getEffectsProperties().setBackgroundMusic(weightedSnowyMusic);
                }
        );

        BiomeModifications.addProperties(
                (biomeContext) -> biomeContext.hasTag(BiomeTags.HAS_CAVES_MUSIC),
                (selectionContext, modificationContext) -> {
                    System.out.println("Setting music for: " + selectionContext.getKey().toString());
                    modificationContext.getEffectsProperties().setBackgroundMusic(weightedCavesMusic);
                }
        );
    }

    @NotNull
    public static ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void logWithModId(String message) {
            LOGGER.info(message + " " + MOD_ID);
    }

}
