package com.cosmicdan.minecraftempires.items;

import com.cosmicdan.minecraftempires.Main;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemJournal extends Item {
    public ItemJournal() {
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("Journal");
        setTextureName("minecraftempires:journal");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            Main.proxy.openJournal();
        }
        return stack;
    }
}
