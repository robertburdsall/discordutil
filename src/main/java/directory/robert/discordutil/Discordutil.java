package directory.robert.discordutil;

import org.bukkit.plugin.java.JavaPlugin;
import directory.robert.discordutil.events.listener;

public final class Discordutil extends JavaPlugin {
    private boolean enabled = false;

    @Override
    public void onEnable() {

        System.out.println("Discordutil Plugin is enabling...");

        enabled = true;

        getServer().getPluginManager().registerEvents(new listener(enabled), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
