package com.cosmicdan.minecraftempires.blocks;

import java.util.Iterator;
import java.util.Random;

import com.cosmicdan.minecraftempires.client.renderers.ModRenderers;
import com.cosmicdan.minecraftempires.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockStickShelter extends BlockDirectional {
    //private static final String __OBFID = "CL_00000198";
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconEnd;
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconSide;
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconTop;

    // I have no idea *what* this is but it's something to do with alignment/orientation or some such
    public static final int[][] blockAlignmentData = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};

    public BlockStickShelter() {
        super(Material.cloth);
        setBounds();
        disableStats();
        setBlockName("StickShelter");
        setBlockTextureName("minecraftempires:stickshelter");
    }
    
    @Override
    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
        return this == ModBlocks.stickshelter;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int blockX, int blockY, int blockZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            int blockMetadata = world.getBlockMetadata(blockX, blockY, blockZ);
            if (!isBlockHeadOfBed(blockMetadata)) {
                int blockDirection = getDirection(blockMetadata);
                blockX += blockAlignmentData[blockDirection][0];
                blockZ += blockAlignmentData[blockDirection][1];

                if (world.getBlock(blockX, blockY, blockZ) != this) {
                    return true;
                }
                blockMetadata = world.getBlockMetadata(blockX, blockY, blockZ);
            }

            if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(blockX, blockZ) != BiomeGenBase.hell)
            {
                if (func_149976_c(blockMetadata)) {
                    EntityPlayer entityplayer1 = null;
                    Iterator iterator = world.playerEntities.iterator();

                    while (iterator.hasNext()) {
                        EntityPlayer entityplayer2 = (EntityPlayer)iterator.next();

                        if (entityplayer2.isPlayerSleeping()) {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if (chunkcoordinates.posX == blockX && chunkcoordinates.posY == blockY && chunkcoordinates.posZ == blockZ) {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if (entityplayer1 != null) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                        return true;
                    }

                    BlockBed.func_149979_a(world, blockX, blockY, blockZ, false);
                }

                EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(blockX, blockY, blockZ);

                if (enumstatus == EntityPlayer.EnumStatus.OK) {
                    BlockBed.func_149979_a(world, blockX, blockY, blockZ, true);
                    return true;
                } else {
                    if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
                    }
                    else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
                    }

                    return true;
                }
            } else {
                double d2 = (double)blockX + 0.5D;
                double d0 = (double)blockY + 0.5D;
                double d1 = (double)blockZ + 0.5D;
                world.setBlockToAir(blockX, blockY, blockZ);
                int k1 = getDirection(blockMetadata);
                blockX += blockAlignmentData[k1][0];
                blockZ += blockAlignmentData[k1][1];

                if (world.getBlock(blockX, blockY, blockZ) == this)
                {
                    world.setBlockToAir(blockX, blockY, blockZ);
                    d2 = (d2 + (double)blockX + 0.5D) / 2.0D;
                    d0 = (d0 + (double)blockY + 0.5D) / 2.0D;
                    d1 = (d1 + (double)blockZ + 0.5D) / 2.0D;
                }

                world.newExplosion((Entity)null, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), 5.0F, true, true);
                return true;
            }
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side == 0) {
            return Blocks.planks.getBlockTextureFromSide(side);
        } else {
            int direction = getDirection(meta);
            int bedDirection = Direction.bedDirection[direction][side];
            int isHead = isBlockHeadOfBed(meta) ? 1 : 0;
            return (isHead != 1 || bedDirection != 2) && (isHead != 0 || bedDirection != 3) ? (bedDirection != 5 && bedDirection != 4 ? this.bedIconTop[isHead] : this.bedIconSide[isHead]) : this.bedIconEnd[isHead];
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.bedIconTop = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_top"), p_149651_1_.registerIcon(this.getTextureName() + "_head_top")};
        this.bedIconEnd = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_end"), p_149651_1_.registerIcon(this.getTextureName() + "_head_end")};
        this.bedIconSide = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_side"), p_149651_1_.registerIcon(this.getTextureName() + "_head_side")};
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType() {
        return ModRenderers.rendererStickShelter;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.setBounds();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        int l = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        int i1 = getDirection(l);

        if (isBlockHeadOfBed(l)) {
            if (p_149695_1_.getBlock(p_149695_2_ - blockAlignmentData[i1][0], p_149695_3_, p_149695_4_ - blockAlignmentData[i1][1]) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        } else if (p_149695_1_.getBlock(p_149695_2_ + blockAlignmentData[i1][0], p_149695_3_, p_149695_4_ + blockAlignmentData[i1][1]) != this) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);

            if (!p_149695_1_.isRemote) {
                //this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, l, 0);
            }
        }
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        /**
         * Returns whether or not this bed block is the head of the bed.
         */
        return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : ModItems.stickshelter;
    }

    private void setBounds() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    }

    /**
     * Returns whether or not this bed block is the head of the bed.
     */
    public static boolean isBlockHeadOfBed(int p_149975_0_) {
        return (p_149975_0_ & 8) != 0;
    }

    public static boolean func_149976_c(int p_149976_0_) {
        return (p_149976_0_ & 4) != 0;
    }

    /**
     * Called when a user either starts or stops sleeping in the bed.
     *  
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param player The player or camera entity, null in some cases.
     * @param occupied True if we are occupying the bed, or false if they are stopping use of the bed
     */
    @Override
    public void setBedOccupied(IBlockAccess world, int x, int y, int z, EntityPlayer player, boolean occupied) {
        if (world instanceof World) {
            BlockBed.func_149979_a((World)world,  x, y, z, occupied);
            if (!occupied) {
                // Player woke up, collapse the stick shelter
                ((World) world).setBlockToAir(x, y, z);
                // TODO: quest generation
            }
        }
    }
    
    /**
     * Returns the position that the player is moved to upon 
     * waking up, or respawning at the bed.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param player The player or camera entity, null in some cases.
     * @return The spawn position
     * 
     */
    @Override
    public ChunkCoordinates getBedSpawnPosition(IBlockAccess world, int x, int y, int z, EntityPlayer player) {
        if (world instanceof World)
            return BlockBed.func_149977_a((World)world, x, y, z, 0);
        return null;
    }

    /**
     * Returns the direction of the block. Same values that
     * are returned by BlockDirectional
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return Bed direction
     */
    @Override
    public int getBedDirection(IBlockAccess world, int x, int y, int z) {
        return BlockBed.getDirection(world.getBlockMetadata(x,  y, z));
    }

    /**
     * Determines if the current block is the foot half of the bed.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return True if the current block is the foot side of a bed.
     */
    @Override
    public boolean isBedFoot(IBlockAccess world, int x, int y, int z) {
        return BlockBed.isBlockHeadOfBed(world.getBlockMetadata(x,  y, z));
    }
    
    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    /*
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!isBlockHeadOfBed(p_149690_5_)) {
            super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        }
    }
    */

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag() {
        return 1;
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return ModItems.stickshelter;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode && isBlockHeadOfBed(p_149681_5_)) {
            int i1 = getDirection(p_149681_5_);
            p_149681_2_ -= blockAlignmentData[i1][0];
            p_149681_4_ -= blockAlignmentData[i1][1];

            if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_, p_149681_4_) == this) {
                p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_, p_149681_4_);
            }
        }
    }
}
