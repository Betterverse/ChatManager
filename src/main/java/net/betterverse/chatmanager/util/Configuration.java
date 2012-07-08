package net.betterverse.chatmanager.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import net.betterverse.chatmanager.ChatManager;

public class Configuration {
    private final YamlFile file;

    public Configuration(ChatManager plugin) {
        this.file = new YamlFile(plugin, new File(plugin.getDataFolder(), "config.yml"), "config");

        load();
    }

    public long getConsecutiveMessageTimeout() {
        return file.getInt("consecutive-message-timeout-seconds") * 20;
    }

    public String getConsecutiveMessageTimeoutNotification() {
        return StringHelper.parseColors(file.getString("messages.consecutive-message-timeout-notification"));
    }

    public String getFormattedMessage(Player player, String message) {
        return file.getString("chat-format").replace("<pex-prefix>", PermissionsEx.getUser(player).getPrefix(player.getWorld().getName())).replace("<prefix>", "" /* TODO */).replace("<nickname>", player.getDisplayName())
                .replace("<message>", message);
    }

    public int getChatLimit() {
        return file.getInt("chat-limit.messages");
    }

    public long getChatLimitMillis() {
        return file.getInt("chat-limit.time-in-seconds") * 1000;
    }

    public String getChatLimitWarning() {
        return StringHelper.parseColors(file.getString("messages.chat-limit-warning").replace("<time>", String.valueOf(file.getInt("chat-limit.time-in-seconds"))));
    }

    public String getConsecutiveMessageWarning() {
        return StringHelper.parseColors(file.getString("messages.consecutive-warning"));
    }

    public int getMaximumConsecutiveMessages() {
        return file.getInt("maximum-consecutive-messages");
    }

    public void load() {
        file.load();

        // Add defaults
        Map<String, Object> defaults = new HashMap<String, Object>();
        defaults.put("chat-format", "<prefix><pex-prefix><nickname>:&f <message>");
        defaults.put("chat-limit.messages", 4);
        defaults.put("chat-limit.time-in-seconds", 30);
        defaults.put("consecutive-message-timeout-seconds", 30);
        defaults.put("maximum-consecutive-messages", 4);
        defaults.put("messages.chat-limit-warning", "&cYou have sent too many messages within <time> seconds. Please be patient.");
        defaults.put("messages.consecutive-message-timeout-notification", "&aOkay, enough punishment. You can talk again.");
        defaults.put("messages.consecutive-warning", "&cYou have sent too many messages in a row. Please wait for someone else to chat before chatting again.");

        for (Entry<String, Object> entry : defaults.entrySet()) {
            if (!file.containsKey(entry.getKey())) {
                file.set(entry.getKey(), entry.getValue());
            }
        }

        file.save();
    }
}
