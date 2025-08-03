package directory.robert.discordutil;

import directory.robert.discordutil.commands.linkCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import directory.robert.discordutil.events.listener;
import directory.robert.discordutil.discordbot.DiscordBot;

public final class Discordutil extends JavaPlugin {
    private boolean enabled = false;
    private DiscordBot DiscordBot;
    public static int playerCount;
    public static boolean playerStatus;

    @Override
    public void onEnable() {

        System.out.println("Discordutil Plugin is enabling...");
        /* configuration logic */
        saveDefaultConfig(); // copies default config.yml and puts it in plugin's data folder
        getConfig().options().copyDefaults(true); // merges any new config changes
        saveConfig(); // saves new merged config

        String configVersion = getConfig().getString("config-version");

        String botKey = getConfig().getString("bot-token");
        if (botKey == null) {
            throw new RuntimeException("Bot token has not been set!");
        }

        String statusChannel = getConfig().getString("status-channel");
        if (statusChannel == null) {
            throw new RuntimeException("Status channel has not been set!");
        }

        String botStatus = getConfig().getString("bot-status");
        if (botStatus.equals("PLAYERCOUNT")) {
            playerCount = getServer().getOnlinePlayers().size();
            playerStatus = true;
        } else {
            playerStatus = false;
        }

        enabled = true;
        /* discord bot logic */
        if (enabled) {
            // start discord bot
            DiscordBot = new DiscordBot(botKey, statusChannel, botStatus);
            DiscordBot.start(); // runs bot on its own thread? so sigma.
            // register events
            getServer().getPluginManager().registerEvents(new listener(), this);

            // register commands
            getCommand("link").setExecutor(new linkCommand());

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

    public static void sendMessage(String message) {
        Bukkit.getServer().broadcastMessage(message);
    }

}
