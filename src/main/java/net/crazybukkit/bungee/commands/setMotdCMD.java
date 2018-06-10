/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import java.util.Arrays;

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
public class setMotdCMD extends Command{
    
    private Bungee plugin;
    
    public setMotdCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length >= 1) {
                String motd = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
                this.plugin.fileManager.motd = motd;
                this.plugin.runnableManager.async(() -> {
                    this.plugin.fileManager.setMOTD(motd);
                });
                sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast die MOTD auf " + motd.replace("&","§") + " §7gesetzt"));

            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/setmotd <MOTD>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}


