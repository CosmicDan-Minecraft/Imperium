package com.cosmicdan.imperium4x.server;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper packetReq = NetworkRegistry.INSTANCE.newSimpleChannel("imperium4x");

    public static void init() {
        // Register EntityPlayerME for updates TO clients (e.g. status and information for player GUI's)
        packetReq.registerMessage(SyncEvents.class, SyncEvents.class, 0, Side.CLIENT);
        // Register EntityPlayerME for updates FROM clients (e.g. events and quests triggered)
        packetReq.registerMessage(SyncEvents.class, SyncEvents.class, 1, Side.SERVER);
        // Register packet for clients to tell server to update a block (e.g. for lighting)
        // Unused for the moment
        //packetReq.registerMessage(DoBlockUpdate.class, DoBlockUpdate.class, 2, Side.SERVER);
    }
}
