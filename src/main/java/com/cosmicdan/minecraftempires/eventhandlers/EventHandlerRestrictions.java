package com.cosmicdan.minecraftempires.eventhandlers;

import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;
import com.cosmicdan.minecraftempires.medata.player.PlayerData;
import com.cosmicdan.minecraftempires.medata.player.PlayerData.PlayerAbilities;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsEssential.EssentialEvents;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsTutorial.TutorialEvents;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


 /* 
  * This class is responsible for imposing restrictions depending on their progression.
  * This will become massive in future, so it gets it's own class based on purpose rather than specific events
  */
public class EventHandlerRestrictions {
    
    MinecraftEmpiresPlayer playerME;
    
    // hook event when a player is trying to break a block
    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event) {
        playerME = MinecraftEmpiresPlayer.get(event.entityPlayer); 
        if (event.block == Blocks.log) {
            // the player is trying to break a vanilla tree
            checkAbortTree(event);
        }
        else if (event.block == Blocks.log2) {
            // the player is trying to break another vanilla tree
            checkAbortTree(event);
        }
    }
    
    // this method is for determining if a tree block break is to be permitted or denied
    public void checkAbortTree(BreakSpeed event) {
        if (!PlayerData.hasAbility(playerME, PlayerAbilities.CANPUNCHWOOD)) {
            if (!playerME.eventListDone.toString().contains("WOODPUNCH")) {
                playerME.addInstantEvent(TutorialEvents.WOODPUNCH);
                playerME.syncToServer("events");
            }
            if(event.isCancelable()) { // not sure if needed but doesn't hurt
                event.entityPlayer.swingProgressInt = 0;
                event.setCanceled(true);
            }
        }
    }
}
