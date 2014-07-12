package com.cosmicdan.minecraftempires.playermanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;

import com.cosmicdan.minecraftempires.playermanagement.EventsEssential.EssentialEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityPlayerME implements IExtendedEntityProperties {
    
    public final static String EXT_PROP_NAME = "MinecraftEmpiresPlayer";
    //public final static String EXT_PROP_LASTLOGIN = "lastLogin";
    private final EntityPlayerMP player;
    
    public Boolean hasData = false;
    public Boolean eventPending = false;
    public ArrayList eventListPending = new ArrayList(0);
    public ArrayList eventListDone = new ArrayList(0);
    public Long lastLogin, lastSave = 0L;
    
    public EntityPlayerME(EntityPlayerMP player) {
        this.player = player;
    }
    
    public static final void register(EntityPlayerMP player) {
        //System.out.println(">>> New EntityPlayerME!");
        player.registerExtendedProperties(EntityPlayerME.EXT_PROP_NAME, new EntityPlayerME(player));
    }
    
    public static final EntityPlayerME get(EntityPlayerMP player) {
        return (EntityPlayerME) player.getExtendedProperties(EXT_PROP_NAME);
    }
    
    @Override
    public void saveNBTData(NBTTagCompound playerData) {
        NBTTagCompound playerProps = new NBTTagCompound();
        playerProps.setBoolean("eventPending", eventPending);
        playerProps.setString("eventListPending", eventListPending.toString());
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
        this.eventListPending = nbtStringToArrayList(playerProps.getString("eventListPending"));
        this.eventListDone = nbtStringToArrayList(playerProps.getString("eventListDone"));
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
    
    public void doEvents() {
        System.out.println("'" + eventListPending.toString() + "'");
        for (Object event : eventListPending) {
            if (eventTypeEssential(event.toString()))
                EventsEssential.eventEssential(player, (EssentialEvents)event);
            
            eventListDone.add(event.toString());
        }
        eventListPending = new ArrayList(0);
        eventPending = false;
    }
    
    /*
     * helpers
     */
    private static ArrayList nbtStringToArrayList(String s) {
        ArrayList retval = new ArrayList(0);
        List<String> list = Arrays.asList(s.substring(1, s.length() - 1).split(", "));
        for (String event : list) {
            if (eventTypeEssential(event))
                retval.add(EssentialEvents.valueOf(event));
        }
        return retval;
    }
    
    private static Boolean eventTypeEssential(String event) {
        for (EssentialEvents essentialEvent : EssentialEvents.values()) {
            if(essentialEvent.toString().contains(event.toString())) {
                return true;
            }
        }
        return false;
    }
}
