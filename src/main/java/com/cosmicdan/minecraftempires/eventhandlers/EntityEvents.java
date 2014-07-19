package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.cosmicdan.minecraftempires.items.ModItems;
import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsTutorial.TutorialEvents;
import com.cosmicdan.minecraftempires.server.PacketHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class EntityEvents {
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && MinecraftEmpiresPlayer.get((EntityPlayer) event.entity) == null)
            MinecraftEmpiresPlayer.register((EntityPlayer) event.entity);

        if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(MinecraftEmpiresPlayer.EXT_PROP_NAME) == null)
            event.entity.registerExtendedProperties(MinecraftEmpiresPlayer.EXT_PROP_NAME, new MinecraftEmpiresPlayer((EntityPlayer) event.entity));
    }
    
    public void onEntityJoin(EntityJoinWorldEvent event) {
        
        // TODO: Is this needed for multiplayer? I think PlayerEvents should handle this fine, need to test
        //if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
        //    PacketHandler.packetReq.sendTo(new SyncPlayerME((EntityPlayer)event.entity), (EntityPlayerMP)event.entity);
        //}
    }
    
    @SubscribeEvent
    public void onPlayerPickup(EntityItemPickupEvent event) {
        if (event.entity instanceof EntityPlayer) {
            MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get((EntityPlayer) event.entity);
            if (event.item.getEntityItem().getItem() == ModItems.brushwood) {
                if (!playerME.eventListDone.toString().contains("BRUSHWOOD")) {
                    playerME.addInstantEvent(TutorialEvents.BRUSHWOOD);
                    playerME.syncToServer();
                }
            }
        }
        event.setResult(Result.ALLOW);
    }
    
    @SubscribeEvent
    public void onCheckSpawn(CheckSpawn event) {
        // always allow players, not sure it's needed but whatever
        if (event.entity instanceof EntityPlayer) {
            event.setResult(Result.ALLOW);
            return;
        }
        if (event.entity instanceof EntityMob) {
            event.setResult(Result.DENY);
            return;
        }
    }
}
