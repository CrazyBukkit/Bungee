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
public enum Rang {

    Sponsor("Sponsor","§2","§2§lSponsor ","§8┃ §7","00Sponsor"),

    Administrator("Admin","§4","§4§lAdmin ","§8┃ §7","00Admin"),
    
    Entwickler("Developer","§b","§bDeveloper ","§8┃ §b","01Developer"),
    
    Moderator("Moderator","§9","§9Moderator ","§8┃ §9","02Moderator"),
    
    Content("Content","§b","§bContent ","§8┃ §b","03Content"),
    
    Supporter("Supporter","§a","§aSupporter ","§8┃ §a","04Supporter"),
    
    Builder("Builder","§e","§eBuilder ","§8┃ §e","05Builder"),
    
    YouTube("YouTube","§5","§5YouTube ","§8┃ §5","06YouTube"),
    
    PremiumPlus("PremiumPlus","§3","§3Premium+ ","§8┃ §3","07PremiumPlus"),
    
    Platin("Platin","§f","§fPlatin ","§8┃ §f","08Platin"),
    
    Premium("Premium","§6","§6Premium ","§8┃ §6","09Premium"),
    
    Spieler("Spieler","§7","§7Spieler ","§8┃ §7","10Spieler");
    
    
    String name;
    String color;
    String prefix1;
    String prefix2;
    String group;

    private Rang(String name, String color, String prefix1, String prefix2, String group) {
        this.name = name;
        this.color = color;
        this.prefix1 = prefix1;
        this.prefix2 = prefix2;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getPrefix1() {
        return prefix1;
    }

    public String getPrefix2() {
        return prefix2;
    }

    public String getGroup() {
        return group;
    }
    
    
    
    
    
    
    
    public static Rang getUnit(String unit) {
        for(Rang units : Rang.values()) {
            if(units.getName().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }

        return null;

    }
    
   

    
    
}


