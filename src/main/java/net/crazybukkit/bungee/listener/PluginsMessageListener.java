/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.listener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoFriends;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author Tebbe
 */
public class PluginsMessageListener implements Listener{
    
    private final Bungee plugin;

    public PluginsMessageListener(Bungee plugin) {
        this.plugin = plugin;
    }
    

    @EventHandler
    public void onMessage(PluginMessageEvent event)
    {
        if (event.getTag().equalsIgnoreCase("BungeeCord"))
        {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try
            {
                String channel = in.readUTF();
                if (channel.equals("friends")) {
                    String input = in.readUTF();
                    String target = in.readUTF();
                    target = target.replace("§c","").replace("§a","");
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(event.getReceiver().toString());
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(input + " " + target + " " + player.getName()));
                    ProxiedPlayer p = ProxyServer.getInstance().getPlayer(target);
                    if (input.equals("remove")) {
                        MongoFriends mongoFreunde = MongoFriends.getPlayer(this.plugin,this.plugin.uuidFetcher.getUUID(player.getName()),player.getName());
                        if(mongoFreunde.isFriend(target)) {
                            mongoFreunde.removeFriend(target);
                            player.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast §c"+target+"§7 aus §7deine §7Freundesliste §centfernt"));
                            if(p != null) {
                                p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§e"+player.getName()+"§7 hat dich auser §7seiner §7Freundesliste §centfernt"));
                            }

                        }
                    } else if (input.equals("accept")) {

                    } else if (input.equals("deny")) {

                    } else if(input.equals("jump")) {
                        if(p != null) {
                            player.connect(p.getServer().getInfo());
                            player.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du bist §e" + p.getName() + " §7hinterher gesprungen auf dem Server §6" +
                                    ProxyServer.getInstance().getPlayer(p.getName()).getServer().getInfo().getName()));
                        } else {
                            player.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist nicht online!"));
                        }


                    }
                }
            }
            catch (Exception localException) {}
        }
    }
    
    
    
}
