package com.cosmicdan.minecraftempires.client.gui;

import java.util.ArrayList;

import net.minecraft.util.StatCollector;

import com.cosmicdan.minecraftempires.medata.player.EntityPlayerME;

public class EventLogBuilder {
    // TODO: Only get the latest x events (configurable) otherwise the player
    //       will one day have a log full of ancient events (speed concern)
    
    public String eventLog = "";
    private int eventDayCurrent = 0;
    
    
    public EventLogBuilder(EntityPlayerME player) {
        // iterate all done events for this player
        System.out.println(player.eventListDone.toString());
        /*
        for (String eventDone : (String[]) player.eventListDone.toArray()) {
            int eventDoneDay = Integer.parseInt(eventDone.substring(eventDone.indexOf("=") + 1));
            String event = eventDone.replace("=" + eventDoneDay, "");
            if (EntityPlayerME.eventTypeEssential(event))
                addToEventLog("ESSENTIAL", event, eventDoneDay);
        }*/
    }
    
    private void addToEventLog (String newEventType, String newEvent, int newEventDay) {
        if (eventDayCurrent != newEventDay) {
            // TODO: Make a special flag to ensure that 'Day #' headers do not appear on the bottom of pages.
            //       Probably best to do this in the NiceText builder as the data is formatted
            eventLog += (StatCollector.translateToLocal("playerlog.day") + " " + newEventDay + "\n");
            eventDayCurrent = newEventDay;
        }
        eventLog += "-" + (StatCollector.translateToLocal(newEventType + "_" + newEvent) + "\n");
    }
}
