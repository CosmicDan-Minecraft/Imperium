package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
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
    public void onEntityPickup(EntityItemPickupEvent event) {
        if (event.entity instanceof EntityPlayer) {
            MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get((EntityPlayer) event.entity);
            if (event.item.getEntityItem().getItem() == ModItems.brushwood) {
                if (!playerME.eventListDone.toString().contains("BRUSHWOOD")) {
                    playerME.addInstantEvent(TutorialEvents.BRUSHWOOD);
                    playerME.syncToServer();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onCheckSpawn(CheckSpawn event) {
        if(!event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityMob || event.entity instanceof IMob) {
                event.setResult(Result.DENY);
                return;
            }
            event.setResult(Result.ALLOW);
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(!event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityAnimal) {
                // make animals run away from the player
                EntityAnimal entityAnimal = (EntityAnimal) event.entity;
                if (entityAnimal.tasks.taskEntries.size() > 0) { // ensure it actually uses the new AI, just in case
                    /*
                     *  - First int of addTask seems to be priority
                     *  theEntity, targetEntityClass, distanceFromEntity, farSpeed, nearSpeed
                     *  - distanceFromEntity - no idea how this is measured but it seems to be close to block distance
                     *  - farSpeed and nearSpeed are multipliers of their base speed
                     */
                    entityAnimal.tasks.addTask(1, new EntityAIAvoidEntity(entityAnimal, EntityPlayer.class, 6.0F, 1.5D, 2.0D));
                }
            }
        }
    }
}
