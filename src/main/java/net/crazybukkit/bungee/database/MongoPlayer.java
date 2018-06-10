/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import org.bson.Document;

/**
 *
 * @author tebbh
 */
public class MongoPlayer {
    
    private Bungee plugin;

    public UUID uuid;
    public String name;

    public FindIterable<Document> find;

    private MongoPlayer(Bungee plugin, UUID uuid,String name) {

        this.plugin = plugin;
        this.uuid = uuid;
        this.name = name;
        this.find = plugin.getMongoManager().getPlayers().find(Filters.eq("uuid", uuid));
        
    }

    public static MongoPlayer getPlayer(Bungee plugin, UUID uuid,String name) {
        
        return new MongoPlayer(plugin, uuid,name);

    }
    
    

    public static MongoPlayer getPlayerByName(Bungee plugin, String name) {
        FindIterable<Document> find = plugin.getMongoManager().getPlayers().find(Filters.eq("name", Pattern.compile(name, Pattern.CASE_INSENSITIVE)));
        Document document = find.first();
        if (document == null) {
            return null;
        }
        return new MongoPlayer(plugin, (UUID) document.get("uuid"),(String) document.get("name"));


    }



    private void setObject(String value, Object object) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject(value, object));

        BasicDBObject document = new BasicDBObject().append("uuid", uuid);
        plugin.getMongoManager().getPlayers().updateOne(document, newDocument);
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

    


    public void create() {
        Document document = find.first();
        if (document != null) {
            return;
        }
        document = new Document()
                .append("uuid", uuid)
                .append("name", name)
                .append("Rang", "Spieler")
                .append("RangTime",-1)
                .append("Coins", 500)     
                .append("Nickname", "")   
                .append("Ts3UUID", "NONE")
                .append("isTempRang", false)
                .append("BanReason","")
                .append("BanTime",(long)0)
                .append("BanBy", "")
                .append("BanPoins", 0)
                .append("MuteReason", "")
                .append("MuteTime", 0)
                .append("MuteBy", "")
                .append("MutePoins", 0);
        plugin.getMongoManager().getPlayers().insertOne(document);

    }
    
    
    // BAN
    
    
    public void ban(CommandSender sender, String reason , long time) {
        long ende = 0;
        if(time == -1) {
            ende = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = time * 1000;
            ende = current + millis;
        }
        setObject("BanReason", reason);
        setObject("BanTime", ende);
        setObject("BanBy", sender.getName());
        
        
    }
    
    
    public void unban() {
        setObject("BanReason", "");
        setObject("BanTime", (long)0);
        setObject("BanBy", "");        
    }
    
    
    public String getBanReason() {
        return (String) getObject("BanReason");
    }
    
    public long getBanTime() {
        return (long) getObject("BanTime");
    } 
    
    public String getBanBy() {
        return (String)getObject("BanBy");
    }
    
    
    public int getBanPoins() {
        return (int) getObject("BanPoins");
    }
    
    
    public void setBanPoins(int poins) {
        setObject("BanPoins", poins);
    }
    
    
    public boolean isBanned() {
        if(getBanTime() == 0) {
            return false;
        } else {
            return true;
        }





    }
    
    
    //  MUTE
    
    
    public void mute(CommandSender sender, String reason, long time) {
        long ende = 0;
        if (time == -1) {
            ende = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = time * 1000;
            ende = current + millis;
        }        
        setObject("MuteReason", reason);
        setObject("MuteTime", ende);
        setObject("MuteBy", sender.getName());
    }
    
    
    public void unmute() {
        setObject("MuteReason", "");
        setObject("MuteTime", 0);
        setObject("MuteBy", "");        
    }
    
    
    
    public String getMuteReason() {
        return (String) getObject("MuteReason");
    }
    
    public long getMuteTime() {
        return (long) getObject("MuteTime");
    } 
    
    public String getMuteBy() {
        return (String)getObject("MuteBy");
    }
    
    public int getMutePoins() {
        return (int) getObject("MutePoins");
    }
    
    public void setMutePoins(int poins) {
        setObject("MutePoins", poins);
    }
    
    public boolean isMuted() {
        if(getMuteReason().equalsIgnoreCase(null)) {
            return false;
        }
        return true;
    }



    

    public UUID getUuid() {
        return uuid;
    }
    
    public void setrang(String rang) {
        setObject("RangTime",-1);
        setObject("Rang", rang);
        setObject("isTempRang",false);
    }  
    
    public void setTempRang(String rang,long end) {
        long ende = 0;
        if (end == -1) {
            ende = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = end * 1000;
            ende = current + millis;
        }
        setObject("RangTime",ende);
        setObject("Rang", rang);
        setObject("isTempRang",true);
    } 
    
    public boolean getIsTempRang() {
        return (boolean)getObject("isTempRang");
    }

    public boolean isTempRang() {
        if(getIsTempRang()) {
            return true;
        }
        return false;
    }    
    
    
    public String getRang() {
        return (String) getObject("Rang");
    }
    
    public Long getRangTime() {
        return (Long)getObject("RangTime");
    }

    public boolean isInGroup(String rang) {
        return getRang().contains(rang);
    }
    
    public void setCoins(int coins) {
        setObject("Coins", coins);

    }
   
    public int getCoins() {
        return (int) getObject("Coins");
    }
    
    
    public void addCoins(int coins) {
        setCoins(getCoins()+coins);
    }
    
    
    public String getNickname() {
        return (String)getObject("Nickname");
    }
    
    
    public boolean isNick() {
        if(getNickname() == null) {
            return false;
        }
        return true;
    }
    
    
    public void setName(String name) {
        setObject("name",name);

    }

    public String getName() {
        return (String)getObject("name");
    }
    
    
    public String getRangTimeReamning(UUID uuid) {
        MongoPlayer mongoBan = MongoPlayer.getPlayer(this.plugin,uuid,ProxyServer.getInstance().getPlayer(uuid).getName());
        long current = System.currentTimeMillis();
        long end = mongoBan.getRangTime();
        long millis = end - current;
        if (end == -1) {
            return "§4Permanent";
        }


        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        while (millis > 1000) {
            millis -= 1000;
            seconds++;
        }
        while (seconds > 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }
        while (hours > 24) {
            hours -= 24;
            days++;
        }

        while (days > 7) {
            days -= 7;
            weeks++;

        }

        if (weeks == 0) {
            return "§6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n) ";
        } else if (days == 0) {
            return "§6" + hours + " §eStunde(n) §e" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (hours == 0) {
            return "§6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (minutes == 0) {
            return "§6" + seconds + " §eSekunde(n)";


        } else {
            return "§6" + weeks + " §eWoche(n) §6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        }


    }
    
    
    public String getBanTimeReamning(UUID uuid) {
        MongoPlayer mongoBan = MongoPlayer.getPlayer(this.plugin,uuid,ProxyServer.getInstance().getPlayer(uuid).getName());
        long current = System.currentTimeMillis();
        long end = mongoBan.getBanTime();
        long millis = end - current;
        if (end == -1) {
            return "§4Permanent";
        }


        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        while (millis > 1000) {
            millis -= 1000;
            seconds++;
        }
        while (seconds > 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }

        while (hours > 24) {
            hours -= 24;
            days++;
        }

        while (days > 7) {
            days -= 7;
            weeks++;

        }

        if (weeks == 0) {
            return "§6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n) ";
        } else if (days == 0) {
            return "§6" + hours + " §eStunde(n) §e" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (hours == 0) {
            return "§6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (minutes == 0) {
            return "§6" + seconds + " §eSekunde(n)";


        } else {
            return "§6" + weeks + " §eWoche(n) §6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        }


    }
    
    
    public String getMuteTimeReamning(UUID uuid) {
        MongoPlayer mongoBan = MongoPlayer.getPlayer(this.plugin,uuid,ProxyServer.getInstance().getPlayer(uuid).getName());
        long current = System.currentTimeMillis();
        long end = mongoBan.getMuteTime();
        long millis = end - current;
        if (end == -1) {
            return "§4Permanent";
        }


        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        while (millis > 1000) {
            millis -= 1000;
            seconds++;
        }
        while (seconds > 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }

        while (hours > 24) {
            hours -= 24;
            days++;
        }

        while (days > 7) {
            days -= 7;
            weeks++;

        }

        if (weeks == 0) {
            return "§6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n) ";
        } else if (days == 0) {
            return "§6" + hours + " §eStunde(n) §e" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (hours == 0) {
            return "§6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        } else if (minutes == 0) {
            return "§6" + seconds + " §eSekunde(n)";


        } else {
            return "§6" + weeks + " §eWoche(n) §6" + days + " §eTag(e) §6" + hours + " §eStunde(n) §6" + minutes + " §eMinute(n) §6" + seconds + " §eSekunde(n)";
        }


    }




}