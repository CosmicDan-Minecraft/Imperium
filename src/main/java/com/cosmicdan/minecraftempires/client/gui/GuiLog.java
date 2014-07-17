package com.cosmicdan.minecraftempires.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;
import com.cosmicdan.minecraftempires.server.PacketHandler;
import com.cosmicdan.minecraftempires.server.SyncPlayerME;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

// this class is responsible for drawing the "Log" GUI.
public class GuiLog extends GuiScreen {
    static final ResourceLocation texture = new ResourceLocation("minecraftempires:textures/gui/playerlog.png");
    
    private static final int BUTTON_CLOSE = 0;
    private static final int BUTTON_NEXT = 1;
    private static final int BUTTON_PREV = 2;
    private static final int guiWidthHeight = 230;
    private static final int guiContentWidth = 144;
    private static final int guiContentHeight = 180;
    
    private EntityPlayer player;
    
    private int totalPages = 0;
    private int currPage = 0;
    private PageButton buttonNextPage;
    private PageButton buttonPreviousPage;
    private GuiButton buttonClose;
    private List<List<String>> eventPageContent = new ArrayList<List<String>>();
    private int eventPageLineHeight = 9;


    public GuiLog(EntityPlayer player) {
        // initialize fields and construct static data (it's all realtime for this class though)
        this.player = player;
    }
    
    
    @Override
    public void initGui() {
        
    }
    
    
    @Override
    public void onGuiClosed() {

    }
    
    // draw the book GUI background/frame
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartials) {
        // set stuff for translucency (minor, removes aliasing)
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        // assign the "Log" texture
        mc.renderEngine.bindTexture(texture);
        int guiXStart = (width - guiWidthHeight) / 2;
        int guiYStart = 0;
        int guiContentXStart = guiXStart + 42;
        int guiContentYStart = 16;
        // draw the GUI
        drawTexturedModalRect(guiXStart, guiYStart, 0, 0, guiWidthHeight, guiWidthHeight);
        
        // construct data (we do it inside the drawScreen so we get realtime updates)
        EventLogBuilder playerEvents = new EventLogBuilder(MinecraftEmpiresPlayer.get(player));
        String content = playerEvents.eventLog;
        NiceText pageData = new NiceText(this.fontRendererObj, content, NiceText.Fonts.VANILLA, guiContentWidth, guiContentHeight);
        eventPageContent = pageData.pageData;
        eventPageLineHeight = pageData.lineHeight;
        // we also want to set the initial page of event log to the last page (most recent)
        totalPages = currPage = eventPageContent.size() - 1;
        
        // draw the buttons
        this.buttonList.clear();
        this.buttonList.add(this.buttonClose = new CloseButton(BUTTON_CLOSE, guiXStart + 176, 8));
        this.buttonList.add(this.buttonNextPage = new PageButton(BUTTON_NEXT, guiXStart + 170, 200, true));
        this.buttonList.add(this.buttonPreviousPage = new PageButton(BUTTON_PREV, guiXStart + 150, 200, false));
        
        // draw buttonList and labelList
        super.drawScreen(mouseX, mouseY, renderPartials);
        
        // linear smoothing disabled, can't turn it off :\
        //GL11.glAlphaFunc(GL11.GL_GREATER,0.5f);
        
        int drawTextY = guiContentYStart;
        for (String line : eventPageContent.get(currPage)) {
            GL11.glPushMatrix();
            if (line.contains("[HEADER][DAY]")) {
                line = line.substring(line.indexOf("=") + 1);
                GL11.glScaled(0.2D, 0.2D, 1.0D);
                double drawTextMulti = 5;
                double drawTextYAdjust = 0.2D; 
                drawTextY += (int) (pageData.drawDayHeader(mc, this, (int)(guiContentXStart * drawTextMulti), (int)(drawTextY * drawTextMulti), 1, Integer.parseInt(line)) * drawTextYAdjust);
                
            } else {
                GL11.glScaled(0.8D, 0.8D, 1.0D);
                double drawTextMulti = 1.25D;
                this.fontRendererObj.drawString(line, (int)(guiContentXStart * drawTextMulti), (int)(drawTextY * drawTextMulti), 0xFFFFFF);
                drawTextY += eventPageLineHeight;
            }
            GL11.glPopMatrix();
        }
        
        // linear smoothing disabled, can't turn it off :\
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    }

    // the actions for GUI buttons
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BUTTON_CLOSE:
                // command to close the current GUI window (i.e. this book)
                close();
                break;
            case BUTTON_NEXT:
                if (currPage < totalPages) ++currPage;
                break;
            case BUTTON_PREV:
                if (currPage > 0) --currPage;
                break;
        }
    }

    // we don't want this GUI to pause the game
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    // hook for keyboard keys
    @Override
    protected void keyTyped(char c, int key) {
        switch (key) {
            case Keyboard.KEY_ESCAPE:
            case Keyboard.KEY_E:
                // Escape = close book
                close();
                break;
            case Keyboard.KEY_A:
            case Keyboard.KEY_LEFT:
                if (currPage > 0) --currPage;
                break;
            case Keyboard.KEY_D:
            case Keyboard.KEY_RIGHT:
                if (currPage < totalPages) ++currPage;
                break;
        }
    }
    
    private void close() {
        GL11.glDisable(GL11.GL_BLEND);
        mc.displayGuiScreen(null);
    }

    
    // page next/previous buttons. Has some hard-coded values for my texture UV map.
    static class PageButton extends GuiButton {
        private final boolean isNextPage;

        public PageButton(int id, int xPos, int yPos, boolean isNextPage) {
            super(id, xPos, yPos, 16, 16, "");
            this.isNextPage = isNextPage;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                boolean isHovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                // translucency stuff
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                mc.renderEngine.bindTexture(texture);
                int uPos = 0;
                int vPos = 231;
                if (isHovered) {
                    uPos += 17;
                }
                if (!this.isNextPage) {
                    uPos += 35;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, uPos, vPos, 16, 16);
            }
        }
    }
    
    // page close button. As above, has some hard-coded values for UV mapping
    static class CloseButton extends GuiButton {
        public CloseButton(int id, int xPos, int yPos) {
            super(id, xPos, yPos, 16, 16, "");
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                boolean isHovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                mc.renderEngine.bindTexture(texture);
                int uPos = 70;
                int vPos = 231;
                if (isHovered) {
                    uPos += 17;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, uPos, vPos, 16, 16);
            }
        }
    }
}
