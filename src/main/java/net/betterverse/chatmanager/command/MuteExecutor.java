package net.betterverse.chatmanager.command;

import java.util.HashMap;
import java.util.Map;

import net.betterverse.chatmanager.util.StringHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteExecutor implements CommandExecutor {
    private final Map<String, String> muted = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender.hasPermission("chatmanager.moderate.mute")) {
            if (cmdLabel.equalsIgnoreCase("mute")) {
                if (args.length >= 2) {
                    Player player = sender.getServer().getPlayer(args[0]);
                    if (player != null) {
                        muted.put(player.getName(), StringHelper.concatenate(args, 1));
                        sender.sendMessage(ChatColor.GREEN + "You muted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");

                        for (Player online : sender.getServer().getOnlinePlayers()) {
                            online.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + " has muted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " for the reason: " + ChatColor.YELLOW + muted.get(player.getName()));
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + args[0] + " is not online.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid arguments. /mute <player> <reason>");
                }
            } else if (cmdLabel.equalsIgnoreCase("unmute")) {
                if (args.length == 1) {
                    if (muted.containsKey(args[0])) {
                        muted.remove(args[0]);
                        sender.sendMessage(ChatColor.GREEN + "You unmuted " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");

                        for (Player online : sender.getServer().getOnlinePlayers()) {
                            online.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + " has unmuted " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + args[0] + " is not muted.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid arguments. /unmute <player>");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission.");
        }

        return true;
    }

    public String getMuteReason(Player player) {
        return muted.get(player.getName());
    }

    public boolean isPlayerMuted(Player player) {
        return muted.containsKey(player.getName());
    }
}
