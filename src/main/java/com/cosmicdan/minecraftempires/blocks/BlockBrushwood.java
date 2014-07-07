package com.cosmicdan.minecraftempires.blocks;

import java.util.Random;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
//import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrushwood extends Block
{
        public BlockBrushwood() {
            super(Material.grass);
            setBlockName("Brushwood");
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
            setBlockTextureName("minecraftempires:brushwood");
            setTickRandomly(true);
        }
        
        public int tickRate() {
            return 200;
         }
        
        @Override
        public void updateTick(World world, int x, int y, int z, Random random) {
            //this.growCropsNearby(world, x, y, z);
            //net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new net.minecraft.util.ChatComponentText("Tick-tock!"));
            // TODO - Have Brushwood randomly regenerate, and/or have brushwood randomly spawn when leaves decay
        }
        
        //If the block's drop is an item.
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return Main.itemBrushwood;
        }
        
        // transparency stuff
        public boolean isOpaqueCube() {
             return false;
        }
        
        public boolean renderAsNormalBlock() {
             return false;
        }
}