package directory.robert.discordutil;

import net.dv8tion.jda.api.JDA;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import directory.robert.discordutil.events.listener;
import directory.robert.discordutil.discordbot.DiscordBot;

public final class Discordutil extends JavaPlugin {
    private boolean enabled = false;
    private DiscordBot DiscordBot;
    public static int playerCount;

    @Override
    public void onEnable() {

        System.out.println("Discordutil Plugin is enabling...");

        saveDefaultConfig(); // copies default config.yml and puts it in plugin's data folder

        String botKey = getConfig().getString("bot-token");
        if (botKey == null) {
            throw new RuntimeException("Bot token has not been set!");
        }

        String statusChannel = getConfig().getString("status-channel");
        if (statusChannel == null) {
            throw new RuntimeException("Status channel has not been set!");
        }

        String botStatus = getConfig().getString("bot-status");
        if (botStatus == "PLAYERCOUNT") {
            playerCount = 0;
        }

        enabled = true;
        if (enabled) {
            // start discord bot
            DiscordBot = new DiscordBot(botKey, statusChannel, botStatus);
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
