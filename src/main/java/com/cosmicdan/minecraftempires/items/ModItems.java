package com.cosmicdan.minecraftempires.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public final class ModItems {
    public static Item brushwood;
    public static Item playerLog;
    
    public static final void addItems() {
        brushwood = new ItemBrushwood();
        GameRegistry.registerItem(brushwood, "itemBrushwood");
        playerLog = new ItemPlayerLog();
        GameRegistry.registerItem(playerLog, "itemPlayerLog");
    }
}
