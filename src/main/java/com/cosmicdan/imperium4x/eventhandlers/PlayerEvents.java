package com.cosmicdan.imperium4x.eventhandlers;

import java.util.Calendar;

import com.cosmicdan.imperium4x.Main;
import com.cosmicdan.imperium4x.data.player.ImperiumPlayer;
import com.cosmicdan.imperium4x.data.player.PlayerEventsEssential.EssentialEvents;
import com.cosmicdan.imperium4x.data.world.WorldData;
import com.cosmicdan.imperium4x.server.PacketHandler;

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
        ImperiumPlayer playerImperium = ImperiumPlayer.get(event.player);
        if (!playerImperium.hasData) {
            playerImperium.hasData = true;
            playerImperium.addInstantEvent(EssentialEvents.FIRSTJOIN);
            WorldTickEvents.addPlayerToPendingInstants((EntityPlayerMP)event.player);
            WorldTickEvents.eventPendingInstant = true;
        }
        String msg = StatCollector.translateToLocalFormatted("text.welcome", event.player.getDisplayName(), WorldData.worldDay);
        event.player.addChatMessage(new ChatComponentText(msg));
        playerImperium.syncToClient("events");
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
    }
}
