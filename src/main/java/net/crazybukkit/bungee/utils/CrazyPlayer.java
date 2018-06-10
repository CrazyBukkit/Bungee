/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.utils;


import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoFriends;
import net.crazybukkit.bungee.database.MongoPlayer;
import net.crazybukkit.naturapi.utils.Rang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.util.UUID;

/**
 *
 * @author tebbh
 */
public class CrazyPlayer {
    
    
    private static Bungee plugin;
    MongoPlayer database;
    MongoFriends friends;
    ProxiedPlayer p;
    String name;
    UUID uuid;
    

    public CrazyPlayer(Bungee cb) {
        plugin = cb;
        
    }
    
    
    public CrazyPlayer(ProxiedPlayer p) {
        this.p = p;
        this.uuid = Bungee.plugin.uuidFetcher.getUUID(p.getName());
        this.name = p.getName();
        init();        
    }
    
    public CrazyPlayer(String name) {
        this.uuid = Bungee.plugin.uuidFetcher.getUUID(name);
        this.name = name;
        this.p = ProxyServer.getInstance().getPlayer(name);
        init();
    }
    
    public CrazyPlayer(UUID uuid) {
        this.uuid = uuid;
        init();
    }
    
    
    private void init() {
        this.database = getMongoPlayer();
        this.friends = getFriends();
        
    }

    public ProxiedPlayer getPlayer() {
        return this.p;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }  
    
    public Server getServer() {
        return this.p.getServer();
    }
    
       
    
    
    public void ban(CommandSender sender, String reason, long end) {
        this.plugin.runnableManager.async(() -> {
            this.database.ban(sender, reason, end);
        });
    }
    
    
    public void mute(CommandSender sender,String reason,long end) {
        this.plugin.runnableManager.async(() -> {
            this.database.mute(sender, reason, end);
        });
    }
    
    public void setBanPoins(int points) {
        this.plugin.runnableManager.async(() -> {
            this.database.setBanPoins(points);
        });
    }
    
    
    public void addBanPoins(int points) {
        this.plugin.runnableManager.async(() -> {
            setBanPoins(getBanPoins()+points);
        });
    }
    
    public int getBanPoins() {
        return this.database.getBanPoins();
    }
    
    
    public boolean isBanned() {
        return this.database.isBanned();
    }
    
    public String getBanReason() {
        return this.database.getBanReason();
    }
    
    
    public String getBanEndTime() {
        return this.database.getBanTimeReamning(this.uuid);
    }
    
    public String getBanBy() {
        return this.database.getBanBy();
    }
    
    public void unban() {
        this.plugin.runnableManager.async(() -> {
           this.database.unban(); 
        });
    }
    
    
    
    public void setMutePoins(int points) {
        this.plugin.runnableManager.async(() -> {
            this.database.setMutePoins(points);
        });
    }
    
    
    public void addMutePoins(int points) {
        this.plugin.runnableManager.async(() -> {
            setMutePoins(getMutePoins()+points);
        });
    }
    
    public int getMutePoins() {
        return this.database.getMutePoins();
    }    
    
    
    public boolean isMute() {
        return this.database.isMuted();
    }
    
    public String getMuteReason() {
        return this.database.getMuteReason();
    }
    
    public String getMuteEndTime() {
        return this.database.getMuteTimeReamning(uuid);
    }
    
    public long getMuteEnd() {
        return this.database.getMuteTime();
    }
    
    
    public void unmute() {
        this.plugin.runnableManager.async(() -> {
            this.database.unmute();
        });
    }
       
    
    public String getRang() {
        return this.database.getRang();
    }
    
    public long getRangTime() {
        return this.database.getRangTime();
    }
    
    public void setRang(Rang rang) {
        this.plugin.runnableManager.async(() -> {
            this.database.setrang(rang.getName());
        });
    }
    
    public void setTempRang(Rang rang, long time) {
        this.plugin.runnableManager.async(() -> {
            this.database.setTempRang(rang.getName(), time);
        });
    }     
    
    public boolean isTempRang() {
        return this.database.isTempRang();
    }
    
    
    public String getRangTimeFormat() {
        return this.database.getRangTimeReamning(uuid);
    }
    
    
    public void setCoins(int coins) {
        this.plugin.runnableManager.async(() -> {
            this.database.setCoins(coins);
        });
    }
      
    public int getCoins(){
       return this.database.getCoins();
   }
    
    
    public void addCoins(int coins) {
        this.plugin.runnableManager.async(() -> {
            setCoins(getCoins()+coins);
        });
    }
    
    public boolean isinGroupe(String rang) {
        return getRang().contains(rang);
    }
    
    public boolean isInGroupeCache(String rang) { 
        return plugin.getCache().get(plugin.getUUID(p.getName())).contains(rang);
    }    
    
    
    public MongoPlayer getMongoPlayer() {
        return MongoPlayer.getPlayer(Bungee.plugin, uuid, name);
    }
    
    public MongoFriends getFriends() {
        return MongoFriends.getPlayer(Bungee.plugin, uuid, name);
    }
    
    //Friend
    
    public void setInvites(boolean b){
        this.plugin.runnableManager.async(() -> {
            this.friends.setInvites(b);
        });
    }
    public void setServer(String b){
        this.plugin.runnableManager.async(() -> {
            this.friends.setServer(b); 
        });
    }
    public void setMessage(boolean b){
        this.plugin.runnableManager.async(() -> {
           this.friends.setMessage(b);
        });
    }
    public void setJump(boolean b){
        this.plugin.runnableManager.async(() -> {
            this.friends.setJump(b);
        });
    }

    public boolean getJump(){
        return this.friends.getJump();
    }

    public boolean getMessages(){
        return this.friends.getMessages();
    }
    
    public String getFriendServer(){
        return this.friends.getServer();
    }

    public boolean getInvites(){ 
        return this.friends.getInvites();
    }

    public void setOnline(boolean b) {
        this.plugin.runnableManager.async(() -> {
            this.friends.setOnline(b);
        });        
    }

    public boolean isOnline() {
        return this.friends.isOnline();
    }
   public void addInvite(String friend){
       this.friends.addInvite(friend);
    }
    public void removeInvite(String friend){
      this.friends.removeInvite(friend);
    }

    public boolean isInvited(String friend){
        return this.friends.Invites.contains(friend);
    }

    public void addFriend(String friend){
      this.friends.addFriend(friend);
    }
    public void removeFriend(String friend){
        this.friends.removeFriend(friend);
    }

    public boolean isFriend(String friend){
        return this.friends.Friends.contains(friend);
    }

    
    
  

}
