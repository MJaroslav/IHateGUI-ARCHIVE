package com.github.mjaroslav.ihategui.minecraft.forge1710.command;

import com.github.mjaroslav.ihategui.minecraft.forge1710.lib.ModInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class TestCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return ModInfo.MOD_ID;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ModInfo.MOD_ID;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

    }
}
