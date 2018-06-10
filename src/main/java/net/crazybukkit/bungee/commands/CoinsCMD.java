/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class CoinsCMD extends Command{
    
    private Bungee plugin;
    
    public CoinsCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }
    

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(sender.getName()));
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast aktuell §e"+cp.getCoins()+"§7 Coins"));
        } else if(args.length >= 3) {
            if(args[0].equalsIgnoreCase("add")) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                if(target != null) {
                    CrazyPlayer cp = new CrazyPlayer(this.plugin.uuidFetcher.getUUID(target.getName()));
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast dem Spieler §e"+target.getName()+"§e "+args[2]+"§7 Coins gegeben"));
                    cp.setCoins(cp.getCoins()+Integer.valueOf(args[2]));
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist nicht online!"));
                }
                
            }
        } 
        

    }
}
