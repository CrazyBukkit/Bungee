/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.manager;

import net.crazybukkit.bungee.Bungee;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author tebbh
 */
public class DiscordBotManager {



    public JDA jda;

    private final Bungee plugin;

    public DiscordBotManager(Bungee plugin) {
        this.plugin = plugin;
    }


    public void init() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NONE").setAutoReconnect(true).setStatus(OnlineStatus.ONLINE).setGame(Game.playing("NaturFront.net")).buildBlocking();
        } catch (LoginException ex) {
            Logger.getLogger(DiscordBotManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscordBotManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    public void sendMessage(String message) {
       plugin.getRunnableManager().async(() -> {
           for (Guild g : jda.getGuilds()) {
               g.getTextChannelById("433302944511819776").sendMessage(message).queue();
           }
       });
    }

}
