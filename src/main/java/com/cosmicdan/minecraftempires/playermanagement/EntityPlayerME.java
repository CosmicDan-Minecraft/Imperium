package com.cosmicdan.minecraftempires.playermanagement;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.EnumUtils;

import com.cosmicdan.minecraftempires.playermanagement.EventsEssential.EssentialEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityPlayerME implements IExtendedEntityProperties {
    
    public final static String EXT_PROP_NAME = "EntityPlayerME";
    private final EntityPlayerMP player;
    
    public Boolean eventPending = false;
    public ArrayList eventList = new ArrayList(0);
    
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
        //NBTTagCompound playerProps = new NBTTagCompound();
        //playerData.setTag(EXT_PROP_NAME, playerProps);
    }

    @Override
    public void loadNBTData(NBTTagCompound playerData) {
        //NBTTagCompound playerProps = (NBTTagCompound) playerData.getTag(EXT_PROP_NAME);
    }

    @Override
    public void init(Entity entity, World world) {
        // nothing to see here
    }

    public Boolean addEvent(Enum event) {
        return eventList.add(event);
    }
    
    public void doEvents() {
        for (Object event : eventList) {
            // check EssentialEvents
            for (EssentialEvents essentialEvent : EssentialEvents.values()) {
                if(essentialEvent.toString().contains(event.toString())) {
                    EventsEssential.eventEssential(player, (EssentialEvents)event);
                }
            }
        }
        eventList = new ArrayList(0);
        eventPending = false;
    }
}
