package com.cosmicdan.minecraftempires.client.renderers;

import com.cosmicdan.minecraftempires.server.DoBlockUpdate;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class RenderCampfire extends RendererCommon implements ISimpleBlockRenderingHandler {
    
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        // nothing here
        return;
    }

    public boolean renderWorldBlock(IBlockAccess blockAccess, int posX, int posY, int posZ, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon;
        float u;
        float v;
        float U;
        float V;
        // start drawing
        tessellator.addTranslation(posX, posY, posZ);
        // No idea what this is for but it fixes messed up color tint
        tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ));
        // check metadata to see if it's a lit campfire
        int blockMeta = blockAccess.getBlockMetadata(posX, posY, posZ);
        if (blockMeta > 0) {
            // is on fire, draw the fire
            iicon = Blocks.fire.getIcon(0, 0);
            u = iicon.getMinU();
            v = iicon.getMinV();
            U = iicon.getMaxU();
            V = iicon.getMaxV();
            tessellator.addVertexWithUV(1, 0, 1, U, V);
            tessellator.addVertexWithUV(1, 1, 1, U, v);
            tessellator.addVertexWithUV(0, 1, 0, u, v);
            tessellator.addVertexWithUV(0, 0, 0, u, V);
            // 2-sided
            tessellator.addVertexWithUV(0, 0, 0, u, V);
            tessellator.addVertexWithUV(0, 1, 0, u, v);
            tessellator.addVertexWithUV(1, 1, 1, U, v);
            tessellator.addVertexWithUV(1, 0, 1, U, V);
            // now the other axis
            tessellator.addVertexWithUV(1, 0, 0, U, V);
            tessellator.addVertexWithUV(1, 1, 0, U, v);
            tessellator.addVertexWithUV(0, 1, 1, u, v);
            tessellator.addVertexWithUV(0, 0, 1, u, V);
            // also 2-sided
            tessellator.addVertexWithUV(0, 0, 1, u, V);
            tessellator.addVertexWithUV(0, 1, 1, u, v);
            tessellator.addVertexWithUV(1, 1, 0, U, v);
            tessellator.addVertexWithUV(1, 0, 0, U, V);
            // check for cooking
            if (blockMeta == 2) {
                // DODGY CODE WARNING
                // draw spit struts
                iicon = block.getIcon(0, 2);
                u = iicon.getMinU();
                v = iicon.getMinV();
                U = iicon.getMaxU();
                V = iicon.getMaxV();
                // east strut
                tessellator.addVertexWithUV(1, 0, 0, U, V);
                tessellator.addVertexWithUV(1, 1.3, 0, U, v);
                tessellator.addVertexWithUV(1, 1.3, 1, u, v);
                tessellator.addVertexWithUV(1, 0, 1, u, V);
                // 2-sided
                tessellator.addVertexWithUV(1, 0, 1, u, V);
                tessellator.addVertexWithUV(1, 1.3, 1, u, v);
                tessellator.addVertexWithUV(1, 1.3, 0, U, v);
                tessellator.addVertexWithUV(1, 0, 0, U, V);
                // west strut
                tessellator.addVertexWithUV(0, 0, 0, U, V);
                tessellator.addVertexWithUV(0, 1.3, 0, U, v);
                tessellator.addVertexWithUV(0, 1.3, 1, u, v);
                tessellator.addVertexWithUV(0, 0, 1, u, V);
                // also 2-sided
                tessellator.addVertexWithUV(0, 0, 1, u, V);
                tessellator.addVertexWithUV(0, 1.3, 1, u, v);
                tessellator.addVertexWithUV(0, 1.3, 0, U, v);
                tessellator.addVertexWithUV(0, 0, 0, U, V);
            }
        }
        
        // southern face (starting bottom-right corner, going anti-clockwise)
            iicon = block.getIcon(0, 0);
            u = iicon.getMinU();
            v = iicon.getMinV();
            U = iicon.getMaxU();
            V = iicon.getMaxV();
            tessellator.addVertexWithUV(1, 0, 1, U, V);
            tessellator.addVertexWithUV(1, 1, 0.25, U, v);
            tessellator.addVertexWithUV(0, 1, 0.25, u, v);
            tessellator.addVertexWithUV(0, 0, 1, u, V);
            // double-sided
            tessellator.addVertexWithUV(0, 0, 1, u, V);
            tessellator.addVertexWithUV(0, 1, 0.25, u, v);
            tessellator.addVertexWithUV(1, 1, 0.25, U, v);
            tessellator.addVertexWithUV(1, 0, 1, U, V);
        // western face
            iicon = block.getIcon(1, 0);
            u = iicon.getMinU();
            v = iicon.getMinV();
            U = iicon.getMaxU();
            V = iicon.getMaxV();
            tessellator.addVertexWithUV(0, 0, 1, U, V);
            tessellator.addVertexWithUV(0.75, 1, 1, U, v);
            tessellator.addVertexWithUV(0.75, 1, 0, u, v);
            tessellator.addVertexWithUV(0, 0, 0, u, V);
            // double-sided
            tessellator.addVertexWithUV(0, 0, 0, u, V);
            tessellator.addVertexWithUV(0.75, 1, 0, u, v);
            tessellator.addVertexWithUV(0.75, 1, 1, U, v);
            tessellator.addVertexWithUV(0, 0, 1, U, V);
        // northern face
            iicon = block.getIcon(2, 0);
            u = iicon.getMinU();
            v = iicon.getMinV();
            U = iicon.getMaxU();
            V = iicon.getMaxV();
            tessellator.addVertexWithUV(0, 0, 0, U, V);
            tessellator.addVertexWithUV(0, 1, 0.75, U, v);
            tessellator.addVertexWithUV(1, 1, 0.75, u, v);
            tessellator.addVertexWithUV(1, 0, 0, u, V);
            // double-sided
            tessellator.addVertexWithUV(1, 0, 0, u, V);
            tessellator.addVertexWithUV(1, 1, 0.75, u, v);
            tessellator.addVertexWithUV(0, 1, 0.75, U, v);
            tessellator.addVertexWithUV(0, 0, 0, U, V);
        // eastern face
            iicon = block.getIcon(3, 0);
            u = iicon.getMinU();
            v = iicon.getMinV();
            U = iicon.getMaxU();
            V = iicon.getMaxV();
            tessellator.addVertexWithUV(1, 0, 0, U, V);
            tessellator.addVertexWithUV(0.25, 1, 0, U, v);
            tessellator.addVertexWithUV(0.25, 1, 1, u, v);
            tessellator.addVertexWithUV(1, 0, 1, u, V);
            // double-sided
            tessellator.addVertexWithUV(1, 0, 1, u, V);
            tessellator.addVertexWithUV(0.25, 1, 1, u, v);
            tessellator.addVertexWithUV(0.25, 1, 0, U, v);
            tessellator.addVertexWithUV(1, 0, 0, U, V);
        // end drawing
        tessellator.addTranslation(-posX, -posY, -posZ);
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return ModRenderers.rendererStickShelter;
    }
}
