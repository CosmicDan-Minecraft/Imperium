package com.cosmicdan.minecraftempires;

import com.cosmicdan.minecraftempires.eventhandlers.BlockEvents;
import com.cosmicdan.minecraftempires.eventhandlers.WorldTickEvents;
import com.cosmicdan.minecraftempires.eventhandlers.WorldEvents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.WorldEvent;

public class CommonProxy {
    
    public void preInit(FMLPreInitializationEvent event) {
        
    }
    
    
    public void init(FMLInitializationEvent event) {
        
    }
    
    
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
        // register the World tick events class
        FMLCommonHandler.instance().bus().register(new WorldTickEvents());
    }
    
    
    public void openJournal() { }
}