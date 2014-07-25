package com.cosmicdan.minecraftempires.entities.tiles;

import java.util.Iterator;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/*
 * TODO: I plan to do more of these "non-GUI machines" , best to move it into a base 
 * class and extend that when I do make another one.
 */
public class TileEntityCampfire extends TileEntity {
    // lifetime of a lit campfire, in minutes
    public double entityLifetime = 5;
    // how long it'll take for food to cook, in seconds
    public int itemProcessTime = 60;
    
    // some of these are public only because I don't see the point in "getter" methods
    public int metadata; // 1 = not cooking, 2 = cooking/cooked
    public ItemStack itemSlot[] = new ItemStack[4];
    public int itemSlotStatus[] = new int[4]; // 0 = slot empty, 1 = slot processing, 2 = slot processed
    private int itemSlotRemaining[] = new int[4]; // how long until the slot is finished processing (always 0 if slot status != 1)
    
    private boolean needUpdate = false;
    
    private int entityLifeCurrent = 0;
    private int entityLifeMax = (int) (entityLifetime * 60 * 20);
    private int itemProcessMax = itemProcessTime * 20;
    
    public TileEntityCampfire() {}
    
    public TileEntityCampfire(World world, int metadata) {
        this.setWorldObj(world);
        this.metadata = metadata;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound entityData) {
        super.writeToNBT(entityData);
        entityData.setInteger("metadata", metadata);
        String itemSlotDo;
        for (int i = 0; i < itemSlot.length; i++) {
            if (itemSlot[i] == null) {
                itemSlotDo = "none";
            } else {
                if (itemSlot[i].getItem() == null) {
                    itemSlotDo = "none";
                } else {
                    itemSlotDo = Item.itemRegistry.getNameForObject(itemSlot[i].getItem());
                }
            }
            entityData.setString("itemSlot" + Integer.toString(i), itemSlotDo);
        }
        entityData.setIntArray("itemSlotStatus", itemSlotStatus);
        entityData.setIntArray("itemSlotRemaining", itemSlotRemaining);
        entityData.setInteger("lifetime", entityLifeCurrent);
    }

    @Override
    public void readFromNBT(NBTTagCompound entityData) {
        super.readFromNBT(entityData);
        this.metadata = entityData.getInteger("metadata");
        String itemSlotDo;
        for (int i = 0; i < itemSlot.length; i++) {
            itemSlotDo = entityData.getString("itemSlot" + Integer.toString(i));
            if (itemSlotDo != "none")
                this.itemSlot[i] = new ItemStack((Item)Item.itemRegistry.getObject(itemSlotDo));
            else
                this.itemSlot[i] = null;
        }
        this.itemSlotStatus = entityData.getIntArray("itemSlotStatus");
        this.itemSlotRemaining = entityData.getIntArray("itemSlotRemaining");
        this.entityLifeCurrent = entityData.getInteger("lifetime");
        needUpdate = true;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
    
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.func_148857_g();
        readFromNBT(tag);
    }
    
    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;
        entityLifeCurrent += 1;
        if (entityLifeCurrent >= entityLifeMax) {
            // Campfire's life is over
            if (this.hasWorldObj()) {
                selfDestruct();
            }
        }
        // tick cooking stuff
        for (int i = 0; i < itemSlotStatus.length; i++) {
            if (itemSlotStatus[i] == 1) {
                --itemSlotRemaining[i]; // decrement timer
                if (itemSlotRemaining[i] <= 0) {
                    // item cooked!
                    itemSlotRemaining[i] = 0;
                    itemSlotStatus[i] = 2;
                    ItemStack itemStack = itemSlot[i];
                    itemSlot[i] = FurnaceRecipes.smelting().getSmeltingResult(itemStack.copy());
                    needUpdate = true;
                }
            }
        }
        
        if (needUpdate) { // update the metadata for the renderer logic
            this.metadata = 1; // default, in case no cooking/cooked item was found
            for (int i = 0; i < itemSlotStatus.length; i++) {
                if (itemSlotStatus[i] > 0) { // something is cooking/cooked, meta should be 2
                    this.metadata = 2;
                }
            }
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, metadata, 3);
            this.markDirty();
            needUpdate = false;
        }
        
    }
    
    public boolean tryAddItem(ItemStack itemStack) {
        if (FurnaceRecipes.smelting().getSmeltingResult(itemStack) == null) // invalid cookable, return false
            return false;
        for (int i = 0; i < itemSlotStatus.length; i++) {
            if (itemSlotStatus[i] == 0) {
                addItem(itemStack.copy(), i);
                needUpdate=true;
                return true;
            }
        }
        // all slots full, return false
        return false;
    }
    
    private void addItem(ItemStack itemStack, int slot) {
        itemSlot[slot] = itemStack;
        itemSlotStatus[slot] = 1;
        itemSlotRemaining[slot] = itemProcessMax;
    }
    
    public ItemStack tryRemoveItem() {
        for (int i = 0; i < itemSlotStatus.length; i++) {
            if (itemSlotStatus[i] == 2) {
                ItemStack retval = removeItem(i);
                needUpdate=true;
                return retval;
            }
        }
        // nothing to remove, return null
        return null;
        
    }
    
    private ItemStack removeItem(int slot) {
        ItemStack retval = itemSlot[slot]; 
        itemSlot[slot] = null;
        itemSlotStatus[slot] = 0;
        itemSlotRemaining[slot] = 0;
        return retval;
    }
    
    private void selfDestruct() {
        this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
        this.invalidate();
        // func_147480_a = destroyBlock(x,y,z,doDrop)
        this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
    }
 }