package com.cosmicdan.minecraftempires.blocks;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCampfire extends Block {
    
    private IIcon[] campfireIcon = new IIcon[4];
    
    public BlockCampfire() {
        super(Material.vine);
        setBlockName("Campfire");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return ModItems.brushwood;
    }
    
    @Override
    public int quantityDropped(Random random) {
        return 2;
    }
    
    @Override
    public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!world.isRemote) {
            if (player.getHeldItem() == null)
                return true;
            Item playerItem = player.getHeldItem().getItem();
            if (player.getHeldItem().getItem() == Items.stick) {
                //world.setBlockMetadataWithNotify(posX, posY, posZ, 1, 3);
                world.setBlockToAir(posX, posY, posZ);
                world.setBlock(posX, posY, posZ, ModBlocks.campfireLit, 1, 3);
            }
        }
        //world.setBlockMetadataWithNotify(posX, posY, posZ, 1, 3);
        return true;
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
    public IIcon getIcon(int side, int meta) {
        if (side < 4)
            return this.campfireIcon[side];
        else
            return this.campfireIcon[0];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegistry) {
        this.campfireIcon[0] = iconRegistry.registerIcon("minecraftempires:campfire_side0");
        this.campfireIcon[1] = iconRegistry.registerIcon("minecraftempires:campfire_side1");
        this.campfireIcon[2] = iconRegistry.registerIcon("minecraftempires:campfire_side2");
        this.campfireIcon[3] = iconRegistry.registerIcon("minecraftempires:campfire_side3");
    }
}
