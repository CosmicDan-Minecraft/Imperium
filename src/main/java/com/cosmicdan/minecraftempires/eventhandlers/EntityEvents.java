package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;
import com.cosmicdan.minecraftempires.server.PacketHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents {
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && MinecraftEmpiresPlayer.get((EntityPlayer) event.entity) == null)
            MinecraftEmpiresPlayer.register((EntityPlayer) event.entity);

        if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(MinecraftEmpiresPlayer.EXT_PROP_NAME) == null) {
            event.entity.registerExtendedProperties(MinecraftEmpiresPlayer.EXT_PROP_NAME, new MinecraftEmpiresPlayer((EntityPlayerMP) event.entity));
        }
    }
    
    public void onEntityJoin(EntityJoinWorldEvent event) {
        
        // TODO: Is this needed for multiplayer? I think PlayerEvents should handle this fine, need to test
        //if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
        //    PacketHandler.packetReq.sendTo(new SyncPlayerME((EntityPlayer)event.entity), (EntityPlayerMP)event.entity);
        //}
    }
}
