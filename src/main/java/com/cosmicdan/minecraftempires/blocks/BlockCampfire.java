package com.cosmicdan.minecraftempires.blocks;

import java.util.Random;

import com.cosmicdan.minecraftempires.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockCampfire extends Block {
    
    public BlockCampfire() {
        super(Material.ground);
        setBlockName("Campfire");
        // x, y, z offset; x, y, z size
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        // set graphic
        setBlockTextureName("minecraftempires:brushwood");
        //setTickRandomly(true);
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
}
