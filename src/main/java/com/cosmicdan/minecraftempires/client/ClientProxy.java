package com.cosmicdan.minecraftempires.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;

import com.cosmicdan.minecraftempires.CommonProxy;
import com.cosmicdan.minecraftempires.client.gui.GuiLog;
import com.cosmicdan.minecraftempires.client.renderers.ModRenderers;
import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // register custom renderers
        ModRenderers.registerAll();
    }
    
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
    
    @Override
    public void openJournal(EntityPlayer player) {
        // client-only hook command for when user presses right-click on their Journal (called from the Journal item class)
        // remember - getMinecraft() only exists in the client!
        Minecraft.getMinecraft().displayGuiScreen(new GuiLog(player));
    }
    
    @Override
    public EntityPlayer getPlayerFromMessageContext(MessageContext ctx) {
        switch(ctx.side) {
            case CLIENT:
                EntityPlayer entityClientPlayerMP = Minecraft.getMinecraft().thePlayer;
                return entityClientPlayerMP;
            case SERVER:
                EntityPlayer entityPlayerMP = ctx.getServerHandler().playerEntity;
                return entityPlayerMP;
            default:
                assert false : "Invalid side in TestMsgHandler: " + ctx.side;
        }
        return null;
    }
}