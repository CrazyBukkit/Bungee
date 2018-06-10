/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.BanReasons;
import net.crazybukkit.bungee.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class TeamChatCMD extends Command{
    
    private Bungee plugin;
    
    public TeamChatCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (this.plugin.permissionsManager.getTeam(p)) {
            if (args.length == 0) {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§c/tc <nachricht>"));
                return;
            }

            String msg = "";
            for (int i = 0; i < args.length; i++) {
                msg = msg + args[i] + " ";

            }
            for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                if (!this.plugin.permissionsManager.TeamMessage.contains(sender.getName())) {
                    if (this.plugin.permissionsManager.getTeam(all)) {
                         all.sendMessage(new TextComponent(this.plugin.teamchat +this.plugin.permissionsManager.getColor(this.plugin.getCache().get(plugin.getUUID(p.getName())))+ sender.getName() + "§7: §7" + msg));
                        
                    }
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDu hast die Team-Nachrichten ausgeschaltet und kannst deshalb nicht im Teamchat schreiben"));
                }
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }
    }
    
}
