/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class unbanCMD extends Command{
    
    private Bungee plugin;
    
    public unbanCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getMute(p)) {
            if(args.length == 1) {
                String name = args[0];
                MongoPlayer mongoPlayer = MongoPlayer.getPlayer(plugin,plugin.getUUID(name),name);
                if(mongoPlayer.isBanned()) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast den Spieler §e" + args[0] + "§7 vom Netzwerk entbannt"));
                    mongoPlayer.unban();
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist nicht gebannt"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/unban <name>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

