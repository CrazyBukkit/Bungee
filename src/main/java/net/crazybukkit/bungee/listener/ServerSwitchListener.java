/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.listener;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author Tebbe
 */
public class ServerSwitchListener implements Listener{
    
    private Bungee plugin;

    public ServerSwitchListener(Bungee plugin) {
        this.plugin = plugin;
    }
    
     @EventHandler
    public void onTempRang (ServerSwitchEvent e) {
        try {
            ProxiedPlayer p = e.getPlayer();
            CrazyPlayer cp = new CrazyPlayer(p.getName());
            if(cp.isTempRang()) {
                long current = System.currentTimeMillis();
                long end = cp.getRangTime();
                if ((end > current) || end == -1L) {

                } else {
                    cp.setRang(Rang.Spieler);
                    this.plugin.permissionsManager.sendToTeam("§7Der Spieler §e" + p.getName() + "§7 wurde von der §bCloud §7zum Rang Spieler gesetzt");
                  
                }


            }

        } catch (NullPointerException e1) {
        }
    }
}