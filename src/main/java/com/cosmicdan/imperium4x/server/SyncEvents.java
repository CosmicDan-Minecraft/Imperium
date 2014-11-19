package com.cosmicdan.imperium4x.server;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import com.cosmicdan.imperium4x.Main;
import com.cosmicdan.imperium4x.data.player.ImperiumPlayer;
import com.cosmicdan.imperium4x.eventhandlers.WorldTickEvents;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

/*
 * this network packet is responsible for sending
 * player properties between server and client
 * It's bidirectional!
 */
public class SyncEvents implements IMessage, IMessageHandler<SyncEvents, IMessage> {

    public String eventListDone;
    public String eventListPending;
    public String eventListPendingInstant;
    
    public SyncEvents() {}

    public SyncEvents(EntityPlayer player) {
        ImperiumPlayer playerImperium = ImperiumPlayer.get(player);
        eventListDone = playerImperium.eventListDone.toString();
        eventListPending = playerImperium.eventListPending.toString();
        eventListPendingInstant = playerImperium.eventListPendingInstant.toString();
    }

    // fromBytes loads the packet data into the instance
    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound events = ByteBufUtils.readTag(buf);
        eventListDone = events.getString("eventListDone");
        eventListPending = events.getString("eventListPending");
        eventListPendingInstant = events.getString("eventListPendingInstant");
    }

    // toBytes is for building the packet data
    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound events = new NBTTagCompound();
        events.setString("eventListDone", this.eventListDone);
        events.setString("eventListPending", this.eventListPending);
        events.setString("eventListPendingInstant", this.eventListPendingInstant);
        ByteBufUtils.writeTag(buf, events);
    }

    // onMessage is called after the data is received i.e. we "assign" the packet/message data here
    @Override
    public IMessage onMessage(SyncEvents msg, MessageContext ctx) {
            EntityPlayer player = Main.proxy.getPlayerFromMessageContext(ctx);
            if ( player != null) {
                ImperiumPlayer playerImperium = ImperiumPlayer.get(player);
                if (playerImperium != null ) {
                    if (ctx.side == Side.CLIENT) {
                        // we only want to update eventsDone for the client (i.e. server sending to players)
                        playerImperium.eventListDone = ImperiumPlayer.stringToArrayList(msg.eventListDone);
                    } else {
                        // We only want to update pending events for the server (i.e. client needs to update server).
                        // For some reason there is no need to update playerImperium props for client sends to the server, no idea why. 
                        // Could cause trouble down the line if there are duplicates and the server is overloaded.
                        if (msg.eventListPendingInstant.length() > 2) {
                            if (WorldTickEvents.addPlayerToPendingInstants((EntityPlayerMP)player)) {
                                WorldTickEvents.eventPendingInstant = true;
                            }
                        }
                    }
                }
            }
        return null;
    }
}
