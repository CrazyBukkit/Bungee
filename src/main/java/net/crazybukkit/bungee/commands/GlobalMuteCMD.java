/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class GlobalMuteCMD extends Command{
    
    
    private Bungee plugin;
    
    public GlobalMuteCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 0) {
                if(!this.plugin.fileManager.globalmute) {
                    this.plugin.fileManager.globalmute = true;
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Globalchat mute §aaktiviert"));
                } else {
                    this.plugin.fileManager.globalmute = false;
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Globalchat mute §cdeaktiviert"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7/gmute <Keiner, außer den Teammitglieder, kann mehr schreiben>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

