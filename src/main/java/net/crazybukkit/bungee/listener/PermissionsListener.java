package net.crazybukkit.bungee.listener;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PermissionsListener implements Listener{

    private final Bungee plugin;


    public PermissionsListener(Bungee plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPermCheck(PermissionCheckEvent e) {
        if(!(e.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        if(plugin.permissionsManager.isInGroupeCache(p, Rang.Administrator.getName())) {
            e.setHasPermission(true);
        } else if(plugin.permissionsManager.getTeam(p)) {
            e.setHasPermission(true);
        } else {
            e.setHasPermission(false);
    }
    }
}
