package net.betterverse.chatmanager.command;

import net.betterverse.chatmanager.ChatManager;
import net.betterverse.creditsshop.PlayerListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixExecutor implements CommandExecutor {
    private final ChatManager plugin;

    public PrefixExecutor(ChatManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                plugin.setPrefix(player, "");
                player.sendMessage(ChatColor.GREEN + "Prefix cleared.");
            } else if (args.length == 1) {
                // Validate the prefix
                boolean valid = true;
                for (int i = 0; i < args[0].length(); i++) {
                    if (!Character.isLetter(args[0].charAt(i))) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    if (PlayerListener.deductCredits(player, plugin.config().getPrefixCost())) {
                        plugin.setPrefix(player, args[0]);
                        player.sendMessage(ChatColor.GREEN + "You set your prefix to " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid prefix. Only letters are allowed.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid arguments. /prefix (prefix)");
            }
        } else {
            sender.sendMessage("Only in-game players can use '/" + cmdLabel + "'.");
        }

        return true;
    }
}
