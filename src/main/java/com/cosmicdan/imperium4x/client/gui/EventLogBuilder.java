package com.cosmicdan.imperium4x.client.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.cosmicdan.imperium4x.data.player.ImperiumPlayer;

import net.minecraft.util.StatCollector;

public class EventLogBuilder {
    // TODO: Only get the latest x events (configurable) otherwise the player
    //       will one day have a log full of ancient events (speed concern)
    
    public String eventLog = "";
    private int eventDayCurrent = 0;
    
    
    public EventLogBuilder(ImperiumPlayer player) {
        // iterate all done events for this player
        String[] eventListDone = Arrays.copyOf(player.eventListDone.toArray(), player.eventListDone.size(), String[].class);
        for (String eventDone : eventListDone) {
            int eventDoneDay = Integer.parseInt(eventDone.substring(eventDone.indexOf("=") + 1));
            String event = eventDone.replace("=" + eventDoneDay, "");
            if (ImperiumPlayer.eventTypeEssential(event))
                addToEventLog("ESSENTIAL", event, eventDoneDay);
            else if (ImperiumPlayer.eventTypeTutorial(event))
                addToEventLog("TUTORIAL", event, eventDoneDay);
        }
    }
    
    private void addToEventLog (String newEventType, String newEvent, int newEventDay) {
        if (eventDayCurrent != newEventDay) {
            // TODO: Make a special flag to ensure that 'Day #' headers do not appear on the bottom of pages.
            //       Probably best to do this in the NiceText builder as the data is formatted
            eventLog += "[HEADER][DAY]=" + newEventDay + "\n";
            eventDayCurrent = newEventDay;
        }
        eventLog += "-" + (StatCollector.translateToLocal("playerlog.eventMsg_" + newEventType + "_" + newEvent) + "[NEWLINE]");
    }
}
