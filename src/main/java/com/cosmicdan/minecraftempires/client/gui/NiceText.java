package com.cosmicdan.minecraftempires.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    private int maxWidth;
    private int maxHeight;
    public int lineHeight;
    public List<List<String>> pageData = new ArrayList<List<String>>();
    private List<String> pageLines = new ArrayList<String>();
    
    public NiceText (String string, NiceText.Fonts font, int maxWidth, int maxHeight) {
        this.font = font;
        switch (font) {
            case VANILLA:
                // note that int division simply discards decimals (i.e. rounds-down) 
                this.maxWidth = maxWidth / 6;
                this.maxHeight = maxHeight / 9;
                this.lineHeight = 9;
                break;
        }
        buildNiceTextList(string);
    }
    
    private void buildNiceTextList(String input) {
        String[] wrapped = splitIntoLine(input, maxWidth);
        int lineThisPage = 0;
        int lineTotal = 0;
        for (String line : wrapped) {
            lineThisPage++;
            lineTotal++;
            //pageContent += line + "\n";
            pageLines.add(line);
            if (lineThisPage == maxHeight || lineTotal == wrapped.length) {
                pageData.add(pageLines);
                //System.out.println("-------------");
                pageLines = new ArrayList<String>();
                lineThisPage = 0;
            }
        }
    }
    
    private String[] splitIntoLine(String input, int maxCharInLine){

        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while(word.length() > maxCharInLine){
                output.append(word.substring(0, maxCharInLine-lineLen) + "\n");
                word = word.substring(maxCharInLine-lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");

            lineLen += word.length() + 1;
        }
        return output.toString().split("\n");
    }
    
    /*
    public void drawText() {
        char[] pageTxt = null;
    
        int preLength = 0;
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1);
        GL11.glScaled(0.15D, 0.15D, 0);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/book.png"));
        
        for(int i = 0; i < pageTxt.length; i++) {
            int begin = 0;
            int charLength = 0;
            
            switch(pageTxt[i]) {
                case '0': begin = 175; charLength = 19; break;
                case '1': begin = 0; charLength = 10; break;
                case '2': begin = 11; charLength = 19; break;
                case '3': begin = 31; charLength = 20; break;
                case '4': begin = 52; charLength = 18; break;
                case '5': begin = 71; charLength = 21; break;
                case '6': begin = 93; charLength = 19; break;
                case '7': begin = 113; charLength = 18; break;
                case '8': begin = 132; charLength = 23; break;
                case '9': begin = 156; charLength = 18; break;
            }
            
            this.drawTexturedModalRect(20 + preLength + 330, 140, begin, 182, charLength, 25);
            
            preLength += charLength;
        }
        GL11.glScaled(1, 1, 0);
    }
    */
}
