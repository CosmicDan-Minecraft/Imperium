package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


// This class is responsible for imposing restrictions depending on their progression
public class EventHandlerRestrictions {
    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event) {
        // trees
        if (event.block == Blocks.log) {
            checkAbortTree(event);
        }
        // other trees
        else if (event.block == Blocks.log2) {
            checkAbortTree(event);
        }
    }
    
    
    
    public void checkAbortTree(BreakSpeed event) {
        if(event.isCancelable()) {
            if (event.entityPlayer.isSwingInProgress) {
                // TODO: Actually check if they've learned the art of Lumbering (Tek) or Tree-Chi (Ava)
                event.entityPlayer.performHurtAnimation();
                event.setCanceled(true);
                
            }
        }
    }
}
