package directory.robert.discordutil.discordbot;

import directory.robert.discordutil.discordbot.commands.BotCommands;
import directory.robert.discordutil.discordbot.events.Eventhandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot extends Thread {
    private static JDA jda;
    private String token;
    private String statusChannel;
    private String botStatus;
    private static boolean playerCount = false;
    public static boolean running = false;

    public void run() {
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
        if (botStatus == "PLAYERCOUNT") {
            botStatus = "There are 0 players online!";

        }
                JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new BotCommands())
                .setActivity(Activity.playing(botStatus));
        try {
            jda = builder.build().awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException("Bot failed to start!");
        }
        TextChannel channel = jda.getTextChannelById(statusChannel);
        if (channel != null) {
            running = true;
            Eventhandler Eventhandler = new Eventhandler(channel, jda);
            directory.robert.discordutil.discordbot.events.Eventhandler.serverStartup();
        }

    }



}
