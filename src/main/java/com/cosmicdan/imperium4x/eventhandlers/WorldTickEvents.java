package com.cosmicdan.imperium4x.eventhandlers;

import java.util.ArrayList;

import com.cosmicdan.imperium4x.data.player.ImperiumPlayer;
import com.cosmicdan.imperium4x.data.world.WorldData;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldTickEvents {
    private final Long ticksPerHalfHour = 500L;
    private final Long ticksPerQuartHour = 250L;
    private final Long ticksDawn = 0L;
    private final Long ticksNoon = 6000L;
    private final Long ticksEvening = 12000L;
    private final Long ticksMidnight = 18000L;
    
    // how many ticks between checking for "instant" events (I don't want to check for instants every tick)
    private int instantTickDelay = 30;
    
    private int instantTickDelayReset = instantTickDelay;
    private static ArrayList<String> eventPendingInstantUsers = new ArrayList();
    
    public static Boolean eventPendingInstant = false;
    
    public WorldTickEvents() { }
    
    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event) {
        if(event.side == Side.SERVER && event.phase == Phase.END) {
            World world = event.world;
            if (world.provider.dimensionId == 0) { // only operate on overworld (for now)
                Long thisTick = world.getWorldTime();
                if (instantTickDelay == 0) {
                    if (WorldTickEvents.eventPendingInstant) {
                        if (eventPendingInstantUsers.size() > 0) { // player instants
                            doPlayerInstants();
                        }
                    }
                    instantTickDelay = instantTickDelayReset;
                }
                instantTickDelay--;
                if (thisTick % ticksPerQuartHour == 0) { // check for stuff every in-game quarter-hour
                    globalTicker(event);
                }
            }
        }
    }
    
    private void globalTicker(WorldTickEvent event) {
        World world = event.world;
        Long timeOfDay = world.getWorldTime() % 24000L;
        if (timeOfDay == ticksDawn) { // new day has dawned
            WorldData.worldDay += 1;
            sendGlobalMessage(StatCollector.translateToLocalFormatted("text.newDayDawns", WorldData.worldDay));
            WorldEvents.saveSettings(world, "global");
        }
        doPlayerEvents();
    }
    
    private void sendGlobalMessage(String msg) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(msg));
    }
    
    public static Boolean addPlayerToPendingInstants(EntityPlayerMP player) {
        if (!isPlayerPendingInstant(player))
            return eventPendingInstantUsers.add(player.getDisplayName());
        else
            return false;
    }
    
    public static Boolean isPlayerPendingInstant(EntityPlayerMP player) {
        if (eventPendingInstantUsers.toString().contains(player.getDisplayName()))
            return true;
        else
            return false;
    }
    
    /*
     * Iterates over all players and handles their NEXT events
     */
    private void doPlayerEvents() {
        MinecraftServer mc = FMLClientHandler.instance().getServer();
        String allNames[] = mc.getAllUsernames().clone();
        for(int i = 0; i < allNames.length; i++) {
            // func_152612_a = getPlayerForUsername
            EntityPlayerMP thisPlayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(allNames[i]);
            ImperiumPlayer playerImperium = ImperiumPlayer.get(thisPlayer);
            if (playerImperium.eventPending)
                playerImperium.doEvents();
        }
    }
    
    /*
     * Iterates over all players and handles their INSTANT events
     */
    private void doPlayerInstants() {
        String[] allNames = new String[eventPendingInstantUsers.size()];
        allNames = eventPendingInstantUsers.toArray(allNames);
        for(int i = 0; i < allNames.length; i++) {
            // func_152612_a = getPlayerForUsername
            EntityPlayerMP thisPlayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(allNames[i]);
            // TODO: Ensure they're currently in-world, and check if this is a valid playername (just in case)
            ImperiumPlayer playerImperium = ImperiumPlayer.get(thisPlayer);
            playerImperium.doInstantEvents();
        }
        eventPendingInstantUsers = new ArrayList();
        eventPendingInstant = false;
    }
}
