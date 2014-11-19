package com.cosmicdan.imperium4x.eventhandlers;

import java.util.Iterator;

import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.cosmicdan.imperium4x.ai.EntityAIFlockMentality;
import com.cosmicdan.imperium4x.data.player.ImperiumPlayer;
import com.cosmicdan.imperium4x.data.player.PlayerEventsTutorial.TutorialEvents;
import com.cosmicdan.imperium4x.items.ModItems;
import com.cosmicdan.imperium4x.server.PacketHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class EntityEvents {
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ImperiumPlayer.get((EntityPlayer) event.entity) == null)
            ImperiumPlayer.register((EntityPlayer) event.entity);

        if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(ImperiumPlayer.EXT_PROP_NAME) == null)
            event.entity.registerExtendedProperties(ImperiumPlayer.EXT_PROP_NAME, new ImperiumPlayer((EntityPlayer) event.entity));
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
            ImperiumPlayer playerImperium = ImperiumPlayer.get((EntityPlayer) event.entity);
            if (event.item.getEntityItem().getItem() == ModItems.brushwood) {
                if (!playerImperium.eventListDone.toString().contains("BRUSHWOOD")) {
                    playerImperium.addInstantEvent(TutorialEvents.BRUSHWOOD);
                    playerImperium.syncToServer("events");
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
                EntityAnimal entityAnimal = (EntityAnimal) event.entity;
                if (entityAnimal.tasks.taskEntries.size() > 0) { // ensure it actually uses the new AI, just in case
                    EntityAIBase taskToReplace = null; // initialize with a stupid value to stop precompiler complaining
                    int priorityToReplace = -1; // as above
                    
                    // TODO: This is a bit costly. Must be a better way.
                    Iterator taskEntries = entityAnimal.tasks.taskEntries.iterator();
                    while (taskEntries.hasNext()) {
                        EntityAITasks.EntityAITaskEntry taskEntry = (EntityAITasks.EntityAITaskEntry)taskEntries.next();
                        // Replace the 'EntityAIWander' task with our own 'EntityAIFlockMentality'
                        if (taskEntry.action instanceof net.minecraft.entity.ai.EntityAIWander) {
                            taskToReplace = taskEntry.action;
                            priorityToReplace = taskEntry.priority;
                        }
                    }
                    
                    if (taskToReplace != null) {
                        entityAnimal.tasks.removeTask(taskToReplace);
                        entityAnimal.tasks.addTask(priorityToReplace, new EntityAIFlockMentality(entityAnimal, 1.0D));
                        //TODO: EntityAIAvoidEntity needs to be integrated with the new EntityAIFlockMentality
                        entityAnimal.tasks.addTask(priorityToReplace, new EntityAIAvoidEntity(entityAnimal, EntityPlayer.class, 6.0F, 1.5D, 2.0D));
                    }
                }
            }
        }
    }
}
