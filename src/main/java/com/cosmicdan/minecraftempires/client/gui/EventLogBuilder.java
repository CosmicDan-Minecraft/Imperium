package com.cosmicdan.minecraftempires.client.gui;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.util.StatCollector;

import com.cosmicdan.minecraftempires.medata.player.MinecraftEmpiresPlayer;

public class EventLogBuilder {
    // TODO: Only get the latest x events (configurable) otherwise the player
    //       will one day have a log full of ancient events (speed concern)
    
    public String eventLog = "";
    private int eventDayCurrent = 0;
    
    
    public EventLogBuilder(MinecraftEmpiresPlayer player) {
        // iterate all done events for this player
        String[] eventListDone = Arrays.copyOf(player.eventListDone.toArray(), player.eventListDone.size(), String[].class);
        for (String eventDone : eventListDone) {
            int eventDoneDay = Integer.parseInt(eventDone.substring(eventDone.indexOf("=") + 1));
            String event = eventDone.replace("=" + eventDoneDay, "");
            if (MinecraftEmpiresPlayer.eventTypeEssential(event))
                addToEventLog("ESSENTIAL", event, eventDoneDay);
        }
    }
    
    private void addToEventLog (String newEventType, String newEvent, int newEventDay) {
        if (eventDayCurrent != newEventDay) {
            // TODO: Make a special flag to ensure that 'Day #' headers do not appear on the bottom of pages.
            //       Probably best to do this in the NiceText builder as the data is formatted
            eventLog += (StatCollector.translateToLocalFormatted("playerlog.day", newEventDay)) + "\n";
            eventDayCurrent = newEventDay;
        }
        eventLog += "-" + (StatCollector.translateToLocal("playerlog.eventMsg_" + newEventType + "_" + newEvent) + "\n");
    }
}
