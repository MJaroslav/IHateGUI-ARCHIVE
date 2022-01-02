package com.github.mjaroslav.ihategui.minecraft.forge1710;

import com.github.mjaroslav.ihategui.minecraft.forge1710.command.TestCommand;
import com.github.mjaroslav.ihategui.minecraft.forge1710.lib.ModInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(name = ModInfo.NAME, modid = ModInfo.MOD_ID, version = ModInfo.VERSION, acceptableRemoteVersions = "*")
public class IHateGUIMod {
    @Mod.EventHandler
    public void listen(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new TestCommand());
    }
}
