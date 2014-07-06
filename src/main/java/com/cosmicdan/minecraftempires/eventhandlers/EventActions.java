package com.cosmicdan.minecraftempires.eventhandlers;

public class EventActions {
    public static void blockUnbreakable() {
        doGlobalMsg("Oof!");
    }
    
    public static void doGlobalMsg (String msg) {
        net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new net.minecraft.util.ChatComponentText(msg));
    }
}
