package com.cwelth.radiateemall;

import com.cwelth.radiateemall.handlers.CommandReloadConfig;
import com.cwelth.radiateemall.handlers.CommandSetRadiation;
import com.cwelth.radiateemall.handlers.RadiateUnderSunlight;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by zth on 07/08/18.
 */


@Mod(modid = ModMain.MODID, name = ModMain.NAME, version = ModMain.VERSION, acceptableRemoteVersions = "*")
public class ModMain {

    public static final String NAME = "Radiate'em All";
    public static final String MODID = "radiateemall";
    public static final String VERSION = "1.0";

    public Configuration config;
    public static boolean enableNausea = true;
    public static int nauseaThreshold = 800000;
    public static boolean enableHunger = true;
    public static int hungerThreshold = 200000;
    public static boolean enableBlindness = true;
    public static int blindnessThreshold = 2000000;
    public static boolean enablePoison = true;
    public static int poisonThreshold = 4000000;
    public static int radiationStrength = 100;
    public static int radiationDelay = 1080000;
    public static int maximumRadiation = -1;
    public static int minimumRadiation = -1;
    public static ArrayList<String> biomesList = new ArrayList<>();
    public static boolean isBiomesWhiteList = false;
    public static int blocksToIsolateRadiation = 4;
    public static int radPillsHealFactor = 50;

    @SidedProxy(clientSide="com.cwelth.radiateemall.ClientProxy", serverSide="com.cwelth.radiateemall.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger logger = Logger.getLogger(NAME);

    public void loadConfig()
    {
        config.load();
        enableNausea = config.getBoolean("enableNausea", "Radiation", enableNausea, "Enable Nausea effect");
        nauseaThreshold = config.getInt("nauseaThreshold", "Radiation", nauseaThreshold, 0, 10000000,"Nausea effect starts at radiation level");
        enableHunger = config.getBoolean("enableHunger", "Radiation", enableHunger, "Enable Hunger effect");
        hungerThreshold = config.getInt("hungerThreshold", "Radiation", hungerThreshold, 0, 10000000, "Hunger effect starts at radiation level");
        enableBlindness = config.getBoolean("enableBlindness", "Radiation", enableBlindness, "Enable Blindness effect");
        blindnessThreshold = config.getInt("blindnessThreshold", "Radiation", blindnessThreshold, 0, 10000000, "Blindness effect starts at radiation level");
        enablePoison = config.getBoolean("enablePoison", "Radiation", enablePoison, "Enable Poison effect");
        poisonThreshold = config.getInt("poisonThreshold", "Radiation", poisonThreshold, 0, 10000000, "Poison effect starts at radiation level");
        radiationStrength = config.getInt("radiationStrength", "Radiation", radiationStrength, 0, 1000, "Amount of radiation player gains in 20 ticks");
        radiationDelay = config.getInt("radiationDelay", "Radiation", radiationDelay, 0, 10000000, "Radiation in world appears after (in ticks, 1 minecraft day equals 24000 ticks)");
        maximumRadiation = Math.max(Math.max(Math.max(nauseaThreshold, hungerThreshold), blindnessThreshold), poisonThreshold);
        minimumRadiation = Math.min(Math.min(Math.min(nauseaThreshold, hungerThreshold), blindnessThreshold), poisonThreshold);
        isBiomesWhiteList = config.getBoolean("isBiomesWhiteList", "Radiation", isBiomesWhiteList, "Biomes in the list are Whitelisted?");
        Collections.addAll(biomesList, config.getStringList("biomesList", "Radiation", new String[]{"BiomeWasteland"}, "List of Biomes to (not) apply radiation in"));
        blocksToIsolateRadiation = config.getInt("blocksToIsolateRadiation", "Radiation", blocksToIsolateRadiation, 1, 100, "Blocks above players head to prevent radiation accumulation");
        radPillsHealFactor = config.getInt("radPillsHealFactor", "Radiation", radPillsHealFactor, 1, 1000, "Multiply factor (times radiationStrength) for RAD pills effectiveness");


        config.save();
    }

    @Mod.Instance("radiateemall")
    public static ModMain instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        config = new Configuration(e.getSuggestedConfigurationFile());
        loadConfig();

        ModItems.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(new RadiateUnderSunlight());

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandReloadConfig());
        event.registerServerCommand(new CommandSetRadiation());
    }

}
