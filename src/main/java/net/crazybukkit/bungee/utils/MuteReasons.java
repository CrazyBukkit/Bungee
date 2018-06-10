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
public enum  MuteReasons {

    Spamming("Spamming",21600,"6 Stunden"),
    Werbung("Werbung",43200,"12 Stunden"),
    Verhalten("Verhalten",21600,"6 Stunden"),
    Provokation("Provokation",86400,"Einen Tag");


    String name;
    long länge;
    String längeAsString;

    MuteReasons(String name, long länge, String längeAsString) {
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

    public static MuteReasons getAllReasons() {
        for (MuteReasons reasons : MuteReasons.values()) {
            return reasons;
        }
        return null;
    }


    public static MuteReasons getUnit(String unit) {
        for(MuteReasons units : MuteReasons.values()) {
            if(units.getName().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }

        return null;

    }













}

