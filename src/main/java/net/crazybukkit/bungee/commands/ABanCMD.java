/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import java.util.Arrays;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
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
public class ABanCMD extends Command{
    
    private Bungee plugin;
    
    public ABanCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 2) {
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                if(this.plugin.permissionsManager.getTeamDatabase(this.plugin.uuidFetcher.getUUID(args[0]), args[0])) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDu kannst keine Administratoren bannen!"));
                    return;
                }
                CrazyPlayer cp = new CrazyPlayer(args[0]);
                if(!cp.isBanned()) {
                    String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    cp.ban(sender, reason, -1);
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast §c"+args[1]+" §4permanent §7vom Netzwerk gebannt\n§7Grund: §b"+reason));
                    this.plugin.permissionsManager.sendToTeam("§e"+sender.getName()+"§7 hat den Spieler §c"+args[1]+" §7gebannt!\n§7Grund: §b"+reason);
                    plugin.permissionsManager.sendToAdmin("§cBanManager §8» §7"+"\n"+"§7Name §8» §c"+args[1]+ "\n"+
                    "§7By §8» §a"+sender.getName() + "\n"+
                    "§7Grund §8» §e"+reason + "\n" +
                    "§7Verbleibende Zeit §8» "+cp.getBanEndTime());
                    pp.disconnect(new TextComponent("§cDu wurdest vom MCSpookyDE Netzwerk gebannt!\n" + "\n" + 
                            "§cGrund: §b"+args[1]+"\n" + "\n" + 
                            "§cVerbleibene Zeit: " + cp.getBanEndTime()));
                }
              } else {
                p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7/aban <spieler> <grund>"));
                p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§4§lDies ist nur ein permanenter Bann"));
            }
        } else {
            p.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDu hast keine Rechte für den Befehl"));
        }

    }
}

