package com.cosmicdan.minecraftempires.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiJournalBook extends GuiScreen {
    static final ResourceLocation texture = new ResourceLocation("minecraft:textures/gui/book.png");
    
    private static final int BUTTON_CLOSE = 0;

    public GuiJournalBook() {
        // construct data here. Possibly iterate over a list array if I ever bother with pagination 
    }
    
    @Override
    public void initGui() {
        super.initGui();
        List<GuiButton> buttons = buttonList;
        int bookXBegin = (width - 192) / 2;
        // this button looks dodgy as hell but whatever
        buttons.add(new GuiButton(BUTTON_CLOSE, bookXBegin + 160, 2, 10, 10, "x"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BUTTON_CLOSE:
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartials) {
        int bookXStart = (width - 192) / 2;
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(bookXStart, 2, 0, 0, 192, 192);
        super.drawScreen(mouseX, mouseY, renderPartials);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char c, int key) {
        char lowerCase = Character.toLowerCase(c);
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }
}
