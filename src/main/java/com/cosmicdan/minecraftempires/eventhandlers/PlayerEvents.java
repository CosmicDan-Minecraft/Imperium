package com.cosmicdan.minecraftempires.eventhandlers;

import java.util.Calendar;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsEssential.EssentialEvents;
import com.cosmicdan.minecraftempires.medata.world.WorldData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

// Player events
public class PlayerEvents {
    // other events of interest not yet used: onPlayerChangedDimension and onPlayerRespawn
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayerME playerME = EntityPlayerME.get((EntityPlayerMP)event.player);
        if (!playerME.hasData) {
            playerME.addEvent(EssentialEvents.FIRSTJOIN);
            playerME.eventPending = true;
        }
        String msg = StatCollector.translateToLocalFormatted("text.welcome", event.player.getDisplayName(), WorldData.worldDay);
        event.player.addChatMessage(new ChatComponentText(msg));
        
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
    }
}