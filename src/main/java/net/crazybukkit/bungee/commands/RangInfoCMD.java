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
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class RangInfoCMD extends Command{
    
    private Bungee plugin;
    
    public RangInfoCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if (args.length == 1) {
                String name = args[0];
                CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(name));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Rang: §e" + cp.getRang()));
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/ranginfo <name>"));
            }

        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }
    }
}

