package com.cosmicdan.minecraftempires.eventhandlers;

import com.cosmicdan.minecraftempires.datamanagement.WorldData;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldTickEvents {
    private final Long ticksPerHalfHour = 500L;
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
                if (thisTick % ticksPerHalfHour == 0) { // half-hour mark
                    globalTicker(event);
                }
            }
        }
    }
    
    private void globalTicker(WorldTickEvent event) {
        World world = event.world;
        Long timeOfDay = world.getWorldTime() % 24000L;
        System.out.println(timeOfDay);
        if (timeOfDay == ticksDawn) { // new day has dawned
            //System.out.println(">>> DAWN!");
            WorldData.worldDay += 1;
            sendGlobalMessage(StatCollector.translateToLocalFormatted("text.newDayDawns", WorldData.worldDay));
        }
    }
    
    private void sendGlobalMessage(String msg) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(msg));
    }
}
