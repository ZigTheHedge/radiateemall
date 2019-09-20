package com.cwelth.radiateemall;

import com.cwelth.radiateemall.items.ItemGeigerCounter;
import com.cwelth.radiateemall.items.RadPills;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=ModMain.MODID)
public class ModItems {

    static Item itemGiegerCounter;
    static Item itemRadPills;

    public static void init() {
        itemGiegerCounter = new ItemGeigerCounter("geiger_counter").setCreativeTab(CreativeTabs.MISC).setMaxStackSize(1);
        itemRadPills = new RadPills("radpills").setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(16);

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(itemGiegerCounter, itemRadPills);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(itemGiegerCounter);
        registerRender(itemRadPills);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }

}
