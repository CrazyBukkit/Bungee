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
public class TeamMessageCMD extends Command{
    
    private Bungee plugin;
    
    public TeamMessageCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if(this.plugin.permissionsManager.getTeam(p)) {
            if(args.length == 0) {
                if(this.plugin.permissionsManager.TeamMessage.contains(sender.getName())) {
                    this.plugin.permissionsManager.TeamMessage.remove(p.getName());
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast die Team-Nachrichten eingeschalten"));
                } else {
                    this.plugin.permissionsManager.TeamMessage.add(p.getName());
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast die Team-Nachrichten ausgeschaltet"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/tn"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}