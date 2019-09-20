package com.cwelth.radiateemall.handlers;

import com.cwelth.radiateemall.ModMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RadiateUnderSunlight {

    @SubscribeEvent
    public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        if (world.getTotalWorldTime() % 20 != 0)
            return;

        if (!(entity instanceof EntityPlayer))
            return;


        if(!world.isRemote) {
            long wt = entity.getEntityWorld().getTotalWorldTime();

            EntityPlayer player = (EntityPlayer) entity;
            String biome = player.world.getBiome(player.getPosition()).getBiomeClass().getSimpleName();
            if (ModMain.isBiomesWhiteList && !ModMain.biomesList.contains(biome) || !ModMain.isBiomesWhiteList && ModMain.biomesList.contains(biome))
            {
                if (world.isDaytime() && radiateHim(player.world, player.getPosition()) && wt >= ModMain.radiationDelay) {
                    long radAmount = ModMain.radiationStrength;
                    long radiated = player.getEntityData().getLong(ModMain.MODID + "_radiatedtime") + radAmount;
                    if (radiated > ModMain.instance.maximumRadiation) radiated = ModMain.instance.maximumRadiation + 1;
                    if (!player.isCreative())
                        player.getEntityData().setLong(ModMain.MODID + "_radiatedtime", radiated);

                    if (radiated > ModMain.instance.nauseaThreshold && ModMain.instance.enableNausea)
                        player.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 80, 1));
                    if (radiated > ModMain.instance.hungerThreshold && ModMain.instance.enableHunger)
                        player.addPotionEffect(new PotionEffect(Potion.getPotionById(17), 80, 1));
                    if (radiated > ModMain.instance.blindnessThreshold && ModMain.instance.enableBlindness)
                        player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 80, 1));
                    if (radiated > ModMain.instance.poisonThreshold && ModMain.instance.enablePoison)
                        player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 80, 1));
                } else {
                    long radiated = player.getEntityData().getLong(ModMain.MODID + "_radiatedtime");
                    if (radiated < 0) radiated = 0L;

                    if (!player.isCreative())
                        player.getEntityData().setLong(ModMain.MODID + "_radiatedtime", radiated);
                }
            }
        }
    }

    public boolean radiateHim(World worldIn, BlockPos pos)
    {
        if(worldIn.provider.getDimension() != 0)return false;
        int countSolid = 0;
        for(int Ypos = pos.getY(); Ypos <= 255; Ypos++)
        {
            if(!worldIn.isAirBlock(new BlockPos(pos.getX(), Ypos, pos.getZ())))
                if(worldIn.isSideSolid(new BlockPos(pos.getX(), Ypos, pos.getZ()), EnumFacing.DOWN) || worldIn.isSideSolid(new BlockPos(pos.getX(), Ypos, pos.getZ()), EnumFacing.UP))countSolid++;
        }
        if(countSolid >= ModMain.blocksToIsolateRadiation)return false;
        return true;
    }
}
