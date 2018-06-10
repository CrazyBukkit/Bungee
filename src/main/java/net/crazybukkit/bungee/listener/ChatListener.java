/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.listener;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tebbh
 */
public class ChatListener implements Listener{


    private Bungee plugin;

    public ChatListener(Bungee plugin) {
        this.plugin = plugin;
    }
    

    
    @EventHandler
    public void onMuteChat(ChatEvent e) {
        try {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(p.getName()));
            if (cp.isMute()) {
                long current = System.currentTimeMillis();
                long end = cp.getMuteEnd();
                if((end < current) && (end != -1L)) {
                    cp.unmute();
                    this.plugin.permissionsManager.sendToTeam("§7Der Spieler §e"+p.getName()+"§7 wurde von der §bCloud §7entmutet");

                } else if(!e.getMessage().startsWith("/")) {
                    e.setCancelled(true);
                    p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu wurdest aus dem Chat gesperrt!\n" + this.plugin.getPrefix() + "§7Grund: §e" + cp.getMuteReason() + "\n" + this.plugin.getPrefix() + "§7Dauer: §e" + cp.getMuteEndTime()));

                }
            } else {
                e.setCancelled(false);
            }
        } catch (Exception e1) {

        }


    }   
    
    @EventHandler
    public void onBlockedWordsChat(ChatEvent e) {
        ArrayList<ProxiedPlayer> points = new ArrayList<>();
        ProxiedPlayer player = (ProxiedPlayer)e.getSender();
        String message = e.getMessage().toLowerCase();
        if(plugin.wordsManager.blocked.contains(message)) {
            e.setCancelled(true);
            e.setMessage("");
            e.setMessage("§cBitte achte auf deine Wortwahl!");
            this.plugin.permissionsManager.sendToTeam("§c"+player.getName()+"§7 hat das Wort §e"+message+"§7 geschrieben dies ist verboten!");
            if(!points.contains(player)) {
                points.add(player);
            } else {
                CrazyPlayer crazyPlayer = new CrazyPlayer(player);
                crazyPlayer.mute(player, "Wortwahl", 21600);
                this.plugin.permissionsManager.sendToTeam("§7Der Spieler §c"+player.getName()+"§7 wurde von der §bCloud§7 gemutet!\n§cGrund: "+"Wortwahl");
            }
        }
    }
    
 
    
    
    @EventHandler
    public void onReplace(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String msg = e.getMessage();
        if(msg.equalsIgnoreCase("%") || msg.contains("%") || msg.startsWith("%")) {
            msg.replaceAll("%"," Prozent");
            e.setCancelled(true);
            e.setMessage("Prozent");
        }
    }
    
    
    
   /* @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String msg = e.getMessage().toLowerCase();
        for(String block : this.plugin.wordsManager.blocked) {
            if (msg.startsWith(block) || msg.contains(block) || msg.equalsIgnoreCase(block)) {
                if(!this.plugin.permissionsManager.getTeam(p)) {
                msg.replaceAll("%"," Prozent");
                e.setCancelled(true);
                e.setMessage("");
                p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cBitte achte auf deinen Wortwahl!"));
                this.plugin.permissionsManager.sendToTeam("§c"+p.getName()+"§7 hat das Wort §e"+msg+"§7 geschrieben dies ist verboten!");
                return;   
                }

            }
        }

    }*/
    
    
    @EventHandler
    public void onGMute(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if(this.plugin.fileManager.globalmute) {
            if(!this.plugin.permissionsManager.getTeam(p) || !e.getMessage().startsWith("/")) {
                e.setCancelled(true);
                e.setMessage("");
                p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDer Chat ist Global gemutet deswegen kannst du nicht im Chat schreiben"));
            } else {
                e.setCancelled(false);
            }
        } else{
            e.setCancelled(false);
        }

    }


    @EventHandler
    public void onChatLog(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if(e.getMessage().startsWith("/")) {
            return;
        }
        List<String> list = new ArrayList<>();
        if(plugin.chatLogManager.getChatlog().containsKey(plugin.uuidFetcher.getUUID(p.getName()))) {
            list = plugin.chatLogManager.getChatlog().get(plugin.uuidFetcher.getUUID(p.getName()));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("DD:HH:mm");
        String time = sdf.format(new Date());
        String server = p.getServer().getInfo().getName();
        String log = server+":"+time+":"+e.getMessage();
        list.add(log);
        while (list.size() > 15) {
            list.remove(0);
        }
        plugin.chatLogManager.chatlog.put(plugin.uuidFetcher.getUUID(p.getName()),list);


    }
    
}
