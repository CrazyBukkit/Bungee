/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.manager;

import java.util.concurrent.TimeUnit;

import net.crazybukkit.bungee.Bungee;
import net.md_5.bungee.api.ProxyServer;

/**
 *
 * @author tebbh
 */
public class RunnableManager {
    
     private Bungee plugin;

    public RunnableManager(Bungee plugin) {
        this.plugin = plugin;
    }
    
    public void async(Runnable runnable) {
        ProxyServer.getInstance().getScheduler().runAsync(this.plugin, () -> {
            runnable.run();
        });
    }
    
    public void schedule(Runnable runnable,int i2) {
        ProxyServer.getInstance().getScheduler().schedule(this.plugin, () -> {
            runnable.run();
        }, i2,TimeUnit.MILLISECONDS);
    }
    
}

