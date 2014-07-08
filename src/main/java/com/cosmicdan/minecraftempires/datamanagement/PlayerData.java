package com.cosmicdan.minecraftempires.datamanagement;

import com.cosmicdan.minecraftempires.Main;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PlayerData {
    
    private static final String firstJoin = "minecraftempires.firstjoin";
    private static final String lastLogin = "minecraftempires.lastLogin";
    private static final String lastLogout = "minecraftempires.lastLogout";
    private static final String playerDaysRaw = "minecraftempires.playerDaysRaw";
    private static final String playerDaysDouble = "minecraftempires.playerDaysDouble";
    private static final String playerDay = "minecraftempires.playerDay";
    
    public Boolean isNewPlayer = false;
    public NBTTagCompound playerDataPersisted;
    public EntityPlayerMP entityPlayer;
    
    
    // constructor
    public PlayerData(EntityPlayerMP player) {
        loadPlayer(player);
    }
    
    public void loadPlayer(EntityPlayerMP player) {
        NBTTagCompound playerData = player.getEntityData();
        if (!playerData.hasKey(EntityPlayerMP.PERSISTED_NBT_TAG)) {
            playerData.setTag(EntityPlayerMP.PERSISTED_NBT_TAG, (playerDataPersisted = new NBTTagCompound()));
        } else {
            playerDataPersisted = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        }
        if (!playerDataPersisted.hasKey(firstJoin)) {
            playerDataPersisted.setBoolean(firstJoin, true);
            // give them their Journal
            player.inventory.addItemStackToInventory(new ItemStack(Main.itemJournal));
            // kick-starter for first events
            isNewPlayer = true;
        }
        entityPlayer = player;
    }
    
    public void welcome() {
        World world = entityPlayer.worldObj;
        String msg = "";
        if (isNewPlayer) {
            msg = I18n.format("journal.written");
            Long timeNow = world.getTotalWorldTime();
            playerDataPersisted.setLong(lastLogin, timeNow);
        } else {
            msg = I18n.format("text.welcome", entityPlayer.getDisplayName(), playerDataPersisted.getInteger(playerDay));
        }
        
        net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new net.minecraft.util.ChatComponentText(msg));
    }
    
    public static void savePlayerData(EntityPlayerMP player) {
        // get data for this plater
        NBTTagCompound playerData = player.getEntityData();
        playerData = playerData.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        // default for new players
        Long dayRawLast = 0L;
        // clear newbie flag if required
        if (playerData.getBoolean(firstJoin)) {
            playerData.setBoolean(firstJoin, false);
        } else {
            // get their original raw days
            dayRawLast = playerData.getLong(playerDaysRaw);
        }
        
        Long timeNow = player.worldObj.getTotalWorldTime();
        playerData.setLong(lastLogout, timeNow);
        Long dayRawNew = playerData.getLong(playerDaysRaw) + timeNow - (playerData.getLong(lastLogin));
        playerData.setLong(playerDaysRaw, dayRawNew);
        Double playerDays = (double) (dayRawNew / 24000);
        playerData.setDouble(playerDaysDouble, playerDays);
        playerData.setInteger(playerDay, (int) Math.floor(playerDays) + 1);
    }
}
