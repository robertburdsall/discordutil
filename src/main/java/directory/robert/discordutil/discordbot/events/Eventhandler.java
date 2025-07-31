package directory.robert.discordutil.discordbot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import directory.robert.discordutil.discordbot.DiscordBot;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class Eventhandler {
    private static TextChannel statusChannel;
    private static JDA jda;
    public Eventhandler(TextChannel statusChannel, JDA jda) { this.statusChannel = statusChannel; this.jda = jda; }

    private static boolean isActive() {
        return DiscordBot.running;
    }

    public static boolean playerJoined(PlayerJoinEvent event) {
        // if the bot isn't running, return false
        if (!isActive()) { return false; }

        // transitioning to embeds for important messages
        EmbedBuilder eb = new EmbedBuilder();
        String thumbnail = "https://minotar.net/avatar/" + event.getPlayer().getUniqueId() +"/64.png";
        eb.setThumbnail(thumbnail);
        eb.setTitle(event.getPlayer().getDisplayName() + " has joined the server!");
        eb.setColor(Color.green);

        // status for if the message successfully sends
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        // send the embed, if it is successful return true, else false
        statusChannel.sendMessageEmbeds(eb.build()).queue(
                message -> result.complete(true),
                err -> {
                    result.complete(false);
                    throw new RuntimeException("unable to send message!");
                }
        );
        return result.getNow(false);
    }

    public static boolean playerLeave(PlayerQuitEvent event) {
        // if the bot isn't running, return false
        if (!isActive()) { return false; }

        // transitioning to embeds for important messages
        EmbedBuilder eb = new EmbedBuilder();
        String thumbnail = "https://minotar.net/avatar/" + event.getPlayer().getUniqueId() +"/64.png";
        eb.setThumbnail(thumbnail);
        eb.setTitle(event.getPlayer().getDisplayName() + " has left the server ):");
        eb.setColor(Color.red);

        // status for if the message successfully sends
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        // send the embed, if it is successful return true, else false
        statusChannel.sendMessageEmbeds(eb.build()).queue(
                message -> result.complete(true),
                err -> {
                    result.complete(false);
                    throw new RuntimeException("unable to send message!");
                }
        );
        return result.getNow(false);
    }

    public static boolean chatMessage(AsyncPlayerChatEvent event) {
        // if the bot isn't running, return false
        if (!isActive()) { return false; }

                CompletableFuture<Boolean> result = new CompletableFuture<>();
                statusChannel.sendMessage("[" + event.getPlayer().getDisplayName() + "]: " + event.getMessage()).queue(
                message -> result.complete(true),
                err -> {
                    result.complete(false);
                    throw new RuntimeException("unable to send message");
                }
        );
        return result.getNow(false);
    }

    public static boolean serverShutdown() {
        // if the bot isn't running, return false
        if (!isActive()) { return false; }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Server is shutting down!");
        eb.setColor(Color.red);
                CompletableFuture<Boolean> result = new CompletableFuture<>();
                statusChannel.sendMessageEmbeds(eb.build()).queue(
                message -> result.complete(true),
                err -> {
                    result.complete(false);
                    throw new RuntimeException("unable to send message!");
                }
        );
        return result.getNow(false);
    }

    public static boolean serverStartup() {
        // if the bot isn't running, return false
        if (!isActive()) { return false; }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Server is starting!");
        eb.setColor(Color.green);
                CompletableFuture<Boolean> result = new CompletableFuture<>();
                statusChannel.sendMessageEmbeds(eb.build()).queue(
                message -> result.complete(true),
                err -> {
                    result.complete(false);
                    throw new RuntimeException("unable to send message!");
                }
        );
        return result.getNow(false);
    }

}
