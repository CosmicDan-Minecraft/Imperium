package com.cosmicdan.minecraftempires.server;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper packetReq = NetworkRegistry.INSTANCE.newSimpleChannel("minecraftempires");

    public static void init() {
        // Register EntityPlayerME for updates TO clients (e.g. status and information for player GUI's)
        packetReq.registerMessage(SyncPlayerME.class, SyncPlayerME.class, 0, Side.CLIENT);
        // Register EntityPlayerME for updates FROM clients (e.g. events and quests triggered)
        packetReq.registerMessage(SyncPlayerME.class, SyncPlayerME.class, 1, Side.SERVER);
    }
}
