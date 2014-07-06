package com.cosmicdan.minecraftempires.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBrushwood extends Item {
    public ItemBrushwood() {
        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.tabMisc);
        setTextureName("minecraftempires:brushwood");
        setUnlocalizedName("Brushwood");
    }
}
