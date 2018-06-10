/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class PlayerInfoCMD extends Command{
    
    private Bungee plugin;
    
    public PlayerInfoCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getMute(p)) {
            if(args.length == 1) {
                String name = args[0];
                CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(name));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+""));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Name: §c"+name));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Rang: §e"+cp.getRang()));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Coins: §c"+cp.getCoins()));
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+""));
                if(cp.isBanned()) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gebannt: §cJa"));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Grund: §c"+cp.getBanReason()));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Vonwem: §c"+cp.getBanBy()));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+""));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gemutet: §cNein"));
                } else if(cp.isMute()) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gebannt: §cNein"));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+""));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gemutet: §cJa"));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Grund: §c"+cp.getMuteReason()));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Vonwem: §c"+cp.getMuteReason()));
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gebannt: §cNein"));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+""));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Gemutet: §cNein"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/pinfo <name>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

