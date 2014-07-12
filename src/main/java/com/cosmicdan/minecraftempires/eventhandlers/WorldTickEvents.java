package com.cosmicdan.minecraftempires.eventhandlers;

import com.cosmicdan.minecraftempires.datamanagement.WorldData;
import com.cosmicdan.minecraftempires.playermanagement.EntityPlayerME;

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
    //private NBTTagCompound worldDataGlobal;
    
    public WorldTickEvents() {
        //WorldData worldData = new WorldData(); 
        //worldData.loadData(world, "MinecraftEmpires", "global");
        //worldDataGlobal = worldData.getData();
    }
    
    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event) {
        if(event.side == Side.SERVER && event.phase == Phase.END) {
            // only operate on overworld (for now)
            World world = event.world;
            if (world.provider.dimensionId == 0) {
                Long thisTick = world.getWorldTime();
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
    
    /*
     * Iterates over all players and handles their events
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
}
