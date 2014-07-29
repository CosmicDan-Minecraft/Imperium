package com.cosmicdan.minecraftempires.medata.player;

/** 
 *  PlayerData is responsible for helping with player related data and
 *  the propagation of it to/from code-readable formats, and to/from
 *  compact formats for network communication and saving/load data
 */
public class PlayerData {
    /** Constants of all player abilities, stored as true/false (1 or 0) in an int[] */
    public enum PlayerAbilities {
        CANPUNCHWOOD, 
    }
    
    public static boolean hasAbility(MinecraftEmpiresPlayer playerME, PlayerAbilities ability) {
        if (playerME.playerAbilities[ability.ordinal()] > 0)
            return true;
        return false;
    }
}
