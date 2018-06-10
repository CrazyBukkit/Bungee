/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class HelpCMD extends Command{
    
    private Bungee plugin;
    
    public HelpCMD(Bungee plugin,String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if(args.length == 0) {
            p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Informationen über das Netzwerk\n"+"§6/hub §7Geh ich die Lobby\n§6/party §7Spiele mit Freunde zusammen in einer Party\n§6/friend §7Freundes übersicht\n§6/report §7Reporte eine Spieler für sein Fehlverhalten\n§7Premium §7Kann man sich im Shop unter: §bbuy.naturfront.net §7kaufen"));
        } else {
            p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7/help <Erfahre Informationen über das Netzwerk>"));
        }

    }
}

