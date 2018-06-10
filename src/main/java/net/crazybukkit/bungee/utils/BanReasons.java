/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.utils;

/**
 *
 * @author Tebbe
 */
public enum BanReasons {
    
    
    Hacking("Hacking",2592000,"30 Tage"),
    Spamming("Spamming",21600,"6 Stunden"),
    Werbung("Werbung",43200,"12 Stunden"),
    Verhalten("Verhalten",21600,"6 Stunden"),
    Bannumgehung("Bannumgehung",-1,"Permanent"),
    Provokation("Provokation",86400,"Einen Tag"),
    Username("Username",2592000,"30 Tage"),
    Skin("Skin",3600,"Eine Stunde"),
    Reportausnutzung("Reportausnutzung",86400,"Einen Tag"),
    Accountlist("Accountlist",-1,"Permanent");
    
    
    String name;
    long länge;
    String längeAsString;

    BanReasons(String name, long länge, String längeAsString) {
        this.name = name;
        this.länge = länge;
        this.längeAsString = längeAsString;
    }

    public String getName() {
        return name;
    }

    public long getLänge() {
        return länge;
    }

    public String getLängeAsString() {
        return längeAsString;
    }

    public static BanReasons getAllReasons() {
        for (BanReasons reasons : BanReasons.values()) {
            return reasons;
        }
        return null;
    }

    
    public static BanReasons getUnit(String unit) {
        for(BanReasons units : BanReasons.values()) {
            if(units.getName().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }

        return null;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
