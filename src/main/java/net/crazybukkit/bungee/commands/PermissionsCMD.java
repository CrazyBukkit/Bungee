package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PermissionsCMD extends Command{

    private final Bungee plugin;

    public PermissionsCMD(Bungee plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        /*ProxiedPlayer p = (ProxiedPlayer)sender;
        if(plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            if(args.length == 4) {
                if(args[0].equalsIgnoreCase("create")) {
                    String prefix = args[3];
                    String name = args[2];
                    int id = Integer.valueOf(args[1]);
                    MongoPerm mongoPerm = MongoPerm.getPlayer(plugin,id,name);
                    plugin.runnableManager.async(() -> {
                        mongoPerm.create();
                    });
                    p.sendMessage(new TextComponent(plugin.getPrefix()+"§7Du hast die Gruppe §a"+name+"§7 erstellt!\n"+
                    "§7ID » §a"+id+
                    "§7Prefix » "+prefix.replace("&","§")));
                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("delete")) {

                }
            }
        }*/
    }
}
