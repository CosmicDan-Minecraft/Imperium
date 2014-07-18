package com.cosmicdan.minecraftempires;

import java.util.List;
import java.util.Random;

import com.cosmicdan.minecraftempires.blocks.BlockBrushwood;
import com.cosmicdan.minecraftempires.blocks.ModBlocks;
import com.cosmicdan.minecraftempires.eventhandlers.BlockEvents;
import com.cosmicdan.minecraftempires.eventhandlers.PlayerEvents;
import com.cosmicdan.minecraftempires.eventhandlers.EventHandlerRestrictions;
import com.cosmicdan.minecraftempires.items.ItemBrushwood;
import com.cosmicdan.minecraftempires.items.ItemPlayerLog;
import com.cosmicdan.minecraftempires.items.ModItems;
import com.cosmicdan.minecraftempires.recipes.ShapelessRecipes;
import com.cosmicdan.minecraftempires.server.PacketHandler;
import com.google.common.eventbus.Subscribe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "minecraftempires";
    public static final String VERSION = "0.0.1";
    
    @Instance(value = MODID)
    public static Main instance;
    
    @SidedProxy(clientSide="com.cosmicdan.minecraftempires.client.ClientProxy", serverSide="com.cosmicdan.minecraftempires.server.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // blocks/items
        ModBlocks.addBlocks();
        ModItems.addItems();

        // recipes
        ShapelessRecipes.addAll();

        // world generation
        GameRegistry.registerWorldGenerator(new WorldGen(), 1);

        // hook events for restrictions
        MinecraftForge.EVENT_BUS.register(new EventHandlerRestrictions());

        // hook events for blocks
        MinecraftForge.EVENT_BUS.register(new BlockEvents());

        // initialize the packet handler
        PacketHandler.init();

        // initialize the proxy
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new PlayerEvents());
        proxy.init(event);
        //System.out.println(MODID + " v" + VERSION);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        // something
    }
    
    /*
    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        // Save & Quit for integrated server only
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            // get server instance
            MinecraftServer mc = FMLClientHandler.instance().getServer();
            // get player list
            String allNames[] = mc.getAllUsernames().clone();
            // loop over all players
            for(int i = 0; i < allNames.length; i++) {
                // func_152612_a = getPlayerForUsername
                EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(allNames[i]);
            }
        }
    }
    */
    
}
