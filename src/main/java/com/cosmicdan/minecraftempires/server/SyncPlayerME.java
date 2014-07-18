package com.cosmicdan.minecraftempires.server;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.eventhandlers.WorldTickEvents;
import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;

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
public class SyncPlayerME implements IMessage, IMessageHandler<SyncPlayerME, IMessage> {

    public String eventListDone;
    public String eventListPending;
    public String eventListPendingInstant;
    
    public SyncPlayerME() {
        
    }

    public SyncPlayerME(EntityPlayer player) {
        MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get(player);
        eventListDone = playerME.eventListDone.toString();
        eventListPending = playerME.eventListPending.toString();
        eventListPendingInstant = playerME.eventListPendingInstant.toString();
    }

    // fromBytes loads the data into the class ready for sending
    @Override
    public void fromBytes(ByteBuf buf) {
        eventListDone = ByteBufUtils.readUTF8String(buf);
        eventListPending = ByteBufUtils.readUTF8String(buf);
        eventListPendingInstant = ByteBufUtils.readUTF8String(buf);
    }

    // toBytes is for sending a packet/request (in this case, server to the client)
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.eventListDone);
        ByteBufUtils.writeUTF8String(buf, this.eventListPending);
        ByteBufUtils.writeUTF8String(buf, this.eventListPendingInstant);
    }

    // onMessage is called after the data is received i.e. we "assign" the packet/message data here
    @Override
    public IMessage onMessage(SyncPlayerME msg, MessageContext ctx) {
            EntityPlayer player = Main.proxy.getPlayerFromMessageContext(ctx);
            if ( player != null) {
                MinecraftEmpiresPlayer playerME = MinecraftEmpiresPlayer.get(player);
                if (playerME != null ) {
                    if (ctx.side == Side.CLIENT) {
                        // we only want to update eventsDone for the client (i.e. server sending to players)
                        playerME.eventListDone = MinecraftEmpiresPlayer.stringToArrayList(msg.eventListDone);
                    } else {
                        // We only want to update pending events for the server (i.e. client needs to update server).
                        // For some reason there is no need to update playerME props for client calls to server, no idea why. 
                        // Could cause trouble down the line if there are duplicates and the server is loaded.
                        
                        /*
                        if (msg.eventListPending.length() > 2) {
                            
                        }
                        */

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
