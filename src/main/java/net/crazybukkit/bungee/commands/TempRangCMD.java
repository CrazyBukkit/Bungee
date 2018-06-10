/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.crazybukkit.bungee.utils.RangTime;
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
public class TempRangCMD extends Command{
    
    private Bungee plugin;
    
    public TempRangCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if (args.length == 4) {
                int vaule;
                vaule = Integer.valueOf(args[2]);
                String unitString = args[3];
                RangTime time = RangTime.getUnit(unitString);
                int seconds = vaule * time.getToSecond();

                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(args[0]));
                if (args[1].equalsIgnoreCase("Legend")) {
                    if(isOnline(args)) {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §f"+args[1]+" §7gegeben"));
                        pp.disconnect(new TextComponent(this.plugin.getPrefix() + "§7Du hast einen neuen Rang\n" + "§7Rang §8» §f"+args[1]));
                            cp.setTempRang(Rang.Platin, seconds);
                    }else {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §dLegend §7gegeben"));
                            cp.setTempRang(Rang.Platin, seconds);
                    }

                } else if (args[1].equalsIgnoreCase("PremiumPlus")) {
                    if(isOnline(args)) {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §6PremiumPlus §7gegeben"));
                        pp.disconnect(new TextComponent(this.plugin.getPrefix() + "§7Du hast einen neuen Rang\n" + "§7Rang §8» §6PremiumPlus"));
                            cp.setTempRang(Rang.PremiumPlus, seconds);
                    }else {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §6PremiumPlus §7gegeben"));
                            cp.setTempRang(Rang.PremiumPlus, seconds);
                    }
                } else if (args[1].equalsIgnoreCase("Premium")) {
                    if(isOnline(args)) {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §6Premium §7gegeben"));
                        pp.disconnect(new TextComponent(this.plugin.getPrefix() + "§7Du hast einen neuen Rang\n" + "§7Rang §8» §6Premium"));                        
                            cp.setTempRang(Rang.Premium, seconds);   
                    }else {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast dem Spieler §e" + args[0] + "§7 den Rang §6Premium §7gegeben"));                        
                            cp.setTempRang(Rang.Premium, seconds);  

                     }
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cRänge §8» §ePremiumPlus,Premium,Spieler"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§c/rang <name> <rang> <rangtime>"));
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


   

