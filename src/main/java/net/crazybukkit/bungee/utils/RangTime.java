/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tebbe
 */
public enum RangTime {
    
    SECOND("Sekunde(n)", 1, "sec"),
    MINUTE("Minute(n)", 60, "min"),
    HOUR("Stunde(n)", 60*60, "hour"),
    DAY("Tag(e)", 24*60*60, "day"),
    WEEK("Woche(n)", 7*24*60*60, "week");

    private String name;
    private int toSecond;
    private String shortcut;


    private RangTime(String name, int toSecond, String shortcut) {
        this.name = name;
        this.toSecond = toSecond;
        this.shortcut = shortcut;


    }

    public int getToSecond() {
        return toSecond;
    }


    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static List<String> getUnitAsString() {
        List<String> units = new ArrayList<String>();
        for(RangTime unit : RangTime.values()) {
            units.add(unit.getShortcut());
            units.add(unit.getShortcut().toLowerCase());

        }

        return units;
    }

    public static RangTime getUnit(String unit) {
        for(RangTime units : RangTime.values()) {
            if(units.getShortcut().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }

        return null;

    }

}
