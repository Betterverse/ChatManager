package net.betterverse.chatmanager.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreExecutor implements CommandExecutor {
    private final Map<String, Set<String>> ignored = new HashMap<String, Set<String>>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (!args[0].equalsIgnoreCase(player.getName())) {
                    if (!ignored.containsKey(player.getName())) {
                        ignored.put(player.getName(), new HashSet<String>());
                    }
                    Set<String> ignoredPlayers = ignored.get(player.getName());

                    if (cmdLabel.equalsIgnoreCase("ignore")) {
                        if (ignoredPlayers.contains(args[0])) {
                            player.sendMessage(ChatColor.RED + "You have already ignored " + args[0] + ".");
                        } else {
                            ignoredPlayers.add(args[0]);
                            player.sendMessage(ChatColor.GREEN + "You have ignored " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                        }
                    } else if (cmdLabel.equalsIgnoreCase("unignore")) {
                        if (ignoredPlayers.contains(args[0])) {
                            ignoredPlayers.remove(args[0]);
                            player.sendMessage(ChatColor.GREEN + "You have unignored " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                        } else {
                            player.sendMessage(ChatColor.RED + "You have not yet ignored " + args[0] + ".");
                        }
                    }

                    ignored.put(player.getName(), ignoredPlayers);
                } else {
                    player.sendMessage(ChatColor.RED + "You cannot ignore yourself.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid arguments. /" + cmdLabel + " <player>");
            }
        } else {
            sender.sendMessage("Only in-game players can use '/" + cmdLabel + "'.");
        }

        return true;
    }

    public boolean isPlayerIgnoredByPlayer(Player recipient, Player chatter) {
        return ignored.containsKey(recipient.getName()) && ignored.get(recipient.getName()).contains(chatter.getName());
    }
}
