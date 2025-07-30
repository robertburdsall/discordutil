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
    public static boolean running = false;

    public void run() {
        startBot();
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }

    public DiscordBot(String token, String statusChannel) {
        this.token = token;
        this.statusChannel = statusChannel;
    }

    private void startBot() {
                JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new BotCommands())
                .setActivity(Activity.listening("urmom"));
        jda = builder.build();
        // ensure that the provided channel is accessible by the bot
        System.out.println(statusChannel);
        TextChannel channel = jda.getTextChannelById(statusChannel);
        if (channel != null) {
            System.out.println("channel is invalid!!!");
            running = true;
            Eventhandler Eventhandler = new Eventhandler(channel, jda);
        }

    }



}
