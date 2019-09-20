package com.cwelth.radiateemall.handlers;

import com.cwelth.radiateemall.ModMain;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandSetRadiation extends CommandBase {
    private String usage = "rad_setradiation <amount> [player]";

    @Override
    public String getName() {
        return "rad_setradiation";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0 || args.length > 2) throw new WrongUsageException(usage, new Object[0]);
        String playerName = "";
        int radLevel = 0;
        if(args.length == 1)
        {
            if(sender instanceof EntityPlayerMP) {
                ((EntityPlayerMP)sender).getEntityData().setLong(ModMain.MODID + "_radiatedtime", Integer.valueOf(args[0]));
                playerName = ((EntityPlayerMP)sender).getName();
                radLevel = Integer.valueOf(args[0]);
            } else
                throw new WrongUsageException("Sender is not a player", new Object[0]);
        }
        if(args.length == 2)
        {
            EntityPlayerMP entityPlayer = getPlayer(server, sender, args[0]);
            if(entityPlayer != null)
            {
                entityPlayer.getEntityData().setLong(ModMain.MODID + "_radiatedtime", Integer.valueOf(args[1]));
                playerName = entityPlayer.getName();
                radLevel = Integer.valueOf(args[1]);
            } else
                throw new WrongUsageException("No such player", new Object[0]);
        }
        sender.sendMessage(new TextComponentTranslation("command.setradiation", playerName, radLevel));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 0 ? Collections.<String>emptyList() : getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }
}
