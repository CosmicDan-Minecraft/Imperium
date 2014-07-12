package com.cosmicdan.minecraftempires.datamanagement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

// this class is responsible for handling the world data reading/writing
public class WorldDataHandler extends WorldSavedData {
    
    private NBTTagCompound worldData = new NBTTagCompound();
    private String tag;
    
    public WorldDataHandler(String nbtFile) {
        super(nbtFile);
    }
    
    public void requestTag(String tag) {
        this.tag = tag;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        //System.out.println(">>> Read World Data");
        worldData = compound.getCompoundTag(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        //System.out.println(">>> Write World Data");
        compound.setTag(tag, worldData);
    }
    
    public NBTTagCompound getData() {
        return worldData;
    }
}
