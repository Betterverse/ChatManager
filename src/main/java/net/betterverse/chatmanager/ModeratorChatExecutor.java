package net.betterverse.chatmanager;

import java.util.HashSet;
import java.util.Set;

import net.betterverse.chatmanager.util.StringHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ModeratorChatExecutor implements CommandExecutor, Listener {
    private final Set<String> modChat = new HashSet<String>();

    public ModeratorChatExecutor(ChatManager plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("chatmanager.moderate.modchat")) {
                if (modChat.contains(sender.getName())) {
                    // Player is toggling mod chat off
                    modChat.remove(sender.getName());
                    sender.sendMessage(ChatColor.GREEN + "You have left the private moderator chat channel.");
                } else {
                    // Player is toggling mod chat on
                    modChat.add(sender.getName());
                    sender.sendMessage(ChatColor.GREEN + "You have entered the private moderator chat channel.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission.");
            }
        } else {
            sender.sendMessage("Only in-game players can use '/" + cmdLabel + "'.");
        }

        return true;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (modChat.contains(player.getName())) {
            // Send message to players with moderator chat permission
            for (Player online : player.getServer().getOnlinePlayers()) {
                if (!online.hasPermission("chatmanager.moderate.modchat")) {
                    event.getRecipients().remove(online);
                }
            }

            event.setFormat(StringHelper.parseColors("&d[ModChat]&f ") + event.getFormat());
        }
    }
}
