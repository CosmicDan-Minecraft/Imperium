package com.cosmicdan.imperium4x.entities.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.cosmicdan.imperium4x.blocks.ModBlocks;



public class TileEntityCampfireRenderer extends TileEntitySpecialRenderer {

    //private static final ResourceLocation spitrod = new ResourceLocation("imperium4x:textures/blocks/campfire_spitrod.png");
    private Tessellator tessellator; 
    private IIcon spitrod;
    private float spitrodMap[] = new float[4];
    private double transX;
    private double transY;
    private double transZ;
    private float transScale;
    private float u;
    private float v;
    private float U;
    private float V;
    
    public TileEntityCampfireRenderer() {
        spitrod = ModBlocks.campfireLit.getIcon(1, 7);
        spitrodMap[0] = spitrod.getMinU();
        spitrodMap[1] = spitrod.getMinV();
        spitrodMap[2] = spitrod.getMaxU();
        spitrodMap[3] = spitrod.getMaxV();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double dx, double dy, double dz, float f) {
        renderThisTileEntity((TileEntityCampfire) tileEntity, dx, dy, dz, f);
    }
    
    public void renderThisTileEntity(TileEntityCampfire tileEntity, double dx, double dy, double dz, float f) {
        if (tileEntity.metadata > 6) { // has food in inventory, draw the spit and items
            tessellator = Tessellator.instance;
            
            // draw the spit rod
            transY = dy + 0.9375D;
            transZ = dz + 0.5078125D;
            transScale = 0.25F;
            bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(0));
            GL11.glPushMatrix();
            GL11.glTranslated(dx, transY, transZ);
            GL11.glScalef(transScale, transScale, transScale);
            ItemRenderer.renderItemIn2D(tessellator, spitrodMap[2], spitrodMap[1], spitrodMap[0], spitrodMap[3], 16, 16, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(dx + 0.25D, transY, transZ);
            GL11.glScalef(transScale, transScale, transScale);
            ItemRenderer.renderItemIn2D(tessellator, spitrodMap[2], spitrodMap[1], spitrodMap[0], spitrodMap[3], 16, 16, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(dx + 0.5D, transY, transZ);
            GL11.glScalef(transScale, transScale, transScale);
            ItemRenderer.renderItemIn2D(tessellator, spitrodMap[2], spitrodMap[1], spitrodMap[0], spitrodMap[3], 16, 16, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(dx + 0.75D, transY, transZ);
            GL11.glScalef(transScale, transScale, transScale);
            ItemRenderer.renderItemIn2D(tessellator, spitrodMap[2], spitrodMap[1], spitrodMap[0], spitrodMap[3], 16, 16, 0.0625F);
            GL11.glPopMatrix();
            
            bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(1));
            transY = dy + 1.0D;
            transZ = dz + 0.6D;
            transScale = 0.3125F;
        
            for (int i = 0; i < tileEntity.itemSlotStatus.length; i++) {
                if (tileEntity.itemSlotStatus[i] > 0) {
                    GL11.glPushMatrix();
                    switch (i){
                        case 0: transX = dx + 0.50D; break;
                        case 1: transX = dx + 0.30D; break;
                        case 2: transX = dx + 0.70D; break;
                        case 3: transX = dx + 0.10D; break;
                    }
                    drawItem(tileEntity.itemSlot[i]);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    private void drawItem(ItemStack itemStack) {
        //itemIcon = Items.beef.getIconFromDamage(0);
        if (itemStack == null) return;
        IIcon itemIcon = itemStack.getIconIndex();
        GL11.glTranslated(transX, transY, transZ);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        u = itemIcon.getMinU();
        v = itemIcon.getMinV();
        U = itemIcon.getMaxU();
        V = itemIcon.getMaxV();
        GL11.glScalef(transScale, transScale, transScale);
        ItemRenderer.renderItemIn2D(tessellator, U, v, u, V, itemIcon.getIconWidth(), itemIcon.getIconHeight(), 0.0625F);
    }
}

