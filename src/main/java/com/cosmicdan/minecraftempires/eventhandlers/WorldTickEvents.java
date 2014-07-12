package com.cosmicdan.minecraftempires.eventhandlers;

import java.util.ArrayList;

import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;
import com.cosmicdan.minecraftempires.medata.world.WorldData;

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
    
    // how many ticks between checking for "instant" events (I really don't want to check for instants every tick)
    private int instantTickDelay = 20;
    
    private int instantTickDelayReset = instantTickDelay;
    private static ArrayList<String> eventPendingInstantUsers = new ArrayList();
    
    public static Boolean eventPendingInstant = false;
    
    public WorldTickEvents() {
        //WorldData worldData = new WorldData(); 
        //worldData.loadData(world, "MinecraftEmpires", "global");
        //worldDataGlobal = worldData.getData();
    }
    
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
        }
        doPlayerEvents();
    }
    
    private void sendGlobalMessage(String msg) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(msg));
    }
    
    public static void addPlayerToPendingInstants(EntityPlayerMP player) {
        eventPendingInstantUsers.add(player.getDisplayName());
    }
    
    /*
     * Iterates over all players and handles their NEXT events
     */
    private void doPlayerEvents() {
        MinecraftServer mc = FMLClientHandler.instance().getServer();
        String allNames[] = mc.getAllUsernames().clone();
        for(int i = 0; i < allNames.length; i++) {
            // func_152612_a = getPlayerForUsername
            EntityPlayerMP thisPlayer = (EntityPlayerMP) MinecraftServer.getServer().getConfigurationManager().func_152612_a(allNames[i]);
            EntityPlayerME playerME = EntityPlayerME.get(thisPlayer);
            if (playerME.eventPending)
                playerME.doEvents();
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
            EntityPlayerMP thisPlayer = (EntityPlayerMP) MinecraftServer.getServer().getConfigurationManager().func_152612_a(allNames[i]);
            // TODO: Ensure they're currently in-world, and heck if this is a valid playername (just in case)
            EntityPlayerME playerME = EntityPlayerME.get(thisPlayer);
            playerME.doInstantEvents();
        }
        eventPendingInstantUsers = new ArrayList();
        eventPendingInstant = false;
    }
}
