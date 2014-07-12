package com.cosmicdan.minecraftempires.eventhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import com.cosmicdan.minecraftempires.playermanagement.EntityPlayerME;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents {
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayerMP && EntityPlayerME.get((EntityPlayerMP) event.entity) == null)
            EntityPlayerME.register((EntityPlayerMP) event.entity);

        if (event.entity instanceof EntityPlayerMP && event.entity.getExtendedProperties(EntityPlayerME.EXT_PROP_NAME) == null)
            event.entity.registerExtendedProperties(EntityPlayerME.EXT_PROP_NAME, new EntityPlayerME((EntityPlayerMP) event.entity));
    }
}
