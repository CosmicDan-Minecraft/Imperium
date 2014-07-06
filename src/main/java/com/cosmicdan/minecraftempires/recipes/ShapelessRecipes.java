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
        GameRegistry.addShapelessRecipe(new ItemStack(Items.bed, 1), new Object[] {itemStackBrushwood, itemStackBrushwood, itemStackBrushwood, itemStackBrushwood});
    }
}
