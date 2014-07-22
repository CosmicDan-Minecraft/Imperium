package com.cosmicdan.minecraftempires.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/*
 *  NiceText is a class for parsing a very large multi-line string for paginated display.
 *  Arguments are string, font (only 'Vanilla' for now), maxWidth (px) and maxHeight (px).
 *  NiceText.text will contain the List of the resulting data, with one element per page.
 */
public class NiceText {
    
    public enum Fonts {
        VANILLA,
    }
    
    private Fonts font;
    private int maxWidthPx;
    private int maxWidthCharsEst;
    private int maxHeightChars;
    public int lineHeight;
    private List<String> pageLines = new ArrayList<String>();
    //public List<List<Integer>> lineHeights = new ArrayList<List<Integer>>();
    //private List<Integer> lineHeight = new ArrayList<Integer>();
    private FontRenderer fontRendererObj;
    
    public List<List<String>> pageData = new ArrayList<List<String>>();
    
    public NiceText (FontRenderer fontRendererObj, String string, NiceText.Fonts font, int maxWidth, int maxHeight) {
        this.font = font;
        switch (font) {
            case VANILLA:
                // note that int division simply discards decimals (i.e. rounds-down) 
                //this.maxWidth = (int) (maxWidth / 6 * 1.25);
                this.maxWidthPx = (int) (maxWidth * 1.25);
                // this is an estimate and only used for splitting lines that are a massive string
                this.maxWidthCharsEst = (int) (maxWidth / 6 * 1.25);
                this.maxHeightChars = (int) (maxHeight / 9 * 1.25);
                //this.lineHeight = 9;
                this.lineHeight = fontRendererObj.FONT_HEIGHT;
                break;
        }
        this.fontRendererObj = fontRendererObj;
        buildNiceTextList(string);
    }
    
    private void buildNiceTextList(String input) {
        String[] wrapped = splitToLines(input);
        int lineThisPage = 0;
        int lineTotal = 0;
        for (String line : wrapped) {
            lineThisPage++;
            lineTotal++;
            pageLines.add(line);
            if (lineThisPage == maxHeightChars || lineTotal == wrapped.length) {
                pageData.add(pageLines);
                pageLines = new ArrayList<String>();
                lineThisPage = 0;
            }
        }
    }
    
    private String[] splitToLines(String input){

        
        StringTokenizer tok = new StringTokenizer(input, " \n");
        StringBuilder output = new StringBuilder(input.length());
        int lineLenChars = 0;
        int lineLenPx = 0;
        int lengthSpaceCharPx = fontRendererObj.getStringWidth(" ");
        Boolean newLineTrailing = false;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();
            if (word.contains("[NEWLINE]")) {
                word = word.replace("[NEWLINE]", "\n");
                newLineTrailing = true;
            }
            if (!word.contains("[HEADER]")) {
                while(fontRendererObj.getStringWidth(word) > maxWidthPx){
                    // some stupidly-long string, we'll do our best (should never happen
                    // so I'm not fussed about increasing accuracy here)
                    output.append(word.substring(0, maxWidthCharsEst-lineLenChars) + "\n");
                    word = word.substring(maxWidthCharsEst-lineLenChars);
                    lineLenChars = 0;
                }
                int wordLengthPx = fontRendererObj.getStringWidth(word);
                if (lineLenPx + wordLengthPx > maxWidthPx) {
                    output.append("\n");
                    lineLenChars = 0;
                    lineLenPx = 0;
                }
                output.append(word + " ");
                lineLenChars += word.length() + 1;
                lineLenPx += wordLengthPx + lengthSpaceCharPx;
            } else {
                // line is a header, don't wrap it (it's replaced later)
                output.append(word + "\n");
                lineLenChars = 0;
                lineLenPx = 0;
            }
            if (newLineTrailing) {
                lineLenChars = 0;
                lineLenPx = 0;
                newLineTrailing = false;
            }
        }
        return output.toString().split("\n");
    }
    
    public int drawDayHeader(Minecraft mc, GuiScreen gui, int drawX, int drawY, int age, int day) {
        ResourceLocation texture;
        texture = getDayTextureForLang(mc.gameSettings.language, age);
        mc.renderEngine.bindTexture(texture);
        int dayHeaderWidth = 0;
        int dayHeaderHeight = 0;
        switch (age) {
            case 1: 
                dayHeaderWidth = 142;
                dayHeaderHeight = 80;
                break;
        }
        gui.drawTexturedModalRect(drawX, drawY, 0, 0, dayHeaderWidth, dayHeaderHeight);
        
        // draw number(s) for day
        mc.renderEngine.bindTexture(new ResourceLocation("minecraftempires:textures/gui/playereventlist_numbers_" + age + ".png"));
        drawX += dayHeaderWidth;
        drawY += 6;
        String dayString = String.valueOf(day);
        int drawU = 0;
        int drawV = 0;
        int sizeX = 0;
        int sizeY = 0;
        
        for (int charCount = 0; charCount < dayString.length(); charCount++) {
            switch (dayString.charAt(charCount)) {
                case '1': drawU =   0; drawV =  96; sizeX = 37; sizeY = 53; break;
                case '2': drawU =  50; drawV =  96; sizeX = 44; sizeY = 53; break;
                case '3': drawU = 100; drawV =  96; sizeX = 41; sizeY = 53; break;
                case '4': drawU = 150; drawV =  96; sizeX = 44; sizeY = 53; break;
                case '5': drawU = 201; drawV =  96; sizeX = 34; sizeY = 53; break;
                case '6': drawU =   0; drawV = 175; sizeX = 40; sizeY = 55; break;
                case '7': drawU =  50; drawV = 175; sizeX = 36; sizeY = 55; break;
                case '8': drawU = 100; drawV = 175; sizeX = 44; sizeY = 55; break;
                case '9': drawU = 151; drawV = 175; sizeX = 34; sizeY = 55; break;
                case '0': drawU = 201; drawV = 175; sizeX = 36; sizeY = 55; break;
            }
            gui.drawTexturedModalRect(drawX, drawY, drawU, drawV, sizeX, sizeY);
            drawX += sizeX;
        }
        
        return dayHeaderHeight;
    }
    
    private ResourceLocation getDayTextureForLang(String lang, int age) {
        // only support en_US for now
        if (!lang.equals("en_US"))
            lang="en_US";
        
        return new ResourceLocation("minecraftempires:textures/gui/" + lang + "/playereventlist_day_" + age + ".png");
    }
}
