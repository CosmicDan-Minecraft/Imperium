package com.cosmicdan.minecraftempires.medata.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;

import com.cosmicdan.minecraftempires.eventhandlers.WorldTickEvents;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsEssential.EssentialEvents;
import com.cosmicdan.minecraftempires.medata.world.WorldData;
import com.cosmicdan.minecraftempires.server.PacketHandler;
import com.cosmicdan.minecraftempires.server.SyncPlayerME;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class MinecraftEmpiresPlayer implements IExtendedEntityProperties {
    
    public final static String EXT_PROP_NAME = "MinecraftEmpiresPlayer";
    //public final static String EXT_PROP_LASTLOGIN = "lastLogin";
    private final EntityPlayer player;
    
    public Boolean hasData = false;
    public Boolean eventPending = false;
    public ArrayList eventListPending = new ArrayList();
    public ArrayList eventListPendingInstant = new ArrayList();
    public ArrayList eventListDone = new ArrayList();
    public Long lastLogin, lastSave = 0L;
    
    public MinecraftEmpiresPlayer(EntityPlayer player) {
        this.player = player;
    }
    
    public static final void register(EntityPlayer player) {
        player.registerExtendedProperties(MinecraftEmpiresPlayer.EXT_PROP_NAME, new MinecraftEmpiresPlayer(player));
    }
    
    public static final MinecraftEmpiresPlayer get(EntityPlayer player) {
        return (MinecraftEmpiresPlayer) player.getExtendedProperties(EXT_PROP_NAME);
    }
    
    @Override
    public void saveNBTData(NBTTagCompound playerData) {
        NBTTagCompound playerProps = new NBTTagCompound();
        playerProps.setBoolean("hasData", true);
        playerProps.setBoolean("eventPending", eventPending);
        playerProps.setString("eventListPending", eventListPending.toString());
        playerProps.setString("eventListPendingInstant", eventListPendingInstant.toString());
        playerProps.setString("eventListDone", eventListDone.toString());
        playerProps.setLong("lastLogin", this.lastLogin);
        playerProps.setLong("lastSave", this.player.worldObj.getTotalWorldTime());
        playerData.setTag(EXT_PROP_NAME, playerProps);
    }

    @Override
    public void loadNBTData(NBTTagCompound playerData) {
        NBTTagCompound playerProps = (NBTTagCompound) playerData.getTag(EXT_PROP_NAME);
        this.hasData = playerProps.getBoolean("hasData");
        if (!hasData) return;
        this.eventPending = playerProps.getBoolean("eventPending");
        this.eventListPending = stringToArrayList(playerProps.getString("eventListPending"));
        this.eventListPendingInstant = stringToArrayList(playerProps.getString("eventListPendingInstant"));
        if (this.eventListPendingInstant.size() > 0) {
            //System.out.println(">>> Player has pending events!");
            WorldTickEvents.eventPendingInstant = true;
            WorldTickEvents.addPlayerToPendingInstants((EntityPlayerMP)player);
        }
        this.eventListDone = stringToArrayList(playerProps.getString("eventListDone"));
        this.lastLogin = player.worldObj.getTotalWorldTime();
        this.lastSave = playerProps.getLong("lastSave");
    }

    @Override
    public void init(Entity entity, World world) {
        lastLogin = world.getTotalWorldTime();
        
    }

    public Boolean addEvent(Enum event) {
        return eventListPending.add(event);
    }
    
    public Boolean addInstantEvent(Enum event) {
        return eventListPendingInstant.add(event);
    }
    
    public void doEvents() {
        for (Object event : eventListPending) {
            doEventInternalProc(event);
        }
        eventListPending = new ArrayList(0);
        eventPending = false;
    }
    
    public void doInstantEvents() {
        for (Object event : eventListPendingInstant) {
            doEventInternalProc(event);
        }
        eventListPendingInstant = new ArrayList(0);
    }
    
    private void doEventInternalProc(Object event) {
        if (eventTypeEssential(event.toString()))
            PlayerEventsEssential.eventEssential((EntityPlayerMP)player, (EssentialEvents)event);
        eventListDone.add(event.toString() + "=" + WorldData.worldDay);
        sync();
    }
    
    public void sync() {
        PacketHandler.packetReq.sendTo(new SyncPlayerME(player), (EntityPlayerMP)player);
    }
    
    /*
     * helpers
     */
    public static ArrayList stringToArrayList(String s) {
        ArrayList retval = new ArrayList(0);
        List<String> list = Arrays.asList(s.substring(1, s.length() - 1).split(", "));
        for (String event : list) {
            if (event.isEmpty()) break;
            // cut the day off the end
            int eventDoneDay = Integer.parseInt(event.substring(event.indexOf("=") + 1));
            event = event.replace("=" + eventDoneDay, "");
            if (eventTypeEssential(event))
                retval.add(EssentialEvents.valueOf(event) + "=" + eventDoneDay);
        }
        return retval;
    }
    
    public static Boolean eventTypeEssential(String event) {
        for (EssentialEvents essentialEvent : EssentialEvents.values()) {
            if(essentialEvent.toString().contains(event.toString())) {
                return true;
            }
        }
        return false;
    }
    
    private static void notifyPlayerOfEvent(EntityPlayerMP player, int tier) {
        if (tier == 1) { // Primal age, "Player Log"
            String writtenToLog = StatCollector.translateToLocal("playerlog.written");
            player.addChatComponentMessage(new ChatComponentText(writtenToLog));
        }
    }
}
