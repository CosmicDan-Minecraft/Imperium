package com.cosmicdan.imperium4x.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class RendererCommon {
    
    public IIcon overrideBlockTexture;
    public double renderMinX;
    public double renderMaxX;
    public double renderMinY;
    public double renderMaxY;
    public double renderMinZ;
    public double renderMaxZ;
    public boolean flipTexture;
    // flipTexture again?
    public boolean field_152631_f;
    public int uvRotateEast;
    public int uvRotateWest;
    public int uvRotateSouth;
    public int uvRotateNorth;
    public int uvRotateTop;
    public int uvRotateBottom;
    public boolean renderFromInside = false;
    /** Whether ambient occlusion is enabled or not */
    public boolean enableAO;
    public float colorRedTopLeft;
    public float colorRedBottomLeft;
    public float colorRedBottomRight;
    public float colorRedTopRight;
    public float colorGreenTopLeft;
    public float colorGreenBottomLeft;
    public float colorGreenBottomRight;
    public float colorGreenTopRight;
    public float colorBlueTopLeft;
    public float colorBlueBottomLeft;
    public float colorBlueBottomRight;
    public float colorBlueTopRight;
    public int brightnessTopLeft;
    public int brightnessBottomLeft;
    public int brightnessBottomRight;
    public int brightnessTopRight;
    public boolean partialRenderBounds;
    public boolean lockBlockBounds;
    public boolean renderAllFaces = false;
    
    
    public void setRenderBoundsFromBlock(Block p_147775_1_)
    {
        if (!this.lockBlockBounds)
        {
            this.renderMinX = p_147775_1_.getBlockBoundsMinX();
            this.renderMaxX = p_147775_1_.getBlockBoundsMaxX();
            this.renderMinY = p_147775_1_.getBlockBoundsMinY();
            this.renderMaxY = p_147775_1_.getBlockBoundsMaxY();
            this.renderMinZ = p_147775_1_.getBlockBoundsMinZ();
            this.renderMaxZ = p_147775_1_.getBlockBoundsMaxZ();
            this.partialRenderBounds = Minecraft.getMinecraft().gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0D || this.renderMaxX < 1.0D || this.renderMinY > 0.0D || this.renderMaxY < 1.0D || this.renderMinZ > 0.0D || this.renderMaxZ < 1.0D);
        }
    }
    
    /**
     * Sets overrideBlockTexture
     */
    public void setOverrideBlockTexture(IIcon p_147757_1_)
    {
        this.overrideBlockTexture = p_147757_1_;
    }
    
    /**
     * Clear override block texture
     */
    public void clearOverrideBlockTexture()
    {
        this.overrideBlockTexture = null;
    }

    public boolean hasOverrideBlockTexture()
    {
        return this.overrideBlockTexture != null;
    }
    
    /**
     * Renders the given texture to the bottom face of the block. Args: block, x, y, z, texture, underside
     */
    public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147768_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147768_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0D);
        double d6 = (double)p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0D);

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147768_8_.getMinU();
            d4 = (double)p_147768_8_.getMaxU();
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d5 = (double)p_147768_8_.getMinV();
            d6 = (double)p_147768_8_.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateBottom == 2)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateBottom == 1)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateBottom == 3)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147768_2_ + this.renderMinX;
        double d12 = p_147768_2_ + this.renderMaxX;
        double d13 = p_147768_4_ + this.renderMinY;
        double d14 = p_147768_6_ + this.renderMinZ;
        double d15 = p_147768_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147768_2_ + this.renderMaxX;
            d12 = p_147768_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            else
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            else
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            } else {
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            }
        }
    }

    /**
     * Renders the given texture to the top face of the block. Args: block, x, y, z, texture, underside
     */
    public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147806_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147806_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0D);
        double d6 = (double)p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0D);

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147806_8_.getMinU();
            d4 = (double)p_147806_8_.getMaxU();
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d5 = (double)p_147806_8_.getMinV();
            d6 = (double)p_147806_8_.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateTop == 1)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateTop == 2)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateTop == 3)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147806_2_ + this.renderMinX;
        double d12 = p_147806_2_ + this.renderMaxX;
        double d13 = p_147806_4_ + this.renderMaxY;
        double d14 = p_147806_6_ + this.renderMinZ;
        double d15 = p_147806_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147806_2_ + this.renderMaxX;
            d12 = p_147806_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            else
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            } else {
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            }
        }
    }

    /**
     * Renders the given texture to the north (z-negative) face of the block.  Args: block, x, y, z, texture, underside
     */
    public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, IIcon p_147761_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147761_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147761_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0D);

        if (this.field_152631_f)
        {
            d4 = (double)p_147761_8_.getInterpolatedU((1.0D - this.renderMinX) * 16.0D);
            d3 = (double)p_147761_8_.getInterpolatedU((1.0D - this.renderMaxX) * 16.0D);
        }

        double d5 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147761_8_.getMinU();
            d4 = (double)p_147761_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147761_8_.getMinV();
            d6 = (double)p_147761_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateEast == 2)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateEast == 1)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateEast == 3)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147761_2_ + this.renderMinX;
        double d12 = p_147761_2_ + this.renderMaxX;
        double d13 = p_147761_4_ + this.renderMinY;
        double d14 = p_147761_4_ + this.renderMaxY;
        double d15 = p_147761_6_ + this.renderMinZ;

        if (this.renderFromInside)
        {
            d11 = p_147761_2_ + this.renderMaxX;
            d12 = p_147761_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            else
                tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            else
                tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
                tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
                tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
            } else {
                tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
                tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
                tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            }
        }
    }

    /**
     * Renders the given texture to the south (z-positive) face of the block.  Args: block, x, y, z, texture, underside
     */
    public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147734_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147734_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147734_8_.getMinU();
            d4 = (double)p_147734_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147734_8_.getMinV();
            d6 = (double)p_147734_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateWest == 1)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateWest == 2)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateWest == 3)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147734_2_ + this.renderMinX;
        double d12 = p_147734_2_ + this.renderMaxX;
        double d13 = p_147734_4_ + this.renderMinY;
        double d14 = p_147734_4_ + this.renderMaxY;
        double d15 = p_147734_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147734_2_ + this.renderMaxX;
            d12 = p_147734_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            else
                tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            else
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
                tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
            } else {
                tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
                tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
                tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
                tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            }
        }
    }

    /**
     * Renders the given texture to the west (x-negative) face of the block.  Args: block, x, y, z, texture, underside
     */
    public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147798_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0D);
        double d4 = (double)p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
        double d5 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d3 = (double)p_147798_8_.getMinU();
            d4 = (double)p_147798_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147798_8_.getMinV();
            d6 = (double)p_147798_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateNorth == 1)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateNorth == 2)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateNorth == 3)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147798_2_ + this.renderMinX;
        double d12 = p_147798_4_ + this.renderMinY;
        double d13 = p_147798_4_ + this.renderMaxY;
        double d14 = p_147798_6_ + this.renderMinZ;
        double d15 = p_147798_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d14 = p_147798_6_ + this.renderMaxZ;
            d15 = p_147798_6_ + this.renderMinZ;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            else
                tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            else
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
                tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
            } else {
                tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
                tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
                tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
                tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            }
        }
    }

    /**
     * Renders the given texture to the east (x-positive) face of the block.  Args: block, x, y, z, texture, underside
     */
    public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_, boolean underside)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.hasOverrideBlockTexture())
        {
            p_147764_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0D);
        double d4 = (double)p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0D);

        if (this.field_152631_f)
        {
            d4 = (double)p_147764_8_.getInterpolatedU((1.0D - this.renderMinZ) * 16.0D);
            d3 = (double)p_147764_8_.getInterpolatedU((1.0D - this.renderMaxZ) * 16.0D);
        }

        double d5 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d3 = (double)p_147764_8_.getMinU();
            d4 = (double)p_147764_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147764_8_.getMinV();
            d6 = (double)p_147764_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateSouth == 2)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateSouth == 1)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateSouth == 3)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147764_2_ + this.renderMaxX;
        double d12 = p_147764_4_ + this.renderMinY;
        double d13 = p_147764_4_ + this.renderMaxY;
        double d14 = p_147764_6_ + this.renderMinZ;
        double d15 = p_147764_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d14 = p_147764_6_ + this.renderMaxZ;
            d15 = p_147764_6_ + this.renderMinZ;
        }

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            else
                tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            if (!underside)
                tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            else
                tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            else
                tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            if (!underside)
                tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
            else
                tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
        }
        else
        {
            if (!underside) {
                tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
                tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
                tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
            } else {
                tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
                tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
                tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
                tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            }
        }
    }
}
