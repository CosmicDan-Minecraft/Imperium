package com.cosmicdan.minecraftempires.eventhandlers;

import com.cosmicdan.minecraftempires.datamanagement.WorldData;
import com.cosmicdan.minecraftempires.datamanagement.WorldDataHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
    
    private WorldData worldData;
    //private NBTTagCompound worldDataGlobal;
    
    public WorldEvents() {
        
    }
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.world;
        // ensure it's the server
        if (!world.isRemote) {
            // ensure it's the overworld for global data
            if (world.provider.dimensionId == 0) {
                // write some initial values where required
                worldData = new WorldData(); 
                worldData.loadData(world, "MinecraftEmpires", "global");
                if (!worldData.getData().hasKey("worldDay")) {
                    writeInitialSettings(world, worldData, "global");
                } else {
                    loadSettings(world, worldData, "global");
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        World world = event.world;
        // ensure it's the server
        if (!world.isRemote) {
            // ensure it's the overworld for global data
            if (world.provider.dimensionId == 0) {
                //System.out.println("Saving data");
                worldData.getData().setLong("lastSave", world.getTotalWorldTime());
                worldData.getData().setInteger("worldDay", WorldData.worldDay);
                //worldData.commit();
            }
        }
    }
    
    private void writeInitialSettings(World world, WorldData worldData, String tag) {
        if (tag == "global") {
            WorldData.worldDay = 1;
        }
    }
    
    private void loadSettings(World world, WorldData worldData, String tag) {
        if (tag == "global") {
            WorldData.worldDay = worldData.getData().getInteger("worldDay");
        }
    }
}
