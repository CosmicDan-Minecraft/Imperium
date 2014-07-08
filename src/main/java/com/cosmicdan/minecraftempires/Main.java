package com.cosmicdan.minecraftempires;

import java.util.List;
import java.util.Random;

import com.cosmicdan.minecraftempires.blocks.BlockBrushwood;
import com.cosmicdan.minecraftempires.datamanagement.PlayerData;
import com.cosmicdan.minecraftempires.eventhandlers.BlockEvents;
import com.cosmicdan.minecraftempires.eventhandlers.PlayerEvents;
import com.cosmicdan.minecraftempires.eventhandlers.EventHandlerRestrictions;
import com.cosmicdan.minecraftempires.items.ItemBrushwood;
import com.cosmicdan.minecraftempires.items.ItemJournal;
import com.cosmicdan.minecraftempires.recipes.ShapelessRecipes;
import com.google.common.eventbus.Subscribe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
    
    // new blocks/items
    public static Block blockBrushwood;
    public static Item itemBrushwood;
    public static Item itemJournal;
    
    @Instance(value = MODID)
    public static Main instance;
    
    @SidedProxy(clientSide="com.cosmicdan.minecraftempires.client.ClientProxy", serverSide="com.cosmicdan.minecraftempires.server.ServerProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // blocks/items
        blockBrushwood = new BlockBrushwood();
        GameRegistry.registerBlock(blockBrushwood, "blockBrushwood");
        itemBrushwood = new ItemBrushwood();
        GameRegistry.registerItem(itemBrushwood, "itemBrushwood");
        itemJournal = new ItemJournal();
        GameRegistry.registerItem(itemJournal, "itemJournal");
        
        // recipes
        ShapelessRecipes.RecipeShelter();
        
        // world generation
        GameRegistry.registerWorldGenerator(new WorldGen(), 1);
        
        // hook events for restrictions
        MinecraftForge.EVENT_BUS.register(new EventHandlerRestrictions());
        
        // hook events for blocks
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new PlayerEvents());
        proxy.init(event);
        //System.out.println(MODID + " v" + VERSION);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
            // something
    }
    
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
                PlayerData.savePlayerData((EntityPlayerMP)player);
            }
        }
    }
    
    
}
