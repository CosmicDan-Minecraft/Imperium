package com.cosmicdan.imperium4x.server;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

/*
 * This packet is responsible for client-side renderers and GUI's asking the server to
 * update a block (and it's neighbors, depending on updateType requested):
 * 0 = render update (with range of 12)
 */
public class DoBlockUpdate implements IMessage, IMessageHandler<DoBlockUpdate, IMessage>  {

    private int posX;
    private int posY;
    private int posZ;
    private short updateType;
    
    public DoBlockUpdate() {}
    
    public DoBlockUpdate(int posX, int posY, int posZ, short updateType) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.updateType = updateType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        posX = buf.readInt();
        posY = buf.readInt();
        posZ = buf.readInt();
        updateType = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeShort(updateType);
    }

    @Override
    public IMessage onMessage(DoBlockUpdate msg, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            if (msg.updateType == 0) {
                ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(msg.posX, msg.posY, msg.posZ);
            }
        }
        return null;
    }
    
    public static void sync(int posX, int posY, int posZ, short updateType) {
        PacketHandler.packetReq.sendToServer(new DoBlockUpdate(posX, posY, posZ, updateType));
    }
}
