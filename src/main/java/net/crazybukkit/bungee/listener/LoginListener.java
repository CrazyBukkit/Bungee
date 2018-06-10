/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.listener;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoFriends;
import net.crazybukkit.bungee.database.MongoPlayer;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author tebbh
 */
public class LoginListener implements Listener{
    
    private Bungee plugin;
    
    
    private String header = "§8⬜ §2NaturFrontNET §7MiniGames Netzwerk §8⬜\n      §7Online Spieler §8» §e%onlineplayers%§7/§e%maxplayers% §8➜ §7Du befindest dich auf §e%server%      \n";
    
    private String footer = "\n§7TeamSpeak§: §eNaturFront.net\n§7Shop: §eshop.NaturFront.net";

    public LoginListener(Bungee plugin) {
        this.plugin = plugin;
    }
    
         
    
    
    @EventHandler
    public void onLoginDatabase(LoginEvent e) {
     MongoPlayer mp = MongoPlayer.getPlayer(plugin, this.plugin.uuidFetcher.getUUID(e.getConnection().getName()), e.getConnection().getName());
           this.plugin.runnableManager.async(() -> {
                mp.create();
           });
           CrazyPlayer crazyPlayer = new CrazyPlayer(plugin.getUUID(e.getConnection().getName()));
           plugin.getCache().put(plugin.getUUID(e.getConnection().getName()),mp.getRang());
           onLogin(e);
    }
    
    
    @EventHandler
    public void onLogin(LoginEvent e) {
     MongoPlayer mp = MongoPlayer.getPlayer(plugin, this.plugin.uuidFetcher.getUUID(e.getConnection().getName()), e.getConnection().getName());
            if (!e.getConnection().getName().equals(mp.getName()))           {
                    mp.setName(e.getConnection().getName());
            }
           MongoFriends mf = MongoFriends.getPlayer(this.plugin, this.plugin.uuidFetcher.getUUID(e.getConnection().getName()), e.getConnection().getName());
           this.plugin.runnableManager.async(() -> {
               mf.create();
           });
           this.plugin.runnableManager.async(() -> {
              mf.setOnline(true);
           });
           System.out.println(plugin.cache.get(plugin.getUUID(e.getConnection().getName())));

        
       
    }
    
    
    @EventHandler
    public void onCheckWartung(ServerSwitchEvent e) {
        if(this.plugin.fileManager.wartung) {
            if(!this.plugin.permissionsManager.getTeamDatabase(this.plugin.uuidFetcher.getUUID(e.getPlayer().getName()),e.getPlayer().getName())) {
               e.getPlayer().disconnect(new TextComponent("§cDas Netzwerk ist momentan in Wartungen\n§e Twitter: §b%Twitter%%"));
            }
        }

    }
    
    
    @EventHandler
    public void onCheckBan(PostLoginEvent e) {
        CrazyPlayer crazyPlayer = new CrazyPlayer(e.getPlayer().getName());
        if(crazyPlayer.isBanned()) {
            e.getPlayer().disconnect(new TextComponent("§7Du wurdest vom §cNaturFrontNET §7Netzwerk gebannt!\n"+
                    "\n§7Grund » "+crazyPlayer.getBanReason()+"\n"+
                    "§7Verbleibende Zeit » "+crazyPlayer.getBanEndTime()+"\n"+
                    "§cDu kannst einen Entbannungsantrag im Teamspeak erstellen. §6NaturFront.de"));
        }
    }
    
         @EventHandler
        public void checkVoll(PostLoginEvent e) {
            if (ProxyServer.getInstance().getPlayers().size() >= this.plugin.fileManager.slots) {
                if (!this.plugin.permissionsManager.getHighRangCache(e.getPlayer())) {
                    e.getPlayer().disconnect(new TextComponent("§cDer Server ist voll kaufe dir §6Premium §cdamit du immer joinen kannst\n§7Shop: §6shop.NaturFrontNET.de"));


                }

            }

        }
        
        @EventHandler
        public void checkRang(ServerConnectEvent e){
            CrazyPlayer crazyPlayer = new CrazyPlayer(e.getPlayer().getName());
            if(crazyPlayer.isTempRang()) {
               this.plugin.runnableManager.schedule(() -> {
                    e.getPlayer().sendMessage(new TextComponent(this.plugin.getPrefix()+"§7Du hast deinen §6Premium §7Rang noch \n"+crazyPlayer.getRangTimeFormat()));
               }, 500);
            }

        }
        
        
        
        @EventHandler
        public void onPostLoginTab(PostLoginEvent e) {
            for(ProxiedPlayer all : this.plugin.getProxy().getPlayers()) {
                all.setTabHeader(formatTab(all,header) ,formatTab(all, footer));
            }
        }
        
        @EventHandler
        public void onServerConnectedTab(ServerConnectedEvent e) {
            ProxiedPlayer player = e.getPlayer();         
               player.setTabHeader(formatTab(player, header.replaceAll("%server%", e.getServer().getInfo().getName())) ,formatTab(player, footer.replaceAll("%server%", e.getServer().getInfo().getName())));
            
        }
        
        @EventHandler
        public void onDisconnect(PlayerDisconnectEvent e) {
            MongoFriends friends = MongoFriends.getPlayer(plugin, plugin.uuidFetcher.getUUID(e.getPlayer().getName()), e.getPlayer().getName());
            this.plugin.runnableManager.async(() -> {
                friends.setOnline(false);
            });
            
        }
        
        @EventHandler
        public void onServerDisconnectTab(ServerDisconnectEvent e) {
            ProxiedPlayer player = e.getPlayer();
            for(ProxiedPlayer all : this.plugin.getProxy().getPlayers()) {
                all.setTabHeader(formatTab(all, header.replaceAll("%server%",e.getPlayer().getServer().getInfo().getName())) ,formatTab(all, footer.replaceAll("%server%",e.getPlayer().getServer().getInfo().getName())));
            }
        }
        
        @EventHandler
        public void onSwitch(ServerSwitchEvent e) {
         ProxiedPlayer player = e.getPlayer();
         MongoFriends mongoFreunde = MongoFriends.getPlayer(this.plugin,this.plugin.uuidFetcher.getUUID(player.getName()),player.getName());
         plugin.runnableManager.async(() -> {
             mongoFreunde.setServer(player.getServer().getInfo().getName());
         });
    }
        
        
        private TextComponent formatTab(ProxiedPlayer player, String message){
            return new TextComponent(message.replaceAll("%onlineplayers%", this.plugin.getProxy().getOnlineCount()+"")
            .replaceAll("%maxplayers%", this.plugin.fileManager.getSlots()+""));
        }
    
    
}
