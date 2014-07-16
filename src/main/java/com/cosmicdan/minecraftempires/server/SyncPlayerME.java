package com.cosmicdan.minecraftempires.server;

import java.util.ArrayList;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SyncPlayerME implements IMessage, IMessageHandler<SyncPlayerME, IMessage> {

    public String eventListDone;
    
    public SyncPlayerME() {
        
    }

    public SyncPlayerME(EntityPlayer player) {
        EntityPlayerME playerME = EntityPlayerME.get(player);
        eventListDone = playerME.eventListDone.toString();
    }

    // fromBytes loads the data into the class ready for sending
    @Override
    public void fromBytes(ByteBuf buf) {
        eventListDone = ByteBufUtils.readUTF8String(buf);
    }

    // toBytes is for sending a packet/request (in this case, server to the client)
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.eventListDone);
    }

    // onMessage is called after the data is received i.e. we "assign" the packet/message data here
    @Override
    public IMessage onMessage(SyncPlayerME msg, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPlayer player = Main.proxy.getPlayerFromMessageContext(ctx);
            if ( player != null) {
                EntityPlayerME playerME = EntityPlayerME.get(player);
                if (playerME != null ) {
                    playerME.eventListDone = EntityPlayerME.stringToArrayList(msg.eventListDone);
                }
            }
        }
        return null;
    }
}
