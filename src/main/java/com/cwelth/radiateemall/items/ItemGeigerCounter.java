package com.cwelth.radiateemall.items;

import com.cwelth.radiateemall.ModMain;
import com.cwelth.radiateemall.ModSounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGeigerCounter extends Item {

    public ItemGeigerCounter(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    private long[] sort(long ... sources)
    {
        long[] retVal = new long[sources.length];
        for(int i = 0; i < sources.length; i++)
        {
            retVal[i] = sources[i];
        }
        int curIdx = 0;
        while(curIdx < sources.length - 1)
        {
            long curValue = retVal[curIdx];
            int minIndex = curIdx;
            for(int i = curIdx + 1; i < sources.length; i++) {
                if (retVal[i] < curValue) {
                    minIndex = i;
                    curValue = retVal[i];
                }
            }
            long tmp = retVal[curIdx];
            retVal[curIdx] = retVal[minIndex];
            retVal[minIndex] = tmp;
            curIdx++;
        }
        return retVal;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!worldIn.isRemote) {
            long radiated = playerIn.getEntityData().getLong(ModMain.MODID + "_radiatedtime");
            double prc = radiated * 100 / ModMain.instance.maximumRadiation;
            int countDown = 0;
            long wt = worldIn.getTotalWorldTime();
            if(wt < ModMain.radiationDelay)countDown = (int)((ModMain.radiationDelay - wt) / 1000);
            TextComponentTranslation descTranslated;
            long thresholds[] = sort(ModMain.blindnessThreshold, ModMain.poisonThreshold, ModMain.hungerThreshold, ModMain.nauseaThreshold);
            if(radiated < thresholds[0]){
                if(countDown == 0)
                    descTranslated = new TextComponentTranslation("radlevel.safe.title", radiated, (int)prc);
                else
                    descTranslated = new TextComponentTranslation("radleveltb.safe.title", countDown, radiated, (int)prc);
            } else if(radiated < thresholds[1]) {
                if(countDown == 0)
                    descTranslated = new TextComponentTranslation("radlevel.caution.title", radiated, (int)prc);
                else
                    descTranslated = new TextComponentTranslation("radleveltb.caution.title", countDown, radiated, (int)prc);
            } else if(radiated < thresholds[2]) {
                if(countDown == 0)
                    descTranslated = new TextComponentTranslation("radlevel.warning.title", radiated, (int)prc);
                else
                    descTranslated = new TextComponentTranslation("radleveltb.warning.title", countDown, radiated, (int)prc);
            } else if(radiated < thresholds[3]){
                if(countDown == 0)
                    descTranslated = new TextComponentTranslation("radlevel.dangerous.title", radiated, (int)prc);
                else
                    descTranslated = new TextComponentTranslation("radleveltb.dangerous.title", countDown, radiated, (int)prc);
            } else {
                if(countDown == 0)
                    descTranslated = new TextComponentTranslation("radlevel.critical.title", radiated, (int)prc);
                else
                    descTranslated = new TextComponentTranslation("radleveltb.critical.title", countDown, radiated, (int)prc);
            }
            playerIn.sendStatusMessage(descTranslated, true);
            //worldIn.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, ModSounds.geigerSound, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, ModSounds.geigerSound, SoundCategory.PLAYERS, .5F, 1.0F);

        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
