package net.betterverse.chatmanager;

public class ChatMessage {
    private String player;
    private long time;

    public ChatMessage(String player, long time) {
        this.player = player;
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }
}
