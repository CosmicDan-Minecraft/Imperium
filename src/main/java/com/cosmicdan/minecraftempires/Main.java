package com.cosmicdan.minecraftempires;

import java.util.Random;

import com.cosmicdan.minecraftempires.blocks.BlockBrushwood;
import com.cosmicdan.minecraftempires.eventhandlers.BlockBreakHandler;
import com.cosmicdan.minecraftempires.items.ItemBrushwood;
import com.cosmicdan.minecraftempires.items.ItemJournal;
import com.cosmicdan.minecraftempires.recipes.ShapelessRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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
        
        GameRegistry.registerWorldGenerator(new WorldGen(), 1);
        MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        //System.out.println(MODID + " v" + VERSION);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
    
    public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
        //BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(chunkX, j);
        // the roof number here determines the frequency (higher is more common)
        for(int k = 0; k < 32; k++) {
            int randPosX = chunkX + random.nextInt(16);
            // maximum height to spawn
            int randPosY = random.nextInt(128);
            int randPosZ = chunkZ + random.nextInt(16);
            (new WorldGenFlowers(blockBrushwood)).generate(world, random, randPosX, randPosY, randPosZ);
        }
    }
    
}
