package com.cosmicdan.minecraftempires.medata.player;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.medata.player.PlayerEventsEssential.EssentialEvents;

public class PlayerEventsTutorial {
    public enum TutorialEvents {
        WOODPUNCH, BRUSHWOOD,
    }
    
    public static void eventTutorial(EntityPlayerMP player, TutorialEvents event) {
        MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get(player);
        switch (event){
            // I don't expect these to ever do anything, tutorial events are only informative
            default:
                break;
        }
    }
}
