package io.github.dopadream.neoforge.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dopadream.MineFLAC;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static io.github.dopadream.MineFLAC.MOD_ID;

public record SnowyBiomeModifier(HolderSet<Biome> biomes, int value) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            builder.getSpecialEffects().backgroundMusic(MineFLAC.SNOWY_MUSIC);
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return SNOWY_BIOME_MODIFIER.get();
    }

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MOD_ID);

    public static final Supplier<MapCodec<SnowyBiomeModifier>> SNOWY_BIOME_MODIFIER =
            BIOME_MODIFIERS.register("snowy_biome_modifier", () -> RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            Biome.LIST_CODEC.fieldOf("biomes").forGetter(SnowyBiomeModifier::biomes),
                            Codec.INT.fieldOf("value").forGetter(SnowyBiomeModifier::value)
                    ).apply(instance, SnowyBiomeModifier::new)
            ));

    public static void init() {
        MineFLAC.logWithModId("Registering snowy biome modifications for");
    }
}
