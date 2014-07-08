package com.cosmicdan.minecraftempires.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

// this class is responsible for drawing the "Journal" GUI. Still a work in progress, doesn't do anything except look like ass
public class GuiJournalBook extends GuiScreen {
    static final ResourceLocation texture = new ResourceLocation("minecraft:textures/gui/book.png");
    
    private static final int BUTTON_CLOSE = 0;

    public GuiJournalBook() {
        // construct data for the book contents here. 
    }
    
    // draw the book GUI background/frame
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartials) {
        int bookXStart = (width - 192) / 2;
        // assign the "book" texture
        mc.renderEngine.bindTexture(texture);
        // set the bounds for the book GUI
        drawTexturedModalRect(bookXStart, 2, 0, 0, 192, 192);
        // actually draw the book
        super.drawScreen(mouseX, mouseY, renderPartials);
    }
    
    // draw the GUI elements
    @Override
    public void initGui() {
        super.initGui();
        List<GuiButton> buttons = buttonList;
        int bookXBegin = (width - 192) / 2;
        // Add close button. Looks dodgy as heck but whatever.
        buttons.add(new GuiButton(BUTTON_CLOSE, bookXBegin + 160, 2, 10, 10, "x"));
    }

    // the actions for GUI buttons
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BUTTON_CLOSE:
                // command to close the current GUI window (i.e. this book)
                mc.displayGuiScreen(null);
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
        char lowerCase = Character.toLowerCase(c);
        if (key == Keyboard.KEY_ESCAPE) {
            // Escape = close book
            mc.displayGuiScreen(null);
        }
    }
}
