package net.crazybukkit.bungee.manager;

import net.crazybukkit.bungee.Bungee;
import java.util.*;

public class ChatLogManager {


    public Map<UUID,List<String>> chatlog = new HashMap<>();


    private final Bungee plugin;

    public ChatLogManager(Bungee plugin) {
        this.plugin = plugin;
    }

    public Map<UUID, List<String>> getChatlog() {
        return chatlog;
    }

    public void addChat(UUID uuid,List<String> list) {
        chatlog.put(uuid,list);
    }

    public List<String> getChatLog(UUID uuid) {
        List<String> chat = new ArrayList<>();
        if(chatlog.containsKey(uuid)) {
            chat = chatlog.get(uuid);
        }

        return chat;
    }
}
