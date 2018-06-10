/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import java.util.Arrays;

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
public class KickCMD extends Command{
    
    private Bungee plugin;
    
     public KickCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.getTeam(p)) {
            if(args.length == 2) {
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                if(pp != null) {
                    if (this.plugin.permissionsManager.getTeam(pp)) {
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDu hast keine Teammitglieder kicken!"));
                        return;
                    }
                    String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    String name = args[0];
                    pp.disconnect(new TextComponent("§cDu wurdest vom MCSpookyDE Netzwerk gekickt!\n" + "§cGrund: §b" + reason));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast den Spieler §c" + name + " §7vom Netzwerk gekickt\n" + "§cGrund: §e" + reason));
                    plugin.permissionsManager.sendToTeam("§cKickManager §8» §7"+"\n"+"§7Name §8» §c"+args[1]+ "\n"+
                            "§7By §8» §a"+sender.getName() + "\n");

                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist nicht online"));
                }
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/kick <name> <grund>"));
            }

        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}
