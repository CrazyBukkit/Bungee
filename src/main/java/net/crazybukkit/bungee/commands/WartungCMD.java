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
public class WartungCMD extends Command{
    
    private Bungee plugin;
    
    public WartungCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(!this.plugin.fileManager.wartung == true) {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Wartungsmodus §aaktiviert!"));
                this.plugin.fileManager.wartung = true;
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(!this.plugin.permissionsManager.getTeamDatabase(this.plugin.uuidFetcher.getUUID(all.getName()),all.getName())) {
                        all.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Das Netzwerk ist nun in Wartungen"));
                        plugin.getRunnableManager().async(() -> {
                            all.disconnect(new TextComponent(this.plugin.getPrefix()+"§7Das Netzwerk ist nun in Wartungen"));
                        });
                    }
                }
                this.plugin.runnableManager.async(() -> {
                    this.plugin.fileManager.setWartung(true);
                });
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Wartungsmodus §cdeaktiviert!"));
                this.plugin.fileManager.wartung = false;
                this.plugin.runnableManager.async(() -> {
                    this.plugin.fileManager.setWartung(false);
                });
            }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/wartung "));
        }
    }
}


