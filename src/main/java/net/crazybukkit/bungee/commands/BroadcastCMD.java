/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
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
public class BroadcastCMD extends Command{
    
    private Bungee plugin;
    
    public BroadcastCMD(Bungee plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 0) {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/bc <narchicht>"));
                return;
            }
            String msg = "";
            for (int i = 0; i < args.length; i++) {
                msg = msg + args[i] + " ";

            }

            for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                pp.sendMessage(new TextComponent(""));
                pp.sendMessage(new TextComponent(this.plugin.getPrefix()+"§a"+msg.replace("&","§")));
                pp.sendMessage(new TextComponent(""));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

