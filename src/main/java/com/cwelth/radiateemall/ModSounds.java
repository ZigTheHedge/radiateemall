package com.cwelth.radiateemall;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=ModMain.MODID)
public class ModSounds {
    public static ResourceLocation geigerSoundRL = new ResourceLocation(ModMain.MODID, "geiger");
    public static SoundEvent geigerSound = new SoundEvent(geigerSoundRL).setRegistryName(geigerSoundRL);

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
        event.getRegistry().register(geigerSound);
    }
}
