package com.cosmicdan.minecraftempires.recipes;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.blocks.ModBlocks;
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
        ItemStack itemStackStick = new ItemStack(Items.stick);
        GameRegistry.addShapelessRecipe(
                // Recipe result - stick shelter
                new ItemStack(ModItems.stickshelter),
                // Recipe ingredients - 4x Brushwood
                new Object[] {itemStackBrushwood, itemStackBrushwood, itemStackBrushwood, itemStackBrushwood});
        GameRegistry.addShapelessRecipe(
                itemStackStick,
                new Object[] {itemStackBrushwood});
        GameRegistry.addShapelessRecipe(
                new ItemStack(ModBlocks.campfire),
                new Object[] {itemStackBrushwood, itemStackStick, itemStackBrushwood, itemStackStick});
    }
    
    public static void addAll() {
        recipeShelter();
    }
}
