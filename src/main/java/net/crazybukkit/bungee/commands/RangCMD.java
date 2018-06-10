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
public class RangCMD extends Command{
    
    
    private Bungee plugin;
    
    public RangCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }


        @Override
        public void execute(CommandSender sender, String[] args) {
                 ProxiedPlayer p = (ProxiedPlayer) sender;
                if (this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
                    if (args.length == 2) {
                        ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                        CrazyPlayer cp = new CrazyPlayer(args[0]);
                     if (args[1].equalsIgnoreCase(Rang.Administrator.getName()) || 
                             args[1].equalsIgnoreCase(Rang.Entwickler.getName()) || 
                             args[1].equalsIgnoreCase(Rang.Moderator.getName()) || args[1].equalsIgnoreCase(Rang.Supporter.getName()) || 
                             args[1].equalsIgnoreCase(Rang.Builder.getName()) || args[1].equalsIgnoreCase(Rang.Content.getName()) || 
                             args[1].equalsIgnoreCase(Rang.YouTube.getName()) || args[1].equalsIgnoreCase(Rang.PremiumPlus.getName()) || 
                             args[1].equalsIgnoreCase(Rang.Premium.getName()) || args[1].equalsIgnoreCase(Rang.Spieler.getName())) {
                         Rang rang = Rang.getUnit(args[1]);
                        if(isOnline(args)) {
                            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang "+rang.getColor()+""+rang.getName()+" §7gegeben"));
                            pp.disconnect(new TextComponent(this.plugin.getPrefix() + "§7Du hast einen neuen Rang\n" + "§7Rang §8» "+rang.getColor()+""+rang.getName()));
                                cp.setRang(rang);
                        }else {
                            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang "+rang.getColor()+""+rang.getName()+" §7gegeben"));
                                cp.setRang(rang);

                        }
                     

                    } else {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cRänge §8» §eAdministrator,Entwickler,Moderator,Supporter,Content,Builder,Platin,Youtube,PremiumPlus,Premium,Spieler"));
                    }
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§c/rang <name> <rang>"));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+ "§c§l Die Ränge sind permanent!"));
                }
            } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
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

