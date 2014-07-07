package com.cosmicdan.minecraftempires;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public interface CommonProxy {
    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);
    
    void openJournal();
}