package com.cosmicdan.minecraftempires.entities.tiles;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cosmicdan.minecraftempires.blocks.ModBlocks;

/*
 * TODO: I plan to do more of these "non-GUI machines" , best to move it into a base 
 * class and extend that when I do make another one.
 */
public class TileEntityCampfire extends TileEntity {
    
    /** Lifetime of a lit campfire, in minutes. */
    public double entityLifetime = 1;
    
    /** How long it'll take for food to cook, in seconds. */
    public int itemProcessTime = 40;
    
    /** Metadata, used for internal processing of pigs.
     * if cooking: default meta + 6.
     */
    public int metadata;
    
    /** Slots with items. */
    public ItemStack itemSlot[] = new ItemStack[4];

    /** 0 = empty, 1 = processing, 2 = processed. */
    public int itemSlotStatus[] = new int[4];
    
    /** How long until processed, always 0 if {@link itemSlotStatus} != 1. */
    private int itemSlotRemaining[] = new int[4];
    
    /** Indicates that the tile entity has to be updated. */
    public boolean needUpdate = false;
    
    /** Initial lifetime of the fire (incremented every tick). */
    private int entityLifeCurrent = 0;
    
    /** Used to set the max lifetime initial value. */
    private int entityLifetimeMax = (int) (entityLifetime * 60 * 20);
    
    /** Used to set the food processing timer's initial value. */
    private int itemProcessMax = itemProcessTime * 20;
    
    /** Dummy constructor. */
    public TileEntityCampfire() {}
    
    /**
     * @param world WorldObj that the tileentity will exist in.
     * @param metadata Initial metadata of the tileentity. 
     */
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
        
        if(entityLifeCurrent >= entityLifetimeMax)
        {
            entityLifeCurrent = 0;
            System.out.println(metadata + " :: " + entityLifetime);
            if(entityLifetime != 0)
            {
                if(metadata == 1)
                {
                    // Ember mode derp ;D
                    entityLifetime-=1;
                    metadata-=1;
                    needUpdate = true;
                } else
                {
                metadata-=1;
                entityLifetime-=1;
                needUpdate = true;
                }
            } else
                this.worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.campfire, 0, 3);
        }
        // tick cooking stuff
        for (int i = 0; i < itemSlotStatus.length; i++) {
            if (itemSlotStatus[i] == 1 && entityLifetime > 1) {
                System.out.println("I'm on fire!");
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
            // this.metadata = 1; // default, in case no cooking/cooked item was found
            if(this.metadata > 6)
                this.metadata = this.metadata - 6;
            for (int i = 0; i < itemSlotStatus.length; i++) {
                if (itemSlotStatus[i] > 0) { // something is cooking/cooked, meta should be 2
                    this.metadata = this.metadata + 6;
                    break;
                }
            }
            System.out.println(metadata);
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, metadata, 3);
            this.markDirty();
            needUpdate = false;
        }
        
    }
    
    /** Tries to add an item to process.
     @param itemStack the {@link ItemStack} to add.
     @return true if successful, false otherwise. */
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
    
    /** Adds the item to {@link itemSlot} and sets the status of {@link itemSlotStatus} and {@link itemSlotRemaining}.
     @param itemStack the {@link ItemStack} to add to the specified slot.
     @param slot the slot to add the {@link ItemStack} to. */
    private void addItem(ItemStack itemStack, int slot) {
        itemSlot[slot] = itemStack;
        itemSlotStatus[slot] = 1;
        itemSlotRemaining[slot] = itemProcessMax;
    }
    
    /** Tries to remove processed items.
     * @return {@link ItemStack} if successful, {@code null} otherwise. */
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
    
    /** Removes the item in the specified slot.
     @param slot the slot to remove the item from. */
    private ItemStack removeItem(int slot) {
        ItemStack retval = itemSlot[slot]; 
        itemSlot[slot] = null;
        itemSlotStatus[slot] = 0;
        itemSlotRemaining[slot] = 0;
        return retval;
    }
    
    /** Tries to add fuel to the campfire.
     @return true if successful, false otherwise. */
    public boolean addFuel()
    {
        if(metadata < 6 || metadata > 6 && metadata < 12)
        {
            entityLifetime+=1;
            metadata+=1;
            needUpdate = true;
            return true;
        }
        return false;
    }
 }
