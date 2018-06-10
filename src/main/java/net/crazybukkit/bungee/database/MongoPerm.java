package net.crazybukkit.bungee.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.crazybukkit.bungee.Bungee;
import org.bson.Document;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class MongoPerm {
    private Bungee plugin;

    public int id;
    public String name;

    public FindIterable<Document> find;

    public ArrayList<String> permissions = new ArrayList<>();

    public ArrayList<String> groups = new ArrayList<>();

    private MongoPerm(Bungee plugin, int id,String name) {

        this.plugin = plugin;
        this.id = id;
        this.name = name;
        this.find = plugin.getMongoManager().getPerms().find(Filters.eq("id", id));
       // load();

    }

    public static MongoPerm getPlayer(Bungee plugin, int id,String name) {

        return new MongoPerm(plugin, id,name);

    }



    public static MongoPerm getGroupByName(Bungee plugin, String name) {
        FindIterable<Document> find = plugin.getMongoManager().getPerms().find(Filters.eq("name", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoPerm(plugin, (Integer) document.get("id"),(String) document.get("name"));



    }

    public static MongoPerm getGroupByID(Bungee plugin, String name) {
        FindIterable<Document> find = plugin.getMongoManager().getPerms().find(Filters.eq("id", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoPerm(plugin, (Integer) document.get("id"),(String) document.get("name"));


    }



    private void setObject(String value, Object object) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject(value, object));

        BasicDBObject document = new BasicDBObject().append("id", id);
        plugin.getMongoManager().getPerms().updateOne(document, newDocument);
    }

    private Object getObject(String value) {
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return document.get(value);
    }

    public void getAsyncObject(String value, Consumer<Document> consumer) {
        new Thread(() -> {
            Document document = find.first();
            consumer.accept(document);

        }).start();
    }

    public void setAsyncObject(String value, Object o) {
        new Thread(() -> {
            setObject(value,o);
        });
    }

    public void load() {
        Document document = find.first();
        if(document != null) {
                permissions = (ArrayList<String>) document.get("permissions");
        }
    }




    public void create(String prefix) {
        Document document = find.first();
        if (document != null) {
            return;
        }
        
            permissions = new ArrayList<>();
                document = new Document()
                .append("id", id)
                .append("name", name)
                .append("prefix", prefix)
                .append("permissions",permissions);
        plugin.getMongoManager().getPerms().insertOne(document);

    }

    public void addPermissions(String perms) {
        if(!permissions.contains(perms)) {
            permissions.add(perms);
            setAsyncObject("permissions",permissions);
        }
    }

    public void removePermissions(String perm) {
        if(permissions.contains(perm)) {
            permissions.remove(perm);
            setAsyncObject("permissions",permissions);
        }
    }

    public String getAllPerms() {
        for (int i = 0; i < permissions.size();i++) {
            return permissions.get(i);
        }
        return null;
    }

    public void setName(String name){
        setAsyncObject("name",name);
    }

    public String getName() {
        return (String) getObject("name");
    }

    public void setPrefix(String prefix) {
        setAsyncObject("prefix",prefix);
    }

    public String getPrefix() {
        return (String) getObject("prefix");
    }













}

