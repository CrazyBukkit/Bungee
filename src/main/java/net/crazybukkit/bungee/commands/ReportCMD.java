/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class ReportCMD extends Command{
    
    
    private Bungee plugin;
    
    public ReportCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    public static ArrayList<ProxiedPlayer> l = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 2) {
            final ProxiedPlayer p = (ProxiedPlayer)sender;
            String name = args[0];
            String grund = args[1];
            ProxiedPlayer toreport = ProxyServer.getInstance().getPlayer(args[0]);
            if(ProxyServer.getInstance().getPlayer(name) == null) {
                sender.sendMessage(new TextComponent(this.plugin.reportprefix+"§cDer Spieler ist nicht online"));
                return;
            }
            if(toreport == sender) {
                sender.sendMessage(new TextComponent(this.plugin.reportprefix+"§cDu kannst dich nicht selbst reporten"));
                return;
            }

            if(l.contains(p)) {
                p.sendMessage(new TextComponent(this.plugin.reportprefix+"§cWarte bitte bis zu deinem n§chsten Report"));
            } else {
                TextComponent c = new TextComponent(this.plugin.reportprefix+"§8[§c§bÜberprüfen§8] §7(Server: "+p.getServer().getInfo().getName()+")");
                c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reporta "+name));
                c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§c§bÜberprüfen").create()));
                sender.sendMessage(new TextComponent(this.plugin.reportprefix + "§7Du hast den Spieler §e" + name + " §7erfolgreich reportet!"));
                this.plugin.permissionsManager.sendToTeam("§7Der Spieler §e"+sender.getName()+"§7 hat den Spieler §c"+name+"§7 reportet\n"+this.plugin.reportprefix+"§7Grund: §e"+grund);
                this.plugin.fileManager.reports.add(name);
                plugin.sendToServer("report", sender.getName()+","+name+","+grund);
                l.add(p);
                ProxyServer.getInstance().getScheduler().schedule(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        l.remove(p);
                    }
                }, 120, TimeUnit.SECONDS);



            }

        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/report <spielername> <grund>"));
        }

    }
}

