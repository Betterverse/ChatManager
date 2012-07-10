package net.betterverse.chatmanager.command;

import net.betterverse.chatmanager.ChatManager;
import net.betterverse.chatmanager.util.StringHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

public class MeExecutor implements CommandExecutor {
    private final ChatManager plugin;

    public MeExecutor(ChatManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String message = StringHelper.concatenate(args, 0);
                PlayerChatEvent event = new PlayerChatEvent(player, message);
                player.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    for (Player online : sender.getServer().getOnlinePlayers()) {
                        online.sendMessage(plugin.config().getFormattedMeMessage(player, message));
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid arguments. /me <msg>");
            }
        } else {
            sender.sendMessage("Only in-game players can use '/" + cmdLabel + "'.");
        }

        return true;
    }
}
