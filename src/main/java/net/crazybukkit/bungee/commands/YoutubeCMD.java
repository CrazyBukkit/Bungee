/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class YoutubeCMD extends Command{
    
    private Bungee plugin;
    
    public YoutubeCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (args.length == 0)
        {
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7/youtube §e700 §7Um die cAnforderungen für den PremiumPlus Rang zu sehen"));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7/youtube §e1500 §7Um die cAnforderungen für den Youtuber Rang zu sehen"));
        }
        else if (args[0].equalsIgnoreCase("1500"))
        {
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Vorraussetzungen für §5YouTuber§7:"));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Dein §eKanal §7muss mindestens §e1500 Abonenten§7 besitzen."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Du benötigst eine angemessene §eKlickzahl§7."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Ebenfalls musst du mindestens §e1 Video §7aufnehmen."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Dieses §eVideo §7muss in angemessener §eQualität §7und §eLänge §7sein."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Alles erfüllt? Beantrage den §5YouTuber §7Rang im Teamspeak §ets.McSpooky.de"));
        }
        else if (args[0].equalsIgnoreCase("700"))
        {
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Vorraussetzungen für §6Premium+§7:"));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Dein §eKanal §7muss mindestens §c700 Abonenten§7 besitzen."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Du benötigst eine angemessene §cKlickzahl§7."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Ebenfalls musst du mindestens §e1 Video §7auf dem Server aufnehmen."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Dieses §eVideo §7muss in angemessener §eQualität §7und §eLänge §7sein."));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7» Alles erfüllt? Beantrage den §6PremiumPlus §7Rang im Teamspeak §ets.McSpooky.de"));
        }
        else
        {
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7/youtube §e700 §7Um die cAnforderungen für den PremiumPlus Rang zu sehen"));
            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7/youtube §e1500 §7Um die cAnforderungen für den Youtuber Rang zu sehen"));
        }
    }
}
