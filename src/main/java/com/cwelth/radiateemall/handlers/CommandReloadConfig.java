package com.cwelth.radiateemall.handlers;

import com.cwelth.radiateemall.ModMain;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandReloadConfig extends CommandBase {
    private String usage = "rad_reloadconfig";

    @Override
    public String getName() {
        return "rad_reloadconfig";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ModMain.instance.loadConfig();
        sender.sendMessage(new TextComponentTranslation("command.reloadconfig"));
    }
}
