/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.manager;

import java.util.ArrayList;
import java.util.UUID;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoPlayer;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 *
 * @author tebbh
 */
public class PermissionsManager {
    
    
   
    
     private Bungee plugin;
    
    public String team = "§8│ §cTeam §8* ";
    
    public ArrayList<String> TeamMessage = new ArrayList<>();   
    
   
    

    public PermissionsManager(Bungee aThis) {
       this.plugin = aThis;
    }     
    
    
    public boolean isInGroupeCache(ProxiedPlayer p,String rang) { 
        return plugin.getCache().get(plugin.getUUID(p.getName())).contains(rang);
    }  

    public boolean isHighRang(ProxiedPlayer p) {
        CrazyPlayer cp = new CrazyPlayer(p);
        if (cp.isinGroupe(Rang.Administrator.getName()) || cp.isinGroupe(Rang.Entwickler.getName()) || 
                cp.isinGroupe(Rang.Moderator.getName()) || cp.isinGroupe(Rang.Content.getName()) || cp.isinGroupe(Rang.Supporter.getName()) || cp.isinGroupe(Rang.Builder.getName()) || 
                cp.isinGroupe(Rang.Platin.getName()) || cp.isinGroupe(Rang.YouTube.getName()) || cp.isinGroupe(Rang.PremiumPlus.getName()) || cp.isinGroupe(Rang.Premium.getName())) {

            return true;
        }

        return false;

    }

    public boolean getHighRangCache(ProxiedPlayer p) {
        System.out.println("Rang: "+this.plugin.cache.get(p.getName()));
        CrazyPlayer cp = new CrazyPlayer(p.getName());
        if (cp.isInGroupeCache(Rang.Administrator.getName()) || cp.isInGroupeCache(Rang.Entwickler.getName()) || 
                cp.isInGroupeCache(Rang.Moderator.getName()) || cp.isInGroupeCache(Rang.Content.getName()) || cp.isInGroupeCache(Rang.Supporter.getName()) || 
                cp.isInGroupeCache(Rang.Builder.getName()) || cp.isInGroupeCache(Rang.YouTube.getName()) || cp.isInGroupeCache(Rang.PremiumPlus.getName()) || cp.isInGroupeCache(Rang.Premium.getName())) {

            return true;
        }

        return false;

    }
    

    public boolean getVIP(ProxiedPlayer p) {
        CrazyPlayer cp = new CrazyPlayer(p);
        if (cp.isInGroupeCache(Rang.Administrator.getName()) || cp.isInGroupeCache(Rang.Entwickler.getName()) || 
                cp.isInGroupeCache(Rang.Moderator.getName()) || cp.isInGroupeCache(Rang.Content.getName()) || cp.isInGroupeCache(Rang.Supporter.getName()) || 
                cp.isInGroupeCache(Rang.Builder.getName()) || cp.isInGroupeCache(Rang.YouTube.getName()) || cp.isInGroupeCache(Rang.PremiumPlus.getName())) {

            return true;
        }

        return false;

    }

    public boolean getTeam(ProxiedPlayer p) {
        CrazyPlayer cp = new CrazyPlayer(p);
        if (cp.isInGroupeCache(Rang.Administrator.getName()) || cp.isInGroupeCache(Rang.Entwickler.getName()) || cp.isInGroupeCache(Rang.Moderator.getName()) || cp.isInGroupeCache(Rang.Content.getName()) || cp.isInGroupeCache(Rang.Supporter.getName()) || cp.isInGroupeCache(Rang.Builder.getName())) {
            return true;
        }

        return false;

    }
    
     public boolean getMute(ProxiedPlayer p) {
         CrazyPlayer cp = new CrazyPlayer(p);
        if (cp.isInGroupeCache(Rang.Administrator.getName()) || cp.isInGroupeCache(Rang.Entwickler.getName()) || cp.isInGroupeCache(Rang.Moderator.getName()) || cp.isInGroupeCache(Rang.Content.getName()) || cp.isInGroupeCache(Rang.Supporter.getName())) {
            return true;
        }

        return false;

    }
     
     public boolean getBan(ProxiedPlayer p) {
         CrazyPlayer cp = new CrazyPlayer(p);
        if (cp.isInGroupeCache(Rang.Administrator.getName()) || cp.isInGroupeCache(Rang.Entwickler.getName()) 
                || cp.isInGroupeCache(Rang.Moderator.getName())) {
            return true;
        }

        return false;

    }
    
    public boolean getTeamDatabase(UUID uuid,String name) {
        MongoPlayer cp = MongoPlayer.getPlayer(plugin,uuid,name);
        if (cp.isInGroup(Rang.Administrator.getName()) ||  cp.isInGroup(Rang.Entwickler.getName()) ||
                 cp.isInGroup(Rang.Moderator.getName()) ||  cp.isInGroup(Rang.Content.getName()) ||  cp.isInGroup(Rang.Supporter.getName()) ||
                 cp.isInGroup(Rang.Builder.getName())) {
            return true;
        }

        return false;

    
    }
    
    
     public String getColor(String rang) {
        Rang r = Rang.getUnit(rang);
        return r.getColor();
     }
     public String getRangFormat(ProxiedPlayer player,String rang) {
         if (rang.contains(Rang.Premium.getName())) {
            return "§6"+rang+"§8 ┃ §6"+player.getName();   
        } else if(rang.contains(Rang.PremiumPlus.getName())) {
             return "§3"+rang+"§8 ┃ §3"+player.getName(); 
        } else if (rang.contains(Rang.YouTube.getName())) {
            return "§5"+rang+"§8 ┃ §5"+player.getName();
        } else if (rang.contains(Rang.Platin.getName())) {
            return "§f"+rang+"§8 ┃ §f"+player.getName();
        } else if (rang.contains(Rang.Builder.getName())) {
            return "§e"+rang+"§8 ┃ §e"+player.getName();
        } else if (rang.contains(Rang.Supporter.getName())) {
            return "§a"+rang+"§8 ┃ §a"+player.getName();
        } else if (rang.contains(Rang.Content.getName())) {
            return "§b"+rang+"§8 ┃ §b"+player.getName();
        } else if (rang.contains(Rang.Moderator.getName())) {
            return "§9"+rang+"§8 ┃ §9"+player.getName();
        } else if (rang.contains(Rang.Entwickler.getName())) {
            return "§b"+rang+"§8 ┃ §b"+player.getName();
        } else if (rang.contains(Rang.Administrator.getName())) {
            return "§4"+rang+"§8 ┃ §4"+player.getName();
        } else {
            return "§7"+rang+"§8 ┃ §7"+player.getName();
        }
    
    }
     
     public String getRangCache(ProxiedPlayer player) {
         return this.plugin.getCache().get(player.getName());
         
     }
     
       public void sendToTeam(String message) {
            for (ProxiedPlayer isteam : ProxyServer.getInstance().getPlayers()) {
                if (getTeam(isteam)) {
                    if (!TeamMessage.contains(isteam.getName())) {
                    isteam.sendMessage(new TextComponent(team + message));
                }
            }
        }
    }
       
       public void sendToAdmin(String message) {          
        for (ProxiedPlayer isteam : ProxyServer.getInstance().getPlayers()) {
            CrazyPlayer cp = new CrazyPlayer(isteam);
            if (cp.isInGroupeCache(Rang.Administrator.getName())) {
                    isteam.sendMessage(new TextComponent(team + message));
                
            }
        }
    }
            


    
    
    
}