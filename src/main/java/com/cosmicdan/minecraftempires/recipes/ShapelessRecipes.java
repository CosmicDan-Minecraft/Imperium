package com.cosmicdan.minecraftempires.recipes;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ShapelessRecipes {
    public static void RecipeShelter() {
        ItemStack itemStackBrushwood = new ItemStack(Main.itemBrushwood);
        // For now, 4 Brushwoods just makes a bed. Todo:
        //  - Make it a custom "Shelter" bed
        //  - Model for it (hard)
        //  - Duplicate vanilla bed code, but shelter has a 100% chance to "break" when player awakes (not quite as hard)
        GameRegistry.addShapelessRecipe(new ItemStack(Items.bed, 1), new Object[] {itemStackBrushwood, itemStackBrushwood, itemStackBrushwood, itemStackBrushwood});
    }
}
