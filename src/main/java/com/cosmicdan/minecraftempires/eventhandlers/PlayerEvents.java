package com.cosmicdan.minecraftempires.eventhandlers;

import java.util.Calendar;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.datamanagement.PlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

// Player events
public class PlayerEvents {
    // other events of interest not yet used: onPlayerChangedDimension and onPlayerRespawn
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) { 
        EntityPlayerMP player = (EntityPlayerMP)event.player;
        PlayerData playerData = new PlayerData(player);
        // TODO: I think this should only be run on client, I18n causes dedicated server to crash 
        //       not sure if that's a Forge bug or if there is a better way for string formatting...
        playerData.welcome();
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        // DISABLED. We only want player data to save when the server shuts down!
        // TODO: But is this safe? No, if the server crashes a lot could be lost.
        //PlayerData.savePlayerData((EntityPlayerMP)event.player);
    }
    
}
