package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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
    
    // hook event when a player is trying to break a block
    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event) {
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
        if(event.isCancelable()) { // safety purposes
            if (event.entityPlayer.isSwingInProgress) {
                // Play the hurt animation but don't actually hurt them. 
                // TODO: This event is fired ~5 times on a single click, see if I can't solve that :)
                event.entityPlayer.performHurtAnimation();
                // Deny the block breaking
                // TODO: Actually check if they've learned the art of Lumbering (Tek) or Tree-Chi (Ava)
                event.setCanceled(true);
                // TODO: Queue a Journal update for this player. Something like "Today I tried to punch a tree,
                //       it didn't turn out like I expected. Perhaps I can find some wood [b]lying around[/b] instead...
            }
        }
    }
}
