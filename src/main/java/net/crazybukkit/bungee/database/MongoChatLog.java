package net.crazybukkit.bungee.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.crazybukkit.bungee.Bungee;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class MongoChatLog {
    private Bungee plugin;

    public UUID uuid;
    public String name;

    public FindIterable<Document> find;

    public ArrayList<List<String>> chatlog;

    private MongoChatLog(Bungee plugin, UUID uuid,String name) {

        this.plugin = plugin;
        this.uuid = uuid;
        this.name = name;
        this.find = plugin.getMongoManager().getChatlog().find(Filters.eq("uuid", uuid));

    }

    public static MongoChatLog getPlayer(Bungee plugin, UUID uuid,String name) {

        return new MongoChatLog(plugin, uuid,name);

    }



    public static MongoChatLog getPlayerByName(Bungee plugin, String name) {
        FindIterable<Document> find = plugin.getMongoManager().getChatlog().find(Filters.eq("name", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoChatLog(plugin, (UUID) document.get("uuid"),(String) document.get("name"));


    }

    public static MongoChatLog getChatLogByID(Bungee plugin, String name) {
        FindIterable<Document> find = plugin.getMongoManager().getChatlog().find(Filters.eq("id", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoChatLog(plugin, (UUID) document.get("uuid"),(String) document.get("name"));


    }



    private void setObject(String value, Object object) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject(value, object));

        BasicDBObject document = new BasicDBObject().append("uuid", uuid);
        plugin.getMongoManager().getChatlog().updateOne(document, newDocument);
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




    public void create(List<String> chat) {
        Document document = find.first();
        if (document != null) {
            getAsyncObject("chatlog", d -> chatlog = (ArrayList<List<String>>) d.get("chatlog"));
            return;
        }
        chatlog = new ArrayList<>();
        chatlog.add(chat);
        String[] id = UUID.randomUUID().toString().split("-");
        document = new Document()
                .append("uuid", uuid)
                .append("name", name)
                .append("id",id[1])
                .append("chatlog", chatlog);
        plugin.getMongoManager().getChatlog().insertOne(document);

    }

    public boolean isChatLoggt() {
        if(getObject("id") == null) {
            return false;
        }
        return true;
    }

    public String getChatLogID() {
        return (String) getObject("id");
    }









}
