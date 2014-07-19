package com.cosmicdan.minecraftempires.client.renderers;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ModRenderers {
    
    public static int rendererStickShelter;
    
    public static void registerAll() {
        RenderStickShelter renderStickShelter = new RenderStickShelter();
        rendererStickShelter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(rendererStickShelter, renderStickShelter);
        //rendererStickShelter = renderStickShelter.getRenderId();
    }
}
