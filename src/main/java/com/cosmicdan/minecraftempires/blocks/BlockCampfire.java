package com.cosmicdan.minecraftempires.blocks;

import java.util.Random;

import com.cosmicdan.minecraftempires.client.renderers.ModRenderers;
import com.cosmicdan.minecraftempires.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;

public class BlockCampfire extends Block {
    
    private IIcon[] campfireIcon = new IIcon[4];
    
    public BlockCampfire() {
        super(Material.ground);
        setBlockName("Campfire");
        // x, y, z offset; x, y, z size
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    // transparency stuff (so the blocks surrounding it don't go see-through) 
    public boolean isOpaqueCube() {
         return false;
    }
    
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return ModItems.brushwood;
    }
    
    @Override
    public int quantityDropped(Random random) {
        return 2;
    }
    
    // transparency stuff (so the blocks surrounding it don't go see-through) 
    public boolean renderAsNormalBlock() {
         return false;
    }
    
    public int getRenderType() {
        return ModRenderers.rendererCampfire;
    }
    

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
