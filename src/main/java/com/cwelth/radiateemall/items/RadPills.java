package com.cwelth.radiateemall.items;

import com.cwelth.radiateemall.ModMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class RadPills extends Item {
    public RadPills(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!worldIn.isRemote) {
            long radiated = playerIn.getEntityData().getLong(ModMain.MODID + "_radiatedtime");
            radiated -= 10 * ModMain.radiationStrength;
            playerIn.getEntityData().setLong(ModMain.MODID + "_radiatedtime", radiated);
        }
        playerIn.getHeldItem(handIn).shrink(1);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
