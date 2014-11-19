package com.cosmicdan.imperium4x.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

// This is the Brushwood item drop for Brushwood block. Self-explanatory.
public class ItemBrushwood extends Item {
    public ItemBrushwood() {
        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.tabMisc);
        setTextureName("imperium4x:brushwood");
        setUnlocalizedName("Brushwood");
    }
}
