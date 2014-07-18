package com.cosmicdan.minecraftempires.recipes;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.items.ModItems;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

// New shapeless recipe's here.
public class ShapelessRecipes {
    private static void recipeShelter() {
        // declare item stacks for recipe ingredients. Bit misleading, "ItemStack" is just a type meaning the item in an inventory (doesn't mean more than 1)
        ItemStack itemStackBrushwood = new ItemStack(ModItems.brushwood);
        // For now, 4 Brushwood just makes a bed. Todo:
        //  - Make it a custom "Shelter" bed
        //  - Duplicate vanilla bed code, but shelter has a 100% chance to "break" when player awakes (hard)
        //  - Make a model for it (even harder)
        GameRegistry.addShapelessRecipe(
                // Recipe result - a bed
                new ItemStack(Items.bed, 1),
                // Recipe ingredients - 4x Brushwood
                new Object[] {itemStackBrushwood, itemStackBrushwood, itemStackBrushwood, itemStackBrushwood});
    }
    
    public static void addAll() {
        recipeShelter();
    }
}
