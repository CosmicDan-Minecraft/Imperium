package com.cosmicdan.imperium4x.client.renderers;

import com.cosmicdan.imperium4x.blocks.BlockStickShelter;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public final class RenderStickShelter extends RendererCommon implements ISimpleBlockRenderingHandler {
    
    
    
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        // nothing here
        return;
    }

    public boolean renderWorldBlock(IBlockAccess blockAccess, int posX, int posY, int posZ, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        
        block.setBlockBoundsBasedOnState(blockAccess, posX, posY, posZ);
        this.setRenderBoundsFromBlock(block);
        
        
        //this.renderMinX = block.getBlockBoundsMinX();
        //this.renderMaxX = block.getBlockBoundsMaxX();
        //this.renderMinY = block.getBlockBoundsMinY();
        //this.renderMaxY = block.getBlockBoundsMaxY();
        //this.renderMinZ = block.getBlockBoundsMinZ();
        //this.renderMaxZ = block.getBlockBoundsMaxZ();
        this.renderAllFaces = true;
        
        
        Block bed = blockAccess.getBlock(posX, posY, posZ);
        int i1 = bed.getBedDirection(blockAccess, posX, posY, posZ);
        boolean flag = bed.isBedFoot(blockAccess, posX, posY, posZ);
        //int i1 = block.getBedDirection(blockAccess, posX, posY, posZ);
        //boolean flag = block.isBedFoot(blockAccess, posX, posY, posZ);
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        int j1 = block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ);
        tessellator.setBrightness(j1);
        tessellator.setColorOpaque_F(f, f, f);
        IIcon iicon = this.getBlockIcon(block, blockAccess, posX, posY, posZ, 0);
        //IIcon iicon = block.getIcon(blockAccess, posX, posY, posZ, 0);
        //if (hasOverrideBlockTexture()) iicon = overrideBlockTexture; //BugFix Proper breaking texture on underside
        double d0 = (double)iicon.getMinU();
        double d1 = (double)iicon.getMaxU();
        double d2 = (double)iicon.getMinV();
        double d3 = (double)iicon.getMaxV();
        double d4 = (double)posX + this.renderMinX;
        double d5 = (double)posX + this.renderMaxX;
        double d6 = (double)posY + this.renderMinY + 0.1875D;
        double d7 = (double)posZ + this.renderMinZ;
        double d8 = (double)posZ + this.renderMaxZ;
        // bottom (YNeg)?
        tessellator.addVertexWithUV(d4, d6, d8, d0, d3);
        tessellator.addVertexWithUV(d4, d6, d7, d0, d2);
        tessellator.addVertexWithUV(d5, d6, d7, d1, d2);
        tessellator.addVertexWithUV(d5, d6, d8, d1, d3);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, posX, posY + 1, posZ));
        tessellator.setColorOpaque_F(f1, f1, f1);
        //iicon = this.getBlockIcon(block, blockAccess, posX, posY, posZ, 1);
        iicon = block.getIcon(blockAccess, posX, posY, posZ, 1);
        //if (hasOverrideBlockTexture()) iicon = overrideBlockTexture; //BugFix Proper breaking texture on underside
        d0 = (double)iicon.getMinU();
        d1 = (double)iicon.getMaxU();
        d2 = (double)iicon.getMinV();
        d3 = (double)iicon.getMaxV();
        d4 = d0;
        d5 = d1;
        d6 = d2;
        d7 = d2;
        d8 = d0;
        double d9 = d1;
        double d10 = d3;
        double d11 = d3;

        if (i1 == 0) // west
        {
            d5 = d0;
            d6 = d3;
            d8 = d1;
            d11 = d2;
        }
        else if (i1 == 2) // east
        {
            d4 = d1;
            d7 = d3;
            d9 = d0;
            d10 = d2;
        }
        else if (i1 == 3) // south
        {
            d4 = d1;
            d7 = d3;
            d9 = d0;
            d10 = d2;
            d5 = d0;
            d6 = d3;
            d8 = d1;
            d11 = d2;
        }

        double xMin = (double)posX + this.renderMinX;
        double xMax = (double)posX + this.renderMaxX;
        double yMin = (double)posY + this.renderMinY;
        double yMax = (double)posY + this.renderMaxY;
        double zMin = (double)posZ + this.renderMinZ;
        double zMax = (double)posZ + this.renderMaxZ;
        
        if (i1 == 0) { // South
            tessellator.addVertexWithUV(xMax, yMin, zMax, d8, d10);
            tessellator.addVertexWithUV(xMax, yMin, zMin, d4, d6);
            tessellator.addVertexWithUV(xMin, yMax, zMin, d5, d7);
            tessellator.addVertexWithUV(xMin, yMax, zMax, d9, d11);
            // 2-sided
            tessellator.addVertexWithUV(xMin, yMax, zMax, d9, d11);
            tessellator.addVertexWithUV(xMin, yMax, zMin, d5, d7);
            tessellator.addVertexWithUV(xMax, yMin, zMin, d4, d6);
            tessellator.addVertexWithUV(xMax, yMin, zMax, d8, d10);
        } else if (i1 == 1) { // West
            tessellator.addVertexWithUV(xMax, yMax, zMax, d8, d10);
            tessellator.addVertexWithUV(xMax, yMin, zMin, d4, d6);
            tessellator.addVertexWithUV(xMin, yMin, zMin, d5, d7);
            tessellator.addVertexWithUV(xMin, yMax, zMax, d9, d11);
            // 2-sided
            tessellator.addVertexWithUV(xMin, yMax, zMax, d9, d11);
            tessellator.addVertexWithUV(xMin, yMin, zMin, d5, d7);
            tessellator.addVertexWithUV(xMax, yMin, zMin, d4, d6);
            tessellator.addVertexWithUV(xMax, yMax, zMax, d8, d10);
        } else if (i1 == 2) { // North
            tessellator.addVertexWithUV(xMax, yMax, zMax, d8, d10);
            tessellator.addVertexWithUV(xMax, yMax, zMin, d4, d6);
            tessellator.addVertexWithUV(xMin, yMin, zMin, d5, d7);
            tessellator.addVertexWithUV(xMin, yMin, zMax, d9, d11);
            // 2-sided
            tessellator.addVertexWithUV(xMin, yMin, zMax, d9, d11);
            tessellator.addVertexWithUV(xMin, yMin, zMin, d5, d7);
            tessellator.addVertexWithUV(xMax, yMax, zMin, d4, d6);
            tessellator.addVertexWithUV(xMax, yMax, zMax, d8, d10);
        } else { // East
            tessellator.addVertexWithUV(xMax, yMin, zMax, d8, d10);
            tessellator.addVertexWithUV(xMax, yMax, zMin, d4, d6);
            tessellator.addVertexWithUV(xMin, yMax, zMin, d5, d7);
            tessellator.addVertexWithUV(xMin, yMin, zMax, d9, d11);
            // 2-sided
            tessellator.addVertexWithUV(xMin, yMin, zMax, d9, d11);
            tessellator.addVertexWithUV(xMin, yMax, zMin, d5, d7);
            tessellator.addVertexWithUV(xMax, yMax, zMin, d4, d6);
            tessellator.addVertexWithUV(xMax, yMin, zMax, d8, d10);
        }
        
        
        int k1 = Direction.directionToFacing[i1];

        if (flag)
        {
            k1 = Direction.directionToFacing[Direction.rotateOpposite[i1]];
        }

        byte b0 = 4;

        switch (i1)
        {
            case 0:
                b0 = 5;
                break;
            case 1:
                b0 = 3;
            case 2:
            default:
                break;
            case 3:
                b0 = 2;
        }

        if (k1 != 2 && i1 != 1 && (this.renderAllFaces || block.shouldSideBeRendered(blockAccess, posX, posY, posZ - 1, 2))) {
            tessellator.setBrightness(this.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ - 1));
            tessellator.setColorOpaque_F(f2, f2, f2);
            this.flipTexture = (b0 == 2);
            this.renderFaceZNeg(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 2), false);
            //2-sided
            this.renderFaceZNeg(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 2), true);
        }

        if (k1 != 3 && i1 != 3 && (this.renderAllFaces || block.shouldSideBeRendered(blockAccess, posX, posY, posZ + 1, 3)))
        {
            tessellator.setBrightness(this.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ + 1));
            tessellator.setColorOpaque_F(f2, f2, f2);
            this.flipTexture = (b0 == 3);
            this.renderFaceZPos(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 3), false);
            //2-sided
            this.renderFaceZPos(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 3), true);

        }

        if (k1 != 4 && i1 != 2 && (this.renderAllFaces || block.shouldSideBeRendered(blockAccess, posX - 1, posY, posZ, 4)))
        {
            tessellator.setBrightness(this.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, posX - 1, posY, posZ));
            tessellator.setColorOpaque_F(f3, f3, f3);
            this.flipTexture = (b0 == 4 || b0 == 3 || b0 == 2);
            this.renderFaceXNeg(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 4), false);
            //2-sided
            this.renderFaceXNeg(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 4), true);
        }

        if (k1 != 5 && i1 != 0 && (this.renderAllFaces || block.shouldSideBeRendered(blockAccess, posX + 1, posY, posZ, 5)))
        {
            tessellator.setBrightness(this.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, posX + 1, posY, posZ));
            tessellator.setColorOpaque_F(f3, f3, f3);
            this.flipTexture = (b0 == 5 || b0 == 3 || b0 == 2);
            this.renderFaceXPos(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 5), false);
            //2-sided
            this.renderFaceXPos(block, (double)posX, (double)posY, (double)posZ, block.getIcon(blockAccess, posX, posY, posZ, 5), true);
        }
        

        this.flipTexture = false;
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return ModRenderers.rendererStickShelter;
    }
    
    
    public IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_)
    {
        return this.getIconSafe(p_147793_1_.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
    }
    
    public IIcon getBlockIconFromSideAndMetadata(Block p_147787_1_, int p_147787_2_, int p_147787_3_)
    {
        return this.getIconSafe(p_147787_1_.getIcon(p_147787_2_, p_147787_3_));
    }

    public IIcon getBlockIconFromSide(Block p_147777_1_, int p_147777_2_)
    {
        return this.getIconSafe(p_147777_1_.getBlockTextureFromSide(p_147777_2_));
    }

    public IIcon getBlockIcon(Block p_147745_1_)
    {
        return this.getIconSafe(p_147745_1_.getBlockTextureFromSide(1));
    }

    public IIcon getIconSafe(IIcon p_147758_1_)
    {
        if (p_147758_1_ == null)
        {
            p_147758_1_ = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }

        return (IIcon)p_147758_1_;
    }
    
}
