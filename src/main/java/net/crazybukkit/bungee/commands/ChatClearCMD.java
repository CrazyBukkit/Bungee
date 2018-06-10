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
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class ChatClearCMD extends Command{
    
    private Bungee plugin;
    
    
    public ChatClearCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getMute(p)) {
            if(args.length == 0) {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast den Chat geleert"));
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(!this.plugin.permissionsManager.getTeam(all)) {
                       for(int i = 0; i < 40;i++) {
                            all.sendMessage(new TextComponent("     "));
                       }
                        
                        all.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Der Globale Chat wurde geleert!"));
                    }



                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7/cc <Um den Chat zu leeren>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+ "§cDu hast keine Rechte für den Befehl"));
        }

    }
}
