package com.cosmicdan.minecraftempires.entities.tiles;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModTileEntities {
    
    public static void addTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCampfire.class, "entitiesTileCampfire");
    }
}
