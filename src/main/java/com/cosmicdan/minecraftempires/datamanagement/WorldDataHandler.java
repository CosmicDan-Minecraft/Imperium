package com.cosmicdan.minecraftempires.datamanagement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

// this class is responsible for handling the world data reading/writing
public class WorldDataHandler extends WorldSavedData {
    
    private NBTTagCompound worldData = new NBTTagCompound();
    
    public WorldDataHandler(String nbtFile) {
        super(nbtFile);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        worldData = compound.getCompoundTag("global");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setTag("global", worldData);
    }
    
    public NBTTagCompound getData() {
        return worldData;
    }
}
