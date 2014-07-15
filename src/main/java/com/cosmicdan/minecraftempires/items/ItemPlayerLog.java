package com.cosmicdan.minecraftempires.items;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;

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
        setTextureName("minecraftempires:playerlog");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            //EntityPlayerME playerME = EntityPlayerME.get(player);
            Main.proxy.openJournal(player);
        }
        return stack;
    }
}
