package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;
import com.cosmicdan.minecraftempires.server.PacketHandler;
import com.cosmicdan.minecraftempires.server.SyncPlayerME;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents {
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && EntityPlayerME.get((EntityPlayer) event.entity) == null)
            EntityPlayerME.register((EntityPlayer) event.entity);

        if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(EntityPlayerME.EXT_PROP_NAME) == null) {
            event.entity.registerExtendedProperties(EntityPlayerME.EXT_PROP_NAME, new EntityPlayerME((EntityPlayerMP) event.entity));
        }
    }
    
    public void onEntityJoin(EntityJoinWorldEvent event) {
        
        // multiplayer (needed? I think PlayerEvents should handle this fine)
        //if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
        //    PacketHandler.packetReq.sendTo(new SyncPlayerME((EntityPlayer)event.entity), (EntityPlayerMP)event.entity);
        //}
    }
}
