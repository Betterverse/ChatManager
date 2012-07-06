package net.betterverse.chatmanager;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin implements Listener {

    @Override
    public void onDisable() {
        log(toString() + " disabled.");
    }

    @Override
    public void onEnable() {
        log(toString() + " enabled.");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getDescription().getName() + " v" + getDescription().getVersion() + " [Written by: ");
        List<String> authors = getDescription().getAuthors();
        for (int i = 0; i < authors.size(); i++) {
            builder.append(authors.get(i) + (i + 1 != authors.size() ? ", " : ""));
        }
        builder.append("]");

        return builder.toString();
    }

    public void log(Level level, String message) {
        getServer().getLogger().log(level, "[Communities] " + message);
    }

    public void log(String message) {
        log(Level.INFO, message);
    }
}
