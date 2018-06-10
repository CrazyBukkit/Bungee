/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class ReportAcceptCMD extends Command{
    
    private Bungee plugin;
    
    public ReportAcceptCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getBan(p)) {
            if(args.length == 1) {
                if(this.plugin.fileManager.reports.contains(args[0])) {
                    ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                    if(pp != null) {
                        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(pp.getServer().getInfo().getName());
                        p.connect(serverInfo);
                        p.sendMessage(new TextComponent(this.plugin.reportprefix+"§7Du hast den Report §aangenommen"));
                        this.plugin.permissionsManager.sendToTeam("§e"+p.getName()+"§7 hat den Report von §c"+args[0]+" §aangenommen!");
                        this.plugin.fileManager.reports.remove(args[0]);
                    } else {
                        sender.sendMessage(new TextComponent(this.plugin.reportprefix+"§cDer Spieler ist nicht mehr online!"));
                        this.plugin.fileManager.reports.remove(args[0]);
                    }

                } else {
                    sender.sendMessage(new TextComponent(this.plugin.reportprefix+"§cDer Report wurde bereits bearbeitet!"));
                }
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

