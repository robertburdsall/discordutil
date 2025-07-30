package directory.robert.discordutil;

import net.dv8tion.jda.api.JDA;
import org.bukkit.plugin.java.JavaPlugin;
import directory.robert.discordutil.events.listener;
import directory.robert.discordutil.discordbot.DiscordBot;

public final class Discordutil extends JavaPlugin {
    private boolean enabled = false;
    private DiscordBot DiscordBot;

    @Override
    public void onEnable() {

        System.out.println("Discordutil Plugin is enabling...");

        saveDefaultConfig(); // copies default config.yml and puts it in plugin's data folder

        String botkey = getConfig().getString("bot-token");
        if (botkey == null) {
            throw new RuntimeException("Bot token has not been set!");
        }
        enabled = true;
        if (enabled) {
            // start discord bot
            DiscordBot = new DiscordBot(botkey);
            DiscordBot.start(); // runs bot on its own thread? so sigma.
            // register events
            getServer().getPluginManager().registerEvents(new listener(), this);
        }


    }

    @Override
    public void onDisable() {
        // shut down discord bot thread gracefully
        DiscordBot.shutdown();
        try {
            DiscordBot.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
