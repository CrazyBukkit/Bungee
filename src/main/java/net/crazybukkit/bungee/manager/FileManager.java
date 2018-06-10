/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 *
 * @author tebbh
 */
public class FileManager {
    
 private Bungee plugin;
 
 
    public String motd = "Test";
    public int slots = 50;
    public boolean wartung = false;
    public boolean globalmute = false;
    public ArrayList<String> reports = new ArrayList<>();

    public FileManager(Bungee plugin) {
        this.plugin = plugin;
    }
    
     public void loadConfig() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }
        File file = new File(this.plugin.getDataFolder().getPath(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {

                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                this.motd = config.getString("CrazySystem.motd");
                this.slots = config.getInt("CrazySystem.slots");
                if(config.getBoolean("CrazySystem.wartung")) {
                    ProxyServer.getInstance().getConsole().sendMessage(this.plugin.getPrefix()+"§7Der Wartungsmodus ist §aaktiviert");
                    this.wartung = true;
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(this.plugin.getPrefix()+"§7Der Wartungsmodus ist §cdeaktiviert");
                    this.wartung = false;
                }

    } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setWartung(boolean b) {
        try {
            File file = new File(this.plugin.getDataFolder().getPath(), "config.yml");
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            config.set("CrazySystem.wartung",b);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setMOTD(String motd) {
        try {
            File file = new File(this.plugin.getDataFolder().getPath(), "config.yml");
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            config.set("CrazySystem.motd",motd.replace("&","§"));
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSlots(int slots) {
        try {
            File file = new File(this.plugin.getDataFolder().getPath(), "config.yml");
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            config.set("CrazySystem.slots",slots);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMotd() {
        return motd;
    }

    public Bungee getPlugin() {
        return plugin;
    }

    public int getSlots() {
        return slots;
    }

    public boolean isWartung() {
        return wartung;
    }

    public boolean isGlobalmute() {
        return globalmute;
    }
    
    
    
    
    
    
    

        

    
}

