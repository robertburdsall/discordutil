package directory.robert.discordutil.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener implements Listener {
    public listener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        System.out.println("player " + event.getPlayer().getDisplayName() + " has joined the server");
    }




}
