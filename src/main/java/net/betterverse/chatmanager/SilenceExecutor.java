package net.betterverse.chatmanager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SilenceExecutor implements CommandExecutor, Listener {
    private final Set<String> silence = new HashSet<String>();

    public SilenceExecutor(ChatManager plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (hasPlayerSilencedChat(player)) {
                silence.remove(player.getName());
                player.sendMessage(ChatColor.GREEN + "Chat is no longer silenced.");
            } else {
                silence.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "You have silenced chat.");
            }
        } else {
            sender.sendMessage("Only in-game players can use '/" + cmdLabel + "'.");
        }

        return true;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Unsilence chat for players if they have silenced chat
        Player player = event.getPlayer();
        if (hasPlayerSilencedChat(player)) {
            silence.remove(player.getName());
        }
    }

    public boolean hasPlayerSilencedChat(Player player) {
        return silence.contains(player.getName());
    }
}
