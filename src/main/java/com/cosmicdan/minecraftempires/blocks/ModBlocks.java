package com.cosmicdan.minecraftempires.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public final class ModBlocks {
    public static Block brushwood;
    public static Block stickshelter;
    public static Block campfire;
    
    public static final void addBlocks() {
        brushwood = new BlockBrushwood();
        GameRegistry.registerBlock(brushwood, "blockBrushwood");
        stickshelter = new BlockStickShelter();
        GameRegistry.registerBlock(stickshelter, "blockStickShelter");
        campfire = new BlockCampfire();
        GameRegistry.registerBlock(campfire, "blockCampfire");
    }
}
