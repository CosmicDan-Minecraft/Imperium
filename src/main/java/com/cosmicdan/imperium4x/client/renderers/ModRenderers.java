package com.cosmicdan.imperium4x.client.renderers;

import cpw.mods.fml.client.registry.RenderingRegistry;

public final class ModRenderers {
    
    public static int rendererStickShelter;
    public static int rendererCampfire;
    
    public static void registerAll() {
        RenderStickShelter renderStickShelter = new RenderStickShelter();
        rendererStickShelter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(rendererStickShelter, renderStickShelter);
        RenderCampfire renderCampfire = new RenderCampfire();
        rendererCampfire = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(rendererCampfire, renderCampfire);
    }
}
