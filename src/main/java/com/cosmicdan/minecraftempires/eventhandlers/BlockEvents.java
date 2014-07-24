package com.cosmicdan.minecraftempires.eventhandlers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

// this class is responsible for individual block events
public class BlockEvents {
    
    /*
     *  USER VARS
     *  These will be relocated to a mod config file later-on
     */
    // Chance of a broken leaf block to generate a Brushwood block underneath it (as percentage)
    int leafBrushwoodChance = 33;
    
    // Events for when a block is broken
    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {
        // check if the block broken was a leaf block
        if (event.block.getMaterial() == Material.leaves) {
            // get a random number instance
            Random random = new Random();
            // calculate the percentage and continue if fortunate. Note that random.nextInt will return an int from 0-99
            if ((random.nextInt(100)) < leafBrushwoodChance) {
                // get the height of surface from where the leaf was destroyed
                int y = event.world.getTopSolidOrLiquidBlock(event.x, event.z);
                // get the surface block of where the leaf was destroyed as 'spawnCandidate' 
                Block spawnCandidate = event.world.getBlock(event.x, y, event.z);
                // ensure that spawnCandidate is (a) an air block and (b) below the leaf block that was destroyed
                if (spawnCandidate.isAir(event.world, event.x, y, event.z) && y < event.y) {
                    // spawnCandidate is valid, place Brushwood block!
                    event.world.setBlock(event.x, y, event.z, ModBlocks.brushwood);
                }
            }
        }
    }
    
    /*
     * Note to self: This also hooks leaf decay!
     */
    @SubscribeEvent
    public void onBlockHarvest(HarvestDropsEvent event) {
        
    }

}
