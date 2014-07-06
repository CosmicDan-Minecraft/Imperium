package com.cosmicdan.minecraftempires;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {
    // Brushwood abundance, try placing every n chunk (0 for never, 1 for every chunk, etc.) 
    public static int chunkAbundanceBrushwood = 1;
    public static int chunkAbundanceBrushwoodRuntime = chunkAbundanceBrushwood;
    // Brushwood abundance per chunk - try to place up to this much in every n chunk(s)
    public static int blockAbundanceBrushwood = 5;
    // Radius to scan for leaves (width/breadth). Might be unstable if too high?
    public static int scanRadiusBrushwood = 3;
    // Height to scan for leaves. Might be unstable if too high?
    public static int scanHeightBrushwood = 8;
    
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            // overworld
            case 0: genPrimalAge(world, random, chunkX*16, chunkZ*16);
        }
        
    }
    
    
    private void genPrimalAge(World world, Random random, int chunkStartX, int chunkStartZ) {
        chunkAbundanceBrushwoodRuntime--;
        if (chunkAbundanceBrushwoodRuntime == 0) {
            // reset counter
            chunkAbundanceBrushwoodRuntime = chunkAbundanceBrushwood;
            for (int loop = 0; loop < blockAbundanceBrushwood; loop++) {
                // get a random block in this chunk
                int blockX = chunkStartX + random.nextInt(16);
                int blockZ = chunkStartZ + random.nextInt(16);
                // get the surface level of this chunk
                int blockY = world.getTopSolidOrLiquidBlock(blockX, blockZ);
                // check if there are leaves somewhere around this block
                // TODO: I had a lot of instability with writing this, seems to be OK now for some reason. Possibly the scan ranges being too high caused it and it tried to scan a block outside the chunk/world?
                scanLoop:
                for (int scanBlockX = (blockX - scanRadiusBrushwood); scanBlockX <= (blockX + scanRadiusBrushwood); scanBlockX++) {
                    for (int scanBlockZ = (blockZ - scanRadiusBrushwood); scanBlockZ <= (blockZ + scanRadiusBrushwood); scanBlockZ++) {
                        for (int scanBlockY = blockY; scanBlockY <= (blockY + scanHeightBrushwood); scanBlockY++) {
                            Block thisBlock = world.getBlock(scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0);
                            if (thisBlock.isAir(world, scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0)) break;
                            if (world.getBlock(scanBlockX + 0, scanBlockY + 0, scanBlockZ + 0).getMaterial() == Material.leaves) {
                                world.setBlock(blockX + 0, blockY + 0, blockZ + 0, Main.blockBrushwood, 0, 0);
                                break scanLoop;
                            }
                        }
                    }
                }
            }
        }
    }
}