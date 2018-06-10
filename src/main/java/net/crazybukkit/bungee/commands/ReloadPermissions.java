package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoClan;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadPermissions extends Command {

    private Bungee plugin;

    public ReloadPermissions(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 0) {
                p.sendMessage(new TextComponent(plugin.getPrefix()+"§7Du hast alle Permissions neugeladen"));
                MongoClan mongoClan = MongoClan.getClan(plugin,"Team");
                mongoClan.createClan(p,"Team");
            } else {

            }
        } else {
            p.sendMessage(new TextComponent(plugin.getPrefix()+"§cDu hast keine rechte für den Befehl!"));
        }
    }
}
