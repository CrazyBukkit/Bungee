/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class setSlotsCMD extends Command{
    
       private Bungee plugin;
    
       public setSlotsCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(this.plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 1) {
                this.plugin.fileManager.slots = Integer.valueOf(args[0]);
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast die Slots auf §e"+args[0]+"§7 gesetzt"));
                this.plugin.runnableManager.async(() -> {
                    this.plugin.fileManager.setSlots(Integer.valueOf(args[0]));
                });
            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/slots <slots>"));
            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }

    }
}

