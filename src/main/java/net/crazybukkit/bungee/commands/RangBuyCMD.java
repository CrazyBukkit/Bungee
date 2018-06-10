/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class RangBuyCMD extends Command{
    
    private Bungee plugin;

    public RangBuyCMD(Bungee plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Command ist nur für BuyCraft!"));
            return;
        } else {
            if(args.length == 2) {
                CrazyPlayer cp = new CrazyPlayer(args[0]);
                if(args[1].equalsIgnoreCase("Premium")) {
                        this.plugin.permissionsManager.sendToAdmin("§6§lBuyCraft §r§7hat dem Spieler §e"+args[0]+"§7 den Rang §6Premium §7gegeben");
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§8Die §6CONSOLE §7hat dem Spieler §e"+args[0]+"§7 §6Premium §7gegeben"));
                        cp.setRang(Rang.Premium);
                        if(isOnline(args)) {
                            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
                            p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Rang §e"+args[1]+"§7 bekommen!\n§c§lBitte rejoin"));
                        }                       
                } else if(args[1].equalsIgnoreCase("Legend")) {
                        this.plugin.permissionsManager.sendToAdmin("§6§lBuyCraft §r§7hat dem Spieler §e"+args[0]+"§7 den Rang §dLegend §7gegeben");
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§8Die §6CONSOLE §7hat dem Spieler §e"+args[0]+"§7 §dLegend §7gegeben"));
                        cp.setRang(Rang.Platin);
                        if(isOnline(args)) {
                            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
                            p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Rang §e"+args[1]+"§7 bekommen!\n§c§lBitte rejoin"));
                        }                      
                }
   
             
            } else {
              sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"/premium <spielername> <rang>"));  
            }
        }
       
    }
    
    
        public boolean isOnline(String[] args) {
        if(ProxyServer.getInstance().getPlayer(args[0]) != null) {
            ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
            return true;



        }
        return false;
    }
    
    
    
    
}
