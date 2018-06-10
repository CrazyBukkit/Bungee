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
public class GotoCMD extends Command{
    
    private Bungee plugin;
    
    public GotoCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getMute(p)) {
            if(args.length == 1) {
                String server = args[0];
                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server);
                if(serverInfo != null) {
                    p.connect(serverInfo);
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Server ist nicht online oder existiert nicht!"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7/goto <servername>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+ "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

