package com.cosmicdan.imperium4x.items;

import com.cosmicdan.imperium4x.Main;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// This is the Journal item. Self-explanatory.
public class ItemPlayerLog extends Item {
    public ItemPlayerLog() {
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("PlayerLog");
        setTextureName("imperium4x:playerlog");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            //EntityPlayerME playerImperium = EntityPlayerME.get(player);
            Main.proxy.openJournal(player);
        }
        return stack;
    }
}
