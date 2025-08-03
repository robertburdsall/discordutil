package directory.robert.discordutil.discordbot.commands;

import directory.robert.discordutil.Discordutil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;

public class BotListener extends ListenerAdapter {
    private String[] channels;
    private JDA jda;

    public BotListener(String[] channels, JDA jda) {
        this.channels = channels;
        this.jda = jda;
    }

    /**
     * Upon receiving a message in the appropriate channel(s), the bot forwards the message to the Minecraft server
     * @param event MessageReceivedEvent that calls the method whenever a message is received by the bot
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
        String channel = event.getChannel().getId();
        boolean included = false;
        for (String id : channels) {
            if (id.equals(channel)) { included = true; break; }
        }
        // if the channel isn't one we are reporting, or the author is bot, stop here
        if (!included) return;



        // build the string to send to minecraft server
        String raw = String.format("[&9Discord&f] <%s>: %s", event.getAuthor().getName(), event.getMessage().getContentRaw());
        String message = ChatColor.translateAlternateColorCodes('&', raw);

        Discordutil.sendMessage(message);

    }


    // indexing stuff that indexes on bot start

    @Override
    public void onGuildReady(GuildReadyEvent e) {
        e.getGuild().loadMembers()
                .onSuccess(members -> {
                    
                })
                .onError(error -> {
                    error.printStackTrace();
                });
    }


}
