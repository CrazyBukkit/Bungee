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
public class PingCMD extends Command{
    
    private Bungee plugin;
    
    public PingCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cPing: §7"+p.getPing()));
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/ping"));
        }

    }
}

