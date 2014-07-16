package com.cosmicdan.minecraftempires.server;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper packetReqest = NetworkRegistry.INSTANCE.newSimpleChannel("minecraftempires");

    public static void init() {
        // Register EntityPlayerME updates for clients (i.e. events/quests information)
        packetReqest.registerMessage(SyncPlayerME.class, SyncPlayerME.class, 0, Side.CLIENT);
    }
}
