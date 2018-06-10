/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.listener;

import java.util.UUID;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author Tebbe
 */
public class ProxyPingListener implements Listener{
    
    
    private Bungee plugin;

    public ProxyPingListener(Bungee plugin) {
        this.plugin = plugin;
    }
      
    
    
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        ServerPing.Protocol ver = ping.getVersion();

        if (this.plugin.fileManager.wartung) {
            ping.setDescription("§2NaturFrontNET §8» §7MiniGames Netzwerk §8» §61.8.X §8┃\n" + "§4§lWartungsarbeiten "+this.plugin.fileManager.motd.replace("&", "§"));
            ver.setName("§cWartungsarbeiten");
            ver.setProtocol(2);
            players.setMax(this.plugin.fileManager.slots);
            players.setOnline(0);
            ping.setPlayers(players);
            ping.setVersion(ver);
            players.setSample(new ServerPing.PlayerInfo[]{
                    new ServerPing.PlayerInfo("§8  » §2NaturFrontNET §8«   ",UUID.randomUUID()),
                    new ServerPing.PlayerInfo("§8➥ §7MiniGames Netzwerk",UUID.randomUUID()) });

        } else {
            ping.setDescription("§2NaturFrontNET §8» §7MiniGames Netzwerk §8» §61.8.X §8┃\n"+this.plugin.fileManager.motd.replace("&","§"));
            players.setSample(new ServerPing.PlayerInfo[] {
                    new ServerPing.PlayerInfo("§8  » §2NaturFrontNET §8«   ",UUID.randomUUID()),
                    new ServerPing.PlayerInfo("§8➥ §7MiniGames Netzwerk",UUID.randomUUID()) });
            players.setMax(this.plugin.fileManager.slots);
            ver.setName("");
            ping.setVersion(ver);
            ping.setPlayers(players);
            }
        

        }
    }
