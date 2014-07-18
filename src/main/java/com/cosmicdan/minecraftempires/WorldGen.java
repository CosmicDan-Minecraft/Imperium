package com.cosmicdan.minecraftempires;

import java.util.Random;

import com.cosmicdan.minecraftempires.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {
    /*
     *  USER VARS
     *  These will be relocated to a mod config file later-on
     */
    // Brushwood abundance, try placing every n chunk (0 for never, 1 for every chunk, etc.) 
    public static int chunkAbundanceBrushwood = 1;
    // Brushwood abundance per chunk - try to place up to this much in every n chunk(s)
    public static int blockAbundanceBrushwood = 5;
    // Radius to scan for leaves (width/breadth). Might be unstable if too high?
    public static int scanRadiusBrushwood = 3;
    // Height to scan for leaves. Might be unstable if too high?
    public static int scanHeightBrushwood = 8;
    
    /*
     *  NON-USER VARS
     *  Don't expose these to the (future) config file, they're used internally
     */
    public static int chunkAbundanceBrushwoodRuntime = chunkAbundanceBrushwood;
    
    
    // world generation hook
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            // overworld
            case 0: genPrimalAge(world, random, chunkX*16, chunkZ*16);
        }
        
    }
    
    // world generation for Primal Age (starting age/dimension)
    private void genPrimalAge(World world, Random random, int chunkStartX, int chunkStartZ) {
        // decrement the chunk counter - when it reaches 0, this is a chunk that we'll generate in
        chunkAbundanceBrushwoodRuntime--;
        if (chunkAbundanceBrushwoodRuntime == 0) {
            // reset counter back to config value
            chunkAbundanceBrushwoodRuntime = chunkAbundanceBrushwood;
            // loop over this chunk 'blockAbundanceBrushwood' times
            for (int loop = 0; loop < blockAbundanceBrushwood; loop++) {
                // get a random block in this chunk and store for remaining logic
                int blockX = chunkStartX + random.nextInt(16);
                int blockZ = chunkStartZ + random.nextInt(16);
                // get the surface height of the random block
                int blockY = world.getTopSolidOrLiquidBlock(blockX, blockZ);
                // check if there are leaves somewhere around this randomly-selected surface block
                // TODO: I had a lot of instability with writing this, seems to be OK now for some reason. Possibly the scan ranges being too high caused it and it tried to scan a block outside the chunk/world?
                scanLoop:
                // scan 'scanRadiusBrushwood' on x axis surrounding this randomly-selected surface block... 
                for (int scanBlockX = (blockX - scanRadiusBrushwood); scanBlockX <= (blockX + scanRadiusBrushwood); scanBlockX++) {
                    // ...then scan 'scanRadiusBrushwood' on z axis surrounding this randomly-selected surface block...
                    for (int scanBlockZ = (blockZ - scanRadiusBrushwood); scanBlockZ <= (blockZ + scanRadiusBrushwood); scanBlockZ++) {
                        // ...and finally scan 'scanHeightBrushwood' upwards on y axis
                        for (int scanBlockY = blockY; scanBlockY <= (blockY + scanHeightBrushwood); scanBlockY++) {
                            // get the block we're currently scanning
                            Block thisBlock = world.getBlock(scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0);
                            // if the block is air, break (skip it and scan next block)
                            if (thisBlock.isAir(world, scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0)) break;
                            // check if the block is leaves
                            if (world.getBlock(scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0).getMaterial() == Material.leaves) {
                                // leaves found! Place the block at the original co-ords that were randomly selected
                                world.setBlock(blockX + 0, blockY + 0, blockZ + 0, ModBlocks.brushwood, 0, 0);
                                // abort the radius scan so we can move on to the next iteration for this chunk (or the next chunk if we've reached blockAbundanceBrushwood)
                                break scanLoop;
                            }
                        }
                    }
                }
            }
        }
    }
}