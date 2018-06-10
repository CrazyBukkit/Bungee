/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;

/**
 *
 * @author tebbh
 */
public class MongoManager {
    
    private final String hostname;
    private final int port;
    
    private MongoClient client;
    private MongoDatabase database;
    
    private MongoCollection<Document> players;
    private MongoCollection<Document> friends;
    private MongoCollection<Document> perms;
    private MongoCollection<Document> clans;
    private MongoCollection<Document> chatlog;

    public MongoManager(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        Bungee.getPlugin().getProxy().getConsole().sendMessage(Bungee.getPlugin().getPrefix()+"§7MongoDB was Sucseful connectet!");
    }
    
    
    public void connect() {
        this.client = new MongoClient(hostname,port);
        this.database = this.client.getDatabase("NaturFront");
        this.players = this.database.getCollection("players");
        this.friends = this.database.getCollection("friends");
        this.perms = this.database.getCollection("permissions");
        this.clans = this.database.getCollection("clans");
        this.chatlog = this.database.getCollection("chatlog");

    }
    
    
    public void connect(String username, String password, String database) {
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        this.client = new MongoClient(new ServerAddress(hostname, port),Arrays.asList(credential));
        
        this.database = this.client.getDatabase("thefreshgamer");
        this.players = this.database.getCollection("players");
        this.friends = this.database.getCollection("friends");
        this.perms = this.database.getCollection("permissions");
        this.clans = this.database.getCollection("clans");
        this.chatlog = this.database.getCollection("chatlog");
    }

    public MongoCollection<Document> getPlayers() {
        return players;
    }

    public MongoCollection<Document> getFriends() {
        return friends;
    }

    public MongoCollection<Document> getPerms() {
        return perms;
    }

    public MongoCollection<Document> getClans() {
        return clans;
    }


    public MongoCollection<Document> getChatlog() {
        return chatlog;
    }
}
