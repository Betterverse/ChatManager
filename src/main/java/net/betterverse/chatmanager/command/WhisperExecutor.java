package net.betterverse.chatmanager.command;

import net.betterverse.chatmanager.ChatManager;
import net.betterverse.chatmanager.util.StringHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperExecutor implements CommandExecutor {
    private final ChatManager plugin;

    public WhisperExecutor(ChatManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (args.length >= 2) {
            Player player = sender.getServer().getPlayer(args[0]);
            if (player != null) {
                plugin.whisper(sender, player, StringHelper.concatenate(args, 1));
            } else {
                sender.sendMessage(ChatColor.RED + args[1] + " is not online.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. /w <player> <msg>");
        }

        return true;
    }
}
