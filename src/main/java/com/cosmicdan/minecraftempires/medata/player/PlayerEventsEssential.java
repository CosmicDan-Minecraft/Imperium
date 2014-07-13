package com.cosmicdan.minecraftempires.medata.player;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class PlayerEventsEssential {

    public enum EssentialEvents {
        FIRSTJOIN,
    }
    
    public static void eventEssential(EntityPlayerMP player, EssentialEvents event) {
        switch (event){
        case FIRSTJOIN:
            player.inventory.addItemStackToInventory(new ItemStack(Main.itemPlayerLog));
            // we don't use notifyPlayerOfEvent here because it's special
            String gotPlayerLog = StatCollector.translateToLocal("playerlog.found");
            player.addChatComponentMessage(new ChatComponentText(gotPlayerLog));
        }
    }
    
    private static void notifyPlayerOfEvent(EntityPlayerMP player, int tier) {
        if (tier == 1) { // Primal age, "Player Log"
            String writtenToLog = StatCollector.translateToLocal("playerlog.written");
            player.addChatComponentMessage(new ChatComponentText(writtenToLog));
        }
    }
}
