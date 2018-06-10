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
public class unmuteCMD extends Command{
    
    private Bungee plugin;
    
    public unmuteCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getMute(p)) {
            if(args.length == 1) {
                CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(args[0]));
                if(cp.isMute()) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast den Spieler §e" + args[0] + "§7 entmutet"));
                       cp.unmute();
                } else{
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist nicht gemutet"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/unmute <name>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

