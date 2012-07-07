package net.betterverse.chatmanager;

public class ChatMessage {
    private String player;
    private String message;
    private long time;

    public ChatMessage(String player, String message, long time) {
        this.player = player;
        this.message = message;
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }
}
