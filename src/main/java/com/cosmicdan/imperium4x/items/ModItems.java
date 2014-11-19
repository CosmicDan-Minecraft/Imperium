package com.cosmicdan.imperium4x.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public final class ModItems {
    public static Item brushwood;
    public static Item playerlog;
    public static Item stickshelter;
    
    public static final void addItems() {
        brushwood = new ItemBrushwood();
        GameRegistry.registerItem(brushwood, "itemBrushwood");
        playerlog = new ItemPlayerLog();
        GameRegistry.registerItem(playerlog, "itemPlayerLog");
        stickshelter = new ItemStickShelter();
        GameRegistry.registerItem(stickshelter, "itemStickShelter");
    }
}
