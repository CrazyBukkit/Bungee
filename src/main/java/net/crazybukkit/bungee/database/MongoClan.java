package net.crazybukkit.bungee.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

public class MongoClan {
    private Bungee m;


    public String name;

    public ArrayList<String> members;
    public ArrayList<String> moderatoren;
    public ArrayList<String> leader;
    public ArrayList<String> invites;

    private FindIterable<Document> find;

    private MongoClan(Bungee m, String name) {
        this.m = m;
        this.name = name;
        this.find = m.getMongoManager().getFriends().find(Filters.eq("name", name));
        this.load();
    }

    public static MongoClan getClan(Bungee m,String name) {
        return new MongoClan(m, name);
    }


    private void setObject(String value, Object object) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject(value, object));

        BasicDBObject document = new BasicDBObject().append("name", name);
        m.getMongoManager().getFriends().updateOne(document, newDocument);
    }

    private Object getObject(String value) {
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return document.get(value);
    }

    public void load() {
        Document document = find.first();
        if (existClan()) {
            members = (ArrayList<String>) document.get("members");
            moderatoren = (ArrayList<String>) document.get("moderatoren");
            leader = (ArrayList<String>) document.get("Leader");
            invites = (ArrayList<String>) document.get("invites");
            return;
        }
    }

    public void createClan(ProxiedPlayer owner,String tag) {
        if(!existClan()) {
            Document document;
            String[] id = UUID.randomUUID().toString().split("-");
            if(tag.length() <= 4) {
                members = new ArrayList<>();
                moderatoren = new ArrayList<>();
                leader = new ArrayList<>();
                invites = new ArrayList<>();
                leader.add(owner.getUniqueId().toString());
                document = new Document()
                        .append("id",id[0])
                        .append("name",name)
                        .append("tag",tag)
                        .append("members",members)
                        .append("moderatoren",moderatoren)
                        .append("Leader",moderatoren)
                        .append("invites",invites);
                m.getMongoManager().getClans().insertOne(document);
                System.out.println("create");
            } else {
                owner.sendMessage(new TextComponent("Der Tag ist zu lang"));
            }
        } else{
            owner.sendMessage(new TextComponent("§cDer Clan existiert breits!"));
        }
    }

    public boolean existClan() {
        if(getObject("id") != null) {
            return true;
        }
        return false;
    }



}

