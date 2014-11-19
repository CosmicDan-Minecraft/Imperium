package com.cosmicdan.imperium4x.blocks;

import java.util.Random;

import com.cosmicdan.imperium4x.Main;
import com.cosmicdan.imperium4x.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
//import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrushwood extends Block {
        public BlockBrushwood() {
            // we want this to behave like grass - will look green on maps, will burn in fire, etc.
            super(Material.grass);
            // remember to set the real name in language file
            setBlockName("Brushwood");
            // x, y, z offset; x, y, z size
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F);
            // set graphic
            setBlockTextureName("imperium4x:brushwood");
            // setup the hook for 'updateTick'. I think this should be removed because I have tickRate (or I should remove tickRate?)
            //setTickRandomly(true);
        }
        
        // How many ticks to wait before running 'updateTick' for every instance of this block 
        //public int tickRate() {
        //    return 200;
        //}
        
        //@Override
        //public void updateTick(World world, int x, int y, int z, Random random) {
            // TODO - Have Brushwood randomly replenish as long as there are others nearby. *Maybe*, not sure if I want to do this.
        //}
        
        // This block drops it's item equivalent
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return ModItems.brushwood;
        }
        
        // transparency stuff (so the blocks surrounding it don't go see-through) 
        public boolean isOpaqueCube() {
             return false;
        }
        
        // transparency stuff (so the blocks surrounding it don't go see-through) 
        public boolean renderAsNormalBlock() {
             return false;
        }
}