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
        // I think this should only be run on client...
        playerData.welcome();
        //initPlayer(playerData.playerDataPersisted, playerData.isNewPlayer);
            
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        //System.out.println("Logged out!");
        PlayerData.savePlayerData((EntityPlayerMP)event.player);
    }
    
}
