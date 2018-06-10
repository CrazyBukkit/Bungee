/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.crazybukkit.bungee.Bungee;

/**
 *
 * @author tebbh
 */
public class WordsManager {
    
    
    
    private Bungee plugin;

    public List<String> blocked = Arrays.asList("Hurensohn","Bastard","Arschloch","Hure","Fotze");
    
    public WordsManager(Bungee plugin) {
        this.plugin = plugin;
        //load();
    }
    
    
    /*public void load() {
        blocked.add("Hurensohn");
        blocked.add("Bastard");
        blocked.add("Arschloch");
        blocked.add("Hure");
        blocked.add("Arsch");
        blocked.add("Fotze");
        blocked.add("nutte");
        blocked.add("nuttensohn");
        blocked.add("Bitch");
        blocked.add("Ficker");
        blocked.add("Fickdich");
        blocked.add("Kacke");
        blocked.add("wixxa");
        blocked.add("wixxer");
        blocked.add("faggot");
        blocked.add("schwanz");
        blocked.add("Penis");
        blocked.add("Sperma");
        blocked.add("jude");
        blocked.add("Jundensohn");
        blocked.add("GlassKammer");
        blocked.add("GlasKammer");
        blocked.add("GlasKamer");
        blocked.add("Hitler");
        blocked.add("Hitlersohn");
        blocked.add("fresse");
        blocked.add("Heil");
        blocked.add("Siegheil");
        blocked.add("Spast");
    }*/
    
    
    
}
