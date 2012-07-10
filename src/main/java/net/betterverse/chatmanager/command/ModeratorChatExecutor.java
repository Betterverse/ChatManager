package net.betterverse.chatmanager.command;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ModeratorChatExecutor implements CommandExecutor, Listener {
    private final Set<String> modChat = new HashSet<String>();

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

    public boolean isInModChat(Player player) {
        return modChat.contains(player.getName());
    }
}
