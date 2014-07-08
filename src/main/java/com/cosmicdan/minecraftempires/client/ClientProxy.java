package com.cosmicdan.minecraftempires.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

import com.cosmicdan.minecraftempires.CommonProxy;
import com.cosmicdan.minecraftempires.client.gui.GuiJournalBook;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy implements CommonProxy {
    
    @Override
    public void init(FMLInitializationEvent event) {
        // nothing to see here
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) { }
    
    @Override
    public void openJournal() {
        // client-only hook command for when user presses right-click on their Journal (called from the Journal item class)
        Minecraft.getMinecraft().displayGuiScreen(new GuiJournalBook());
    }
}