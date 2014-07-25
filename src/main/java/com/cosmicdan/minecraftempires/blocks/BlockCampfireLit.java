package com.cosmicdan.minecraftempires.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.cosmicdan.minecraftempires.client.renderers.ModRenderers;
import com.cosmicdan.minecraftempires.entities.tiles.TileEntityCampfire;
import com.cosmicdan.minecraftempires.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCampfireLit extends BlockContainer {
    
    private IIcon[] campfireIcon = new IIcon[6];
    private final Random random = new Random();
    
    public BlockCampfireLit() {
        super(Material.fire);
        setBlockName("CampfireLit");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setLightLevel(1.0F);
    }
    
    // eject tile entity inventory 
    @Override
    public void breakBlock(World world, int posX, int posY, int posZ, Block block, int metadata) {
        if (!world.isRemote && metadata > 0) {
            TileEntityCampfire tileEntity = (TileEntityCampfire) world.getTileEntity(posX, posY, posZ);
            ItemStack itemStack;
            EntityItem entityItem;
            float ranX;
            float ranY;
            float ranZ;
            for (int i = 0; i < tileEntity.itemSlotStatus.length; i++) {
                itemStack = tileEntity.itemSlot[i];
                if (itemStack != null) {
                    itemStack.stackSize = 1;
                    System.out.println("1");
                    ranX = this.random.nextFloat() * 0.8F + 0.1F;
                    ranY = this.random.nextFloat() * 0.8F + 0.1F;
                    ranZ = this.random.nextFloat() * 0.8F + 0.1F;
                    entityItem = new EntityItem(world, (double)((float)posX + ranX), (double)((float)posY + ranY), (double)((float)posZ + ranZ), itemStack);
                    entityItem.motionX = (double)((float)this.random.nextGaussian() * 0.05F);
                    entityItem.motionY = (double)((float)this.random.nextGaussian() * 0.05F);
                    entityItem.motionZ = (double)((float)this.random.nextGaussian() * 0.05F);
                    world.spawnEntityInWorld(entityItem);
                }
            }
        }
        super.breakBlock(world, posX, posY, posZ, block, metadata);
    }
    
    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        // don't want to drop the campfire itself
        return null;
    }
    
    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCampfire(world, metadata);
    }
    
    @Override
    public void onBlockAdded(World world, int posX, int posY, int posZ) {
        world.setBlockMetadataWithNotify(posX, posY, posZ, 1, 3);
    }
    
    @Override
    public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntityCampfire tileEntity = (TileEntityCampfire) world.getTileEntity(posX, posY, posZ);
        if (!world.isRemote) {
            if (player.getHeldItem() != null) {
                Item playerItem = player.getHeldItem().getItem();
                int blockMeta = world.getBlockMetadata(posX, posY, posZ);
                if (blockMeta > 0 ) {
                    // check for foods to add
                    Item item = player.getHeldItem().getItem();
                    if (item instanceof ItemFood) {
                        // check for cookable meats
                        ItemStack itemStack = player.getHeldItem();
                        if (tileEntity.tryAddItem(itemStack))
                            --itemStack.stackSize;
                        else // slots are all full, save them time and return an item instead
                            tryRemoveItem(world, player, tileEntity);
                    } else // not a valid input item, assume the player is trying to remove an item
                        tryRemoveItem(world, player, tileEntity);
                }
            } else // player hand is empty, try to remove an item for them
                tryRemoveItem(world, player, tileEntity);
        }
        return true;
    }
    
    private void tryRemoveItem(World world, EntityPlayer player, TileEntityCampfire tileEntity) {
        ItemStack itemStack = null;
        for (int i = 0; i < tileEntity.itemSlotStatus.length; i++) {
            itemStack = tileEntity.tryRemoveItem();
            if (itemStack != null) {
                EntityItem dropItem = new EntityItem(world, player.posX, player.posY, player.posZ, itemStack.copy());
                dropItem.delayBeforeCanPickup = 0;
                world.spawnEntityInWorld(dropItem);
                // shift-click = remove only one item
                if (player.isSneaking()) break;
            }
        }
    }
    

    @Override
    public int getLightValue(IBlockAccess world, int posX, int posY, int posZ) {
        return 15;
    }
   
    /*
     *  transparency stuff (so the blocks surrounding it don't go see-through)
     */
    public boolean renderAsNormalBlock() {
         return false;
    }
    
    public boolean isOpaqueCube() {
         return false;
    }
    
    public int getRenderType() {
        return ModRenderers.rendererCampfire;
    }
    
    /*
     * Icon stuff for textures
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int icon, int meta) {
        if (icon < 4) {
            if (meta != 2)
                return this.campfireIcon[icon];
            else if (icon < 2)
                return this.campfireIcon[icon + 4];
        }
        return this.campfireIcon[0];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegistry) {
        this.campfireIcon[0] = iconRegistry.registerIcon("minecraftempires:campfire_side0");
        this.campfireIcon[1] = iconRegistry.registerIcon("minecraftempires:campfire_side1");
        this.campfireIcon[2] = iconRegistry.registerIcon("minecraftempires:campfire_side2");
        this.campfireIcon[3] = iconRegistry.registerIcon("minecraftempires:campfire_side3");
        this.campfireIcon[4] = iconRegistry.registerIcon("minecraftempires:campfire_strut");
        this.campfireIcon[5] = iconRegistry.registerIcon("minecraftempires:campfire_spitrod");
    }
}
