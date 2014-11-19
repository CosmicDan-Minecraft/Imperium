package com.cosmicdan.imperium4x.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiCompass extends Gui {
    private Minecraft mc;
    private ResourceLocation compassTexture;
    private ResourceLocation compassTextureMask;
    
    public GuiCompass(Minecraft mc) {
      super();
      this.mc = mc;
      this.compassTexture = new ResourceLocation("imperium4x:textures/gui/compass_1.png");
      this.compassTextureMask = new ResourceLocation("imperium4x:textures/gui/compass_1_mask.png");
    }
    
    @SubscribeEvent
    public void compassRender(RenderGameOverlayEvent.Post event) {
        // ensure we are rendering at the right time
        // TODO: Also ensure playerImperium.hasCompass, this will be very set early in the tutorial
        if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {      
            return;
        }
        
        // TODO: Config for refresh-rate (all this math is relatively costly)
        
        EntityPlayer player = mc.thePlayer;
        int screenWidth = event.resolution.getScaledWidth();
        int screenHeight = event.resolution.getScaledHeight();
        int screenScale = event.resolution.getScaleFactor();
        TextureManager re = mc.renderEngine;
        
        // Gets a cardinal direction of the player (4 points), starting at south=0 and ending with east=3 (aka vanilla debug screen
        int direction4 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        // Gets a principal wind direction (8 points), starting at south=0 and ending with south-east=7 (South, South-West, West, etc)
        //int direction8 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7;
        // Gets a half-wind direction (16 points), starting at south=0 and ending with South-southeast=15
        int direction16 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
        // Gets the player direction in pure bearing, relative to south and going clockwise (0=south, 90=west, etc.)
        int directionBearing = MathHelper.floor_double((double)MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw));
        if (directionBearing < 0) directionBearing += 360;
        
        // prepare vars for drawing
        int compassTexWidth = 360;
        int compassTexHeight = 64;
        // scale multiplier (corresponds to glScaled later) 
        int scaleMulti = 2;
        // viewport limiter is what factor the display is "narrowed" by..
        double viewportLimiter = 2.2D;
        int compassXStart = (int) ((screenWidth * scaleMulti - (compassTexWidth / viewportLimiter)) / 2);
        int compassYStart = 8;
        int compassUStart = (int) (directionBearing + 45 + (45 * viewportLimiter));
        
        // store the current matrix parameters
        GL11.glPushMatrix();
        
        // do the scale scale
        GL11.glScaled(0.5D, 0.5D, 1.0D);

        
        // draw the compass
        re.bindTexture(compassTexture);
        drawTexturedRect(compassXStart, compassYStart, compassUStart, 0, (int) (compassTexWidth / viewportLimiter), compassTexHeight, compassTexWidth, compassTexHeight);
        
        // reset matrix scale
        GL11.glPopMatrix();
    }
    
    public void drawTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        float f = 1F / (float)textureWidth;
        float f1 = 1F / (float)textureHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x), (double)(y + height), 0, (double)((float)(u) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), 0, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y), 0, (double)((float)(u + width) * f), (double)((float)(v) * f1));
        tessellator.addVertexWithUV((double)(x), (double)(y), 0, (double)((float)(u) * f), (double)((float)(v) * f1));
        tessellator.draw();
    }
}
