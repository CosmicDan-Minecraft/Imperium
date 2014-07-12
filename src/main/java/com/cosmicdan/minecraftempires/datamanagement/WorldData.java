package com.cosmicdan.minecraftempires.datamanagement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WorldData {
    
    private WorldDataHandler worldDataFile; 
    public static int worldDay;
    
    public WorldData() {
        
    }
    
    public void loadData(World world, String nbtFile, String tag) {
        if (worldDataFile == null) {
            worldDataFile = (WorldDataHandler) world.mapStorage.loadData(WorldDataHandler.class, nbtFile);
            if (worldDataFile == null) {
                worldDataFile = new WorldDataHandler(nbtFile);
                worldDataFile.requestTag(tag);
                world.mapStorage.setData(nbtFile, worldDataFile);
            } //else {
            //    NBTTagCompound compound = worldDataFile.getData();
            //}
        }
    }
    
    public NBTTagCompound getData() {
        return worldDataFile.getData();
    }
    
    public void commit() {
        worldDataFile.markDirty();
    }
}
