package net.betterverse.chatmanager.command;

import java.util.HashMap;
import java.util.Map;

import net.betterverse.chatmanager.ChatManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class MuteExecutor implements CommandExecutor, Listener {
    private final Map<String, String> muted = new HashMap<String, String>();

    public MuteExecutor(ChatManager plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (args.length >= 2) {
            if (sender.hasPermission("chatmanager.moderate.mute")) {
                Player player = sender.getServer().getPlayer(args[0]);
                if (player != null) {
                    if (cmdLabel.equalsIgnoreCase("mute")) {
                        muted.put(player.getName(), getReason(args));
                        sender.sendMessage(ChatColor.GREEN + "You muted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");

                        for (Player online : sender.getServer().getOnlinePlayers()) {
                            online.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + " has muted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " for the reason: " + ChatColor.YELLOW + muted.get(player.getName()));
                        }
                    } else if (cmdLabel.equalsIgnoreCase("unmute")) {
                        if (muted.containsKey(player.getName())) {
                            muted.remove(player.getName());
                            sender.sendMessage(ChatColor.GREEN + "You unmuted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");

                            for (Player online : sender.getServer().getOnlinePlayers()) {
                                online.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + " has unmuted " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + player.getName() + " is not muted.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid arguments. /<mute|unmute> <player> <reason>");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + args[1] + " is not online.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. /<mute|unmute> <player> <reason>");
        }

        return true;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (muted.containsKey(player.getName())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot speak. You have been muted for the reason: " + muted.get(player.getName()));
        }
    }

    private String getReason(String[] args) {
        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]);

            // Append a space if it is not the last argument
            if (i + 1 < args.length) {
                reason.append(" ");
            }
        }

        return reason.toString();
    }
}
