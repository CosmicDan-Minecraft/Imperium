package com.cosmicdan.imperium4x.entities.tiles;

import cpw.mods.fml.client.registry.ClientRegistry;

public final class ModTileEntityRenderers {
    public static void addTileEntityRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityCampfireRenderer());
    }
}
