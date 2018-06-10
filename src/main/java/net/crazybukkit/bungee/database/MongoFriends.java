/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

import net.crazybukkit.bungee.Bungee;
import org.bson.Document;

/**
 *
 * @author Tebbe
 */
public class MongoFriends {
    
    private Bungee m;

    public UUID uuid;

    public String name;

    public ArrayList<String> Friends;
    public ArrayList<String> Invites;

    private FindIterable<Document> find;

    private MongoFriends(Bungee m, UUID uuid, String name) {
        this.m = m;
        this.uuid = uuid;
        this.name = name;
        this.find = m.getMongoManager().getFriends().find(Filters.eq("uuid", uuid));
        this.create();
    }

    public static MongoFriends getPlayer(Bungee m, UUID uuid, String name) {
        return new MongoFriends(m, uuid, name);
    }

    public static MongoFriends getPlayerByName(Bungee m, String name) {
        FindIterable<Document> find = m.getMongoManager().getFriends().find(Filters.eq("name", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoFriends(m, (UUID) document.get("uuid"), (String) document.get("name"));


    }

    private void setObject(String value, Object object) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject(value, object));

        BasicDBObject document = new BasicDBObject().append("uuid", uuid);
        m.getMongoManager().getFriends().updateOne(document, newDocument);
    }

    private Object getObject(String value) {
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return document.get(value);
    }

    public void create() {
        Document document = find.first();
        if (document != null) {
            Friends = (ArrayList<String>) document.get("Friends");
            Invites = (ArrayList<String>) document.get("Invites");
            //Add Friends to ArrayList;
            return;
        }
        Friends = new ArrayList<>();
        Invites = new ArrayList<>();
        document = new Document()
        .append("uuid", uuid)
        .append("name", name)
        .append("online",true)
        .append("GetInvites", true)
        .append("GetMessage", true)
        .append("GetJump", true)
        .append("Friends", Friends)
        .append("Invites", Invites)
        .append("Server", "");
        m.getMongoManager().getFriends().insertOne(document);
    }

    public void setInvites(boolean b){
        setObject("GetInvites", b);
    }
    public void setServer(String b){
        setObject("Server", b);
    }
    public void setMessage(boolean b){
        setObject("GetMessage", b);
    }
    public void setJump(boolean b){
        setObject("GetJump", b);
    }

    public boolean getJump(){
        return (Boolean)getObject("GetJump");
    }

    public boolean getMessages(){
        return (Boolean)getObject("GetMessage");
    }
    
    public String getServer(){
        return (String)getObject("GetMessage");
    }

    public boolean getInvites(){ 
        return (Boolean)getObject("GetInvites");
    }

    public void setOnline(boolean b) {
        setObject("online", b);
    }

    public boolean isOnline() {
        return (Boolean) getObject("online");
    }

    public void addInvite(String friend){
        if(!isInvited(friend)) {
            Invites.add(friend);
            setObject("Invites",Invites);
        }
    }
    public void removeInvite(String friend){
        if(isInvited(friend)){
            Invites.remove(friend);
            setObject("Invites",Invites);
        }
    }

    public boolean isInvited(String friend){
        return Invites.contains(friend);
    }

    public void addFriend(String friend){
        if(!isFriend(friend)) {
            Friends.add(friend);
            removeInvite(friend);
            setObject("Friends",Friends);
            MongoFriends mf = MongoFriends.getPlayer(m,m.uuidFetcher.getUUID(friend),friend);
            mf.removeInvite(name);
            mf.addFriend(name);
        }
    }
    public void removeFriend(String friend){
        if(isFriend(friend)){
            Friends.remove(friend);
            setObject("Friends",Friends);
            MongoFriends mf = MongoFriends.getPlayer(m,m.uuidFetcher.getUUID(friend),friend);
            mf.removeFriend(name);
        }
    }

    public boolean isFriend(String friend){
        return Friends.contains(friend);
    }

}
