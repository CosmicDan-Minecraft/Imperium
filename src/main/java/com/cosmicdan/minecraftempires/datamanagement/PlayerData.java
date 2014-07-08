package com.cosmicdan.minecraftempires.datamanagement;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PlayerData {
    
    // These strings determine the names of the NBT tags for each player
    private static final String firstJoin = "minecraftempires.firstjoin";
    private static final String lastLogin = "minecraftempires.lastLogin";
    private static final String lastLogout = "minecraftempires.lastLogout";
    private static final String playerDaysRaw = "minecraftempires.playerDaysRaw";
    private static final String playerDaysDouble = "minecraftempires.playerDaysDouble";
    private static final String playerDay = "minecraftempires.playerDay";
    
    // declare some global variables or "fields" for this instance, this is important. If this makes no sense, learn Java :)
    public Boolean isNewPlayer = false;
    public NBTTagCompound playerDataPersisted;
    public EntityPlayerMP entityPlayer;
    
    
    // constructor
    public PlayerData(EntityPlayerMP player) {
        // populate the fields for this instance
        loadPlayer(player);
    }
    
    public void loadPlayer(EntityPlayerMP player) {
        // get NBT data for this player
        NBTTagCompound playerData = player.getEntityData();
        if (!playerData.hasKey(EntityPlayerMP.PERSISTED_NBT_TAG)) {
            // no NBT found, create it - this is a new player!
            playerData.setTag(EntityPlayerMP.PERSISTED_NBT_TAG, (playerDataPersisted = new NBTTagCompound()));
            // set 'isNewPlayer' to true - this is used to start the initial events or 'quests'
            isNewPlayer = true;
            playerDataPersisted.setBoolean(firstJoin, true);
            // give them their Journal
            player.inventory.addItemStackToInventory(new ItemStack(Main.itemJournal));
        } else {
            // returning player, load their NBT data
            playerDataPersisted = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        }
        // save this player to the instance field
        entityPlayer = player;
    }
    
    // this method is called whenever a player joins the server (after they are loaded)
    public void welcome() {
        // get the world object
        World world = entityPlayer.worldObj;
        // initialize string for welcome message
        String msg = "";
        if (isNewPlayer) {
            // they are a new player - set welcome text to "You have written in your journal", indicating the player should check it!
            msg = I18n.format("journal.written");
            // get the current world time
            Long timeNow = world.getTotalWorldTime();
            // Same the current world time as 'lastLogin' key in this player's NBT 
            playerDataPersisted.setLong(lastLogin, timeNow);
        } else {
            // this is a returning player. Show the welcome text.
            msg = I18n.format("text.welcome", entityPlayer.getDisplayName(), playerDataPersisted.getInteger(playerDay));
        }
        // logic done, actually send the text to chat window
        net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new net.minecraft.util.ChatComponentText(msg));
    }
    
    // this method is called when a player logs out, and for every player when the server shuts down.
    //     Has to be void and get all the data from NBT rather than re-using playerdata instance,
    //     because the server shutdown event is not a player event
    // TODO: Make this ONlY apply when the server shuts down. We want their days-in-server  
    //       to continue counting if they leave but the server stays on, so random events 
    //       can trigger next they return depending on what's happened in the world
    public static void savePlayerData(EntityPlayerMP player) {
        // get data for this player from their NBT
        NBTTagCompound playerData = player.getEntityData();
        playerData = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        // 'How many days they've played in this server before' is 0 for new players
        Long dayRawLast = 0L;
        // clear the newbie flag if required
        if (playerData.getBoolean(firstJoin)) {
            playerData.setBoolean(firstJoin, false);
        } else {
            // they're not a new player, load their real value for days spent here
            dayRawLast = playerData.getLong(playerDaysRaw);
        }
        // get current world time
        Long timeNow = player.worldObj.getTotalWorldTime();
        // save their current logout time
        playerData.setLong(lastLogout, timeNow);
        // calculate how long the player has been in this world by adding this world session lenth to the players' previous value
        Long dayRawNew = playerData.getLong(playerDaysRaw) + timeNow - (playerData.getLong(lastLogin));
        // save the total life length of this player in the world
        playerData.setLong(playerDaysRaw, dayRawNew);
        // save the total life length of this player in the world as 'current day number' in double format (a large decimal number)
        Double playerDays = (double) (dayRawNew / 24000);
        playerData.setDouble(playerDaysDouble, playerDays);
        // save the actual day number the player is on by rounding *down* and adding one 
        playerData.setInteger(playerDay, (int) Math.floor(playerDays) + 1);
    }
}
