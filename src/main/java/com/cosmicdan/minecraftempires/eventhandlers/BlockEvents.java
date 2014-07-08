package com.cosmicdan.minecraftempires.eventhandlers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.cosmicdan.minecraftempires.Main;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockEvents {
    
    // Chance of a manually destroyed leaf to generate a Brushwood block (percentage)
    int leafBrushwoodChance = 33;
    
    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {
        if (event.block.getMaterial() == Material.leaves) {
            Random random = new Random();
            if ((random.nextInt(100)) < leafBrushwoodChance) {
                //EntityItem i = new EntityItem(e.world, e.x, e.y, e.z, new ItemStack(Items.stick));
                //e.world.spawnEntityInWorld(i);
                int y = event.world.getTopSolidOrLiquidBlock(event.x, event.z);
                Block spawnCandidate = event.world.getBlock(event.x, y, event.z);
                if (spawnCandidate.isAir(event.world, event.x, y, event.z) && y < event.y) {
                    //event.world.setBlock(event.x, y, event.z, Main.blockBrushwood, 0, 0);
                    event.world.setBlock(event.x, y, event.z, Main.blockBrushwood);
                }
            }
        }
    }

}
