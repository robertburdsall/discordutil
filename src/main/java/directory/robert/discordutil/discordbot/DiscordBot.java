package directory.robert.discordutil.discordbot;

import directory.robert.discordutil.discordbot.commands.BotCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot extends Thread {
    private static JDA jda;
    private String token;
    public boolean running = false;

    public void run() {
        startBot();
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }

    public DiscordBot(String token) { this.token = token; }

    private void startBot() {
                JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new BotCommands())
                .setActivity(Activity.listening("urmom"));
        jda = builder.build();
        running = true;

    }



}
