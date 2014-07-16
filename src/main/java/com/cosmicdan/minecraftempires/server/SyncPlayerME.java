package com.cosmicdan.minecraftempires.server;

import java.util.ArrayList;

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

    //private EntityPlayer player;
    //private EntityPlayerME player;
    //private String playerName;
    private EntityPlayerME playerME;
    public String eventListDone;
    
    public SyncPlayerME() {
        
    }

    public SyncPlayerME(EntityPlayer player) {
        EntityPlayerMP thisPlayer = FMLClientHandler.instance().getServer().getConfigurationManager().func_152612_a(player.getDisplayName()); // not much point in this
        EntityPlayerME playerME = EntityPlayerME.get(player); // obviously this doesn't work 
        //eventListDone = playerME.eventListDone.toString();
        //eventListDone = playerME.eventListDone.toString();

    }

    // fromBytes handles the response of a request (in this case, what the server sent back)
    @Override
    public void fromBytes(ByteBuf buf) {
        //eventListDone = ByteBufUtils.readUTF8String(buf);
    }

    // toBytes is for sending a request (in this case, from client to server)
    @Override
    public void toBytes(ByteBuf buf) {
        //ByteBufUtils.writeUTF8String(buf, eventListDone);
    }

    // onMessage is called after a response is received, i.e. we can do things with the response here
    @Override
    public IMessage onMessage(SyncPlayerME msg, MessageContext ctx) {
        //System.out.println(String.format("Received %s from %s", msg.playerName, ctx.getServerHandler().playerEntity.getDisplayName()));
        //if (ctx.side == Side.SERVER) {
            //EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            playerME = EntityPlayerME.get(ctx.getServerHandler().playerEntity);
            eventListDone = playerME.eventListDone.toString();
        //}
        return null;
    }
}
