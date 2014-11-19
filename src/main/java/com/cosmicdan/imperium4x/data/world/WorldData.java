package com.cosmicdan.imperium4x.data.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WorldData {
    
    private WorldDataHandler worldDataFile; 
    public static int worldDay;
    
    public WorldData() {
        
    }
    
    public void loadData(World world, String nbtFile) {
        if (worldDataFile == null) {
            worldDataFile = (WorldDataHandler) world.mapStorage.loadData(WorldDataHandler.class, nbtFile);
            if (worldDataFile == null) {
                worldDataFile = new WorldDataHandler(nbtFile);
                world.mapStorage.setData(nbtFile, worldDataFile);
            }
        }
    }
    
    public NBTTagCompound getData() {
        return worldDataFile.getData();
    }
    
    public void commitAll() {
        worldDataFile.markDirty();
    }
}
