package com.cosmicdan.minecraftempires.datamanagement;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PlayerData {
    
    // These strings determine the names of the NBT tags for each player
    private static final String firstJoin = "minecraftempires.firstjoin";
    private static final String lastLogin = "minecraftempires.lastLogin";
    private static final String lastLogout = "minecraftempires.lastLogout";
    
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
            msg = StatCollector.translateToLocal("journal.written");
            // get the current world time
            Long timeNow = world.getTotalWorldTime();
            // Save the current world time as 'lastLogin' key in this player's NBT 
            playerDataPersisted.setLong(lastLogin, timeNow);
        } else {
            // this is a returning player. Build/show the welcome text.
            msg = StatCollector.translateToLocalFormatted("text.welcome", entityPlayer.getDisplayName(), WorldData.worldDay);
        }
        // send the text to this players chat
        entityPlayer.addChatMessage(new ChatComponentText(msg));
    }
    
    public static void savePlayerData(EntityPlayerMP player) {
        // get data for this player from their NBT
        NBTTagCompound playerData = player.getEntityData();
        playerData = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        // clear the newbie flag if required
        if (playerData.getBoolean(firstJoin)) {
            playerData.setBoolean(firstJoin, false);
        }
    }
}
