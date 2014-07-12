package com.cosmicdan.minecraftempires.datamanagement;

import com.cosmicdan.minecraftempires.Main;
import com.cosmicdan.minecraftempires.playermanagement.EntityPlayerME;
import com.cosmicdan.minecraftempires.playermanagement.EventsEssential.EssentialEvents;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PlayerData {
    
    // These strings determine the names of the NBT tags for each player
    //private static final String firstJoin = "minecraftempires.firstjoin";
    //private static final String lastLogin = "minecraftempires.lastLogin";
    //private static final String lastLogout = "minecraftempires.lastLogout";
    
    // declare some global variables or "fields" for this instance, this is important. If this makes no sense, learn Java :)
    public Boolean isNewPlayer = false;
    public NBTTagCompound playerDataPersisted;
    public EntityPlayerMP thisPlayer;
    
    
    // constructor
    public PlayerData(EntityPlayerMP player) {
        // populate the fields for this instance
        loadPlayer(player);
    }
    
    public void loadPlayer(EntityPlayerMP player) {
        // save this player to this instance
        thisPlayer = (EntityPlayerMP) player;
        // get NBT data for this player
        NBTTagCompound playerData = player.getEntityData();
        if (!playerData.hasKey(EntityPlayerMP.PERSISTED_NBT_TAG)) {
            // no NBT found, create it - this is a new player!
            playerData.setTag(EntityPlayerMP.PERSISTED_NBT_TAG, (playerDataPersisted = new NBTTagCompound()));
            // trigger firstjoin event
            EntityPlayerME playerME = EntityPlayerME.get(thisPlayer);
            playerME.addEvent(EssentialEvents.FIRSTJOIN);
            playerME.eventPending = true;
            // give them their Journal
            //thisPlayer.inventory.addItemStackToInventory(new ItemStack(Main.itemJournal));
        } else {
            // returning player, load their NBT data
            playerDataPersisted = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        }
    }
    
    // this method is called whenever a player joins the server (after they are loaded)
    public void welcome() {
        // get the world object
        World world = thisPlayer.worldObj;
        // initialize string for welcome message
        String msg = StatCollector.translateToLocalFormatted("text.welcome", thisPlayer.getDisplayName(), WorldData.worldDay);
        // actually send it
        thisPlayer.addChatMessage(new ChatComponentText(msg));
    }
    
    public static void savePlayerData(EntityPlayerMP player) {
        // get data for this player from their NBT
        NBTTagCompound playerData = player.getEntityData();
        playerData = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        // clear the newbie flag if required
        //if (playerData.getBoolean(firstJoin)) {
        //    playerData.setBoolean(firstJoin, false);
        //}
    }
}
