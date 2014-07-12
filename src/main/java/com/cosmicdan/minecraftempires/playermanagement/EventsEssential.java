package com.cosmicdan.minecraftempires.playermanagement;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class EventsEssential {

    public enum EssentialEvents {
        FIRSTJOIN,
    }
    
    public static void eventEssential(EntityPlayerMP player, EssentialEvents event) {
        switch (event){
        case FIRSTJOIN:
            notifyPlayerOfEvent(player);
        }
    }
    
    private static void notifyPlayerOfEvent(EntityPlayerMP player) {
        player.inventory.addItemStackToInventory(new ItemStack(Main.itemJournal));
        String writtenToJournal = StatCollector.translateToLocal("journal.written");
        player.addChatComponentMessage(new ChatComponentText(writtenToJournal));
    }
}
