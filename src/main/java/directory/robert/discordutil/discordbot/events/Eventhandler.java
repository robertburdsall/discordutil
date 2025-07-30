package directory.robert.discordutil.discordbot.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import directory.robert.discordutil.discordbot.DiscordBot;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class Eventhandler {
    private static TextChannel statusChannel;
    private JDA jda;
    public Eventhandler(TextChannel statusChannel, JDA jda) { this.statusChannel = statusChannel; this.jda = jda; }

    public static boolean playerJoined(PlayerJoinEvent event) {
        // if the bot isn't running, return false
        if (!DiscordBot.running) {
            System.out.println("discord bot is not running!");
            return false;
        }
        // sends the message in the designated channel
        System.out.println("sending message!");
        statusChannel.sendMessage(event.getPlayer().getDisplayName() + " has joined the server!");

        return true;
    }

}
