package com.cosmicdan.minecraftempires.eventhandlers;

import com.cosmicdan.minecraftempires.medata.world.WorldData;
import com.cosmicdan.minecraftempires.medata.world.WorldDataHandler;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
    
    private static WorldData worldData;
    private static NBTTagCompound worldDataGlobal;
    
    public WorldEvents() {
        
    }
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.world;
        // ensure it's the server
        if (!world.isRemote) {
            // ensure it's the overworld for global data
            if (world.provider.dimensionId == 0) {
                worldData = new WorldData(); 
                worldData.loadData(world, "MinecraftEmpires");
                worldDataGlobal = worldData.getData();
                if (worldDataGlobal.getLong("lastSave") == 0L) {
                    doInitialSettings(world, "global");
                } else {
                    loadSettings(world, "global");
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        World world = event.world;
        // ensure it's the server
        if (!world.isRemote) {
            if (world.provider.dimensionId == 0) {
                saveSettings(world, "global");
            }
        }
    }
    
    private void doInitialSettings(World world, String tag) {
        if (tag == "global") {
            WorldData.worldDay = 1;
        }
    }
    
    private void loadSettings(World world, String tag) {
        if (tag == "global") {
            WorldData.worldDay = worldDataGlobal.getInteger("worldDay");
        }
    }
    
    public static void saveSettings(World world, String tag) {
        if (tag == "global") {
            worldDataGlobal.setLong("lastSave", world.getTotalWorldTime());
            worldDataGlobal.setInteger("worldDay", WorldData.worldDay);
            worldData.commitAll();
        }
    }
}
