package io.github.dopadream.mixin;

import io.github.dopadream.registry.SoundRegistry;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Gui gui;

    @Shadow
    @Final
    private MusicManager musicManager;


    @Shadow @Nullable public ClientLevel level;

    @Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
    private void getSituationalMusic(CallbackInfoReturnable<MusicInfo> cir) {
        // Screen music takes priority
        Music screenMusic = Optionull.map(this.screen, Screen::getBackgroundMusic);
        if (screenMusic != null) {
            cir.setReturnValue(new MusicInfo(screenMusic));
            return;
        }

        // If no player is present, fallback to menu music
        if (this.player == null) {
            cir.setReturnValue(new MusicInfo(Musics.MENU));
            return;
        }

        Level level = this.player.level();

        // End dimension music check
        if (level.dimension() == Level.END) {
            cir.setReturnValue(new MusicInfo(this.gui.getBossOverlay().shouldPlayMusic() ? Musics.END_BOSS : Musics.END));
            return;
        }

        // Biome-based music logic
        BlockPos playerPos = this.player.blockPosition();
        Holder<Biome> biome = level.getBiome(playerPos);
        float volume = biome.value().getBackgroundMusicVolume();
        Biome.Precipitation precipitation = biome.value().getPrecipitationAt(playerPos, playerPos.getY());

        boolean playsUnderwater = biome.is(BiomeTags.PLAYS_UNDERWATER_MUSIC);
        boolean isUnderwater = this.player.isUnderWater();
        boolean isInCave = (!level.canSeeSky(player.blockPosition()) && player.getY() < level.getSeaLevel()) || biome.is(io.github.dopadream.registry.BiomeTags.HAS_CAVES_MUSIC);

        // Underwater music check
        if (!this.musicManager.isPlayingMusic(Musics.UNDER_WATER) && isUnderwater && playsUnderwater) {
            cir.setReturnValue(new MusicInfo(Musics.UNDER_WATER, volume));
            return;
        }

        // Cave music check
        if (!this.musicManager.isPlayingMusic(SoundRegistry.CAVE_MUSIC) && isInCave
                && !biome.is(Biomes.LUSH_CAVES)
                && !biome.is(Biomes.DEEP_DARK)) {
            cir.setReturnValue(new MusicInfo(SoundRegistry.CAVE_MUSIC, volume));
            return;
        }

        // Night time music check
        if (level.isMoonVisible() && !isInCave && level.canSeeSky(player.blockPosition())) {
            if (precipitation == Biome.Precipitation.RAIN) {
                if (!(level.isRaining() || level.isThundering())) {
                    cir.setReturnValue(new MusicInfo(SoundRegistry.NIGHT_MUSIC, volume));
                    return;
                } else {
                    cir.setReturnValue(new MusicInfo(SoundRegistry.RAIN_NIGHT_MUSIC, volume));
                    return;
                }
            } else {
                cir.setReturnValue(new MusicInfo(SoundRegistry.NIGHT_MUSIC, volume));
                return;
            }
        }

        // Rain music check
        if ((level.isRaining() || level.isThundering()) && precipitation == Biome.Precipitation.RAIN && !isInCave) {
            cir.setReturnValue(new MusicInfo(SoundRegistry.RAIN_MUSIC, volume));
            return;
        }

        // Creative mode music check
        if (level.dimension() != Level.NETHER && this.player.getAbilities().instabuild && this.player.getAbilities().mayfly) {
            cir.setReturnValue(new MusicInfo(Musics.CREATIVE, volume));
            return;
        }

        // Biome-specific music
        Optional<WeightedList<Music>> biomeMusic = biome.value().getBackgroundMusic();
        if (biomeMusic.isPresent()) {
            Optional<Music> music = biomeMusic.get().getRandom(level.random);
            cir.setReturnValue(new MusicInfo(music.orElse(null), volume));
            return;
        }

        // Fallback to game music
        cir.setReturnValue(new MusicInfo(Musics.GAME, volume));
    }
}
