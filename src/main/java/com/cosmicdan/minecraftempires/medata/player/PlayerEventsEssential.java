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
        MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get(player);
        switch (event){
        case FIRSTJOIN:
            // we don't use notifyPlayerOfEvent here because it's special
            String gotPlayerLog = StatCollector.translateToLocal("playerlog.found");
            player.addChatComponentMessage(new ChatComponentText(gotPlayerLog));
            // give them the log item
            player.inventory.addItemStackToInventory(new ItemStack(Main.itemPlayerLog));
        }
    }
}
