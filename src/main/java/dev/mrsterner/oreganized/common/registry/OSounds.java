package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OSounds {
    private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

    public static SoundEvent MUSIC_DISC_PILLAGED = register("music_disc.pillaged");
    public static SoundEvent MUSIC_DISC_18 = register("music_disc.18");
    public static SoundEvent MUSIC_DISC_SHULK = register("music_disc.shulk");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Oreganized.MODID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(soundEvent, id);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
    }
}
