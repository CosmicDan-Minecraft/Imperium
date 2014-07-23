package com.cosmicdan.minecraftempires.entities.tiles;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class TileEntityCampfire extends TileEntity {
    
    // lifetime of a lit campfire, in minutes
    public double entityMaxLife = 5;
    
    private int metadata;
    private boolean isCooking;
    private int entityLifeCurrent = 0;
    private int entityLifeMax = (int) (entityMaxLife * 60 * 20); 
    
    public TileEntityCampfire() {}
    
    public TileEntityCampfire(World world, int metadata) {
        this.setWorldObj(world);
        this.metadata = metadata;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound entityData) {
        super.writeToNBT(entityData);
        entityData.setBoolean("isCooking", isCooking);
    }

    @Override
    public void readFromNBT(NBTTagCompound entityData) {
        super.readFromNBT(entityData);
        this.isCooking = entityData.getBoolean("isCooking");
        if (this.hasWorldObj()) {
            if (metadata > 0) {
                // nothing yet
            }
        }
    }
    
    @Override
    public void updateEntity() {
        entityLifeCurrent += 1;
        if (entityLifeCurrent >= entityLifeMax) {
            // Campfire's life is over
            if (this.hasWorldObj()) {
                selfDestruct();
            }
        }
    }
    
    private void selfDestruct() {
        this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
        this.invalidate();
        // func_147480_a = destroyBlock(x,y,z,doDrop)
        this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
    }
 }