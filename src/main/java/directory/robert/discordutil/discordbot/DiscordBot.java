package directory.robert.discordutil.discordbot;

import directory.robert.discordutil.Discordutil;
import directory.robert.discordutil.discordbot.commands.BotListener;
import directory.robert.discordutil.discordbot.events.Eventhandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot extends Thread {
    private static JDA jda;
    private String token;
    private String statusChannel;
    private String botStatus;
    private String[] channels = new String[256];
    public static boolean running = false;

    public void run() {
        channels[0] = statusChannel;
        startBot();
    }

    public void shutdown() {
        Eventhandler.serverShutdown(); // send the server shutdown message, then stop the bot
        running = false;
        this.interrupt();
    }

    public DiscordBot(String token, String statusChannel, String botStatus) {
        this.token = token;
        this.statusChannel = statusChannel;
        this.botStatus = botStatus;
    }

    private void startBot() {
        if (botStatus.equals("PLAYERCOUNT")) {
            botStatus = "There are no players online ):";
        }
                JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.MESSAGE_CONTENT)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new BotListener(channels, jda))
                .setActivity(Activity.playing(botStatus))
                .setStatus(OnlineStatus.ONLINE);
        try {
            jda = builder.build().awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException("Bot failed to start!");
        }
        if (Discordutil.playerStatus) {
            updatePlayerCount(2); // update the status w/o incrementing
        }
        TextChannel channel = jda.getTextChannelById(statusChannel);
        if (channel != null) {
            running = true;
            Eventhandler Eventhandler = new Eventhandler(channel, jda);
            directory.robert.discordutil.discordbot.events.Eventhandler.serverStartup();
        }

    }

    /**
     * Updates the bots status if "bot-status" has been set to "PLAYERCOUNT"
     * @param join whether someone has joined the server (true) or left (false)
     */
    public static void updatePlayerCount(int join) {
        String status = "";
        if (join == 1) {
            Discordutil.playerCount++;
        } else if (join == 0) {
            Discordutil.playerCount--;
        }

        if (Discordutil.playerCount > 1) {
            status = String.format("There are %s players online!", Discordutil.playerCount);
        } else if (Discordutil.playerCount == 1) {
            status = "There is 1 player online!";
        } else {
            status = "There are no players online ):";
        }
        jda.getPresence().setActivity(Activity.playing(status)); // sets activity of bot
    }



}
