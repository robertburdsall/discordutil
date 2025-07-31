package directory.robert.discordutil.discordbot.commands;

import directory.robert.discordutil.Discordutil;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommands extends ListenerAdapter {
    private String[] channels;
    public BotCommands (String[] channels) { this.channels = channels; }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("message received!");

        String channel = event.getChannel().getId();
        boolean included = false;
        for (String id : channels) {
            System.out.println(id);
            if (id.equals(channel)) { included = true; break; }
        }
        // if the channel isn't one we are reporting, or the author is bot, stop here
        if (!included) return;
        if (event.getAuthor().isBot()) return;

        System.out.println("not bot and in included channels!");

        // build the string to send to minecraft server
        String msg = String.format("[%s]: %s", event.getAuthor().getName(), event.getMessage().getContentDisplay());

        Discordutil.sendMessage(msg);
        System.out.println("sending message!");

    }

}
