package net.betterverse.chatmanager.util;

import java.io.File;

import org.bukkit.entity.Player;

import net.betterverse.chatmanager.ChatManager;

public class PlayerData {
    private final YamlFile file;

    public PlayerData(ChatManager plugin) {
        this.file = new YamlFile(plugin, new File(plugin.getDataFolder(), "data.yml"), "data");

        load();
    }

    public String getPrefix(Player player) {
        return file.getString(player.getName() + ".prefix");
    }

    public void load() {
        file.load();

        file.save();
    }

    public void set(String key, Object value) {
        file.set(key, value);
    }
}
