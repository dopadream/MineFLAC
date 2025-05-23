package io.github.dopadream.registry;

import io.github.dopadream.MineFLAC;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BiomeTags {
    public static final TagKey<Biome> HAS_SNOWY_MUSIC = bind("music_pool/has_snowy_music");
    public static final TagKey<Biome> HAS_CAVES_MUSIC = bind("music_pool/has_caves_music");

    private BiomeTags() {
        throw new UnsupportedOperationException("MineFLAC BiomeTags contains only static declarations.");
    }

    @NotNull
    private static TagKey<Biome> bind(@NotNull String path) {
        return TagKey.create(Registries.BIOME, MineFLAC.id(path));
    }
}
