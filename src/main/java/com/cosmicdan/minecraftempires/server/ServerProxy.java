package com.cosmicdan.minecraftempires.server;

import net.minecraftforge.client.MinecraftForgeClient;

import com.cosmicdan.minecraftempires.CommonProxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ServerProxy implements CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) { 
        //NBTTagCompound dataServer = event.
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) { }
    
    @Override
    public void openJournal() { }
}