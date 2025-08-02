package directory.robert.discordutil.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import directory.robert.discordutil.discordbot.events.Eventhandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class listener implements Listener {
    public listener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Eventhandler.playerJoined(event); // tells the bot to send the proper message
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Eventhandler.playerLeave(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Eventhandler.chatMessage(event);
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Eventhandler.playerAdvancementEvent(event);
    }

}
