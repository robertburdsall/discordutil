package directory.robert.discordutil.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import directory.robert.discordutil.discordbot.events.Eventhandler;

public class listener implements Listener {
    public listener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        System.out.println("running in plugin listener");
        Eventhandler.playerJoined(event); // tells the bot to send the proper message
    }




}
