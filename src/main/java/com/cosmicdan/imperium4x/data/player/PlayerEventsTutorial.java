package com.cosmicdan.imperium4x.data.player;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import com.cosmicdan.imperium4x.Main;
import com.cosmicdan.imperium4x.data.player.PlayerEventsEssential.EssentialEvents;

public class PlayerEventsTutorial {
    public enum TutorialEvents {
        WOODPUNCH, BRUSHWOOD,
    }
    
    public static void eventTutorial(EntityPlayerMP player, TutorialEvents event) {
        ImperiumPlayer playerImperium = ImperiumPlayer.get(player);
        switch (event){
            // I don't expect these to ever do anything, tutorial events are only informative
            default:
                break;
        }
    }
}
