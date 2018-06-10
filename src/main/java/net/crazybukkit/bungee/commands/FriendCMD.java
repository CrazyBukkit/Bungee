/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoFriends;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class FriendCMD extends Command{
    
        
        private Bungee plugin;
  
    
        public FriendCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
            }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend add §e[Spieler]"));
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend remove §e[Spieler]"));//
               // p.sendMessage(new TextComponent(Main.m.getPrefix() + "§7/friend list"));
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend jump §e[Spieler]"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend clear"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend requests"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend accept §e[Spieler]"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend deny §e[Spieler]"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend acceptall"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend denyall"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend toggleadds"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend togglemessage"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/friend togglejump"));//
                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7/msg §e[Spieler]"));//
            } else if (args.length == 2) {
                String name = args[1];
                MongoFriends mf2 = MongoFriends.getPlayer(this.plugin, this.plugin.uuidFetcher.getUUID(p.getName()), p.getName());
                MongoFriends mf = MongoFriends.getPlayer(this.plugin, this.plugin.uuidFetcher.getUUID(name), name);
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(name);
                if (mf == null) {
                    p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDieser Spieler war noch nie auf diesem Server"));
                    return;
                }
                
                if(pp == sender) {
                    p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu kannst dich nicht selbst adden!"));
                    return;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (mf.getInvites()) {
                        if (!mf.isFriend(p.getName())) {
                            if (!mf.isInvited(p.getName())) {
                                mf.addInvite(p.getName());
                                if (mf2.isInvited(name) && mf.isInvited(p.getName())) {
                                    mf.addFriend(p.getName());
                                    p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du bist nun mit §e" + name + " §7befreundet"));
                                    if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                                        pp.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du bist nun mit §e" + p.getName() + " §7befreundet"));
                                    }
                                } else {
                                    p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du hast eine Freundschaftsanfrage an §e" + name + " §7gesendet"));
                                    if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                                        pp.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du hast eine Freundschaftsanfrage von §e" + p.getName() + " §7erhalten"));
                                        TextComponent tc = new TextComponent(this.plugin.getFriendprefix()+"§aAnnehmen");
                                        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + p.getName()));
                                        tc.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§7Nehme die Freundschaftsanfrage von §e" + p.getName() + " §7an").create()));
                                        pp.sendMessage(tc);
                                        TextComponent tc1 = new TextComponent(this.plugin.getFriendprefix()+"§cAblehnen");
                                        tc1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + p.getName()));
                                        tc1.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§7Lehne die Freundschaftsanfrage von §e" + p.getName() + " §7ab").create()));
                                        pp.sendMessage(tc1);
                                    }
                                }
                            } else {
                                p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast bereits §e" + name + " §ceine Freundschaftsanfrage geschickt"));
                            }
                        } else {
                            p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu bist bereits mit §e" + name + " §cbefreundet"));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getPrefix() + "§e" + name + " §chat die Freundschaftsanfragen ausgestellt"));
                    }
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (!mf.isFriend(p.getName())) {
                        if (mf2.isInvited(name)) {
                            mf2.addFriend(name);
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du bist nun mit §e" + name + " §7befreundet"));
                            if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                                pp.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du bist nun mit §e" + p.getName() + " §7befreundet"));
                            }
                        } else {
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu hast keine Freundschaftsanfrage von §e" + name + " §cerhalten"));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu bist bereits mit §e" + name + " §cbefreundet"));
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (!mf.isFriend(p.getName())) {
                        if (mf2.isInvited(name)) {
                            mf2.removeInvite(name);
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du hast die Freundschaftsanfrage von §e" + name + " §7abgelehnt"));
                            if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                                pp.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§e" + p.getName() + " §7hat die Freundschaftsanfrage abgelehnt"));
                            }
                        } else {
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu hast keine Freundschaftsanfrage von §e" + name + " §cerhalten"));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu bist bereits mit §e" + name + " §cbefreundet"));
                    }
                } else if (args[0].equalsIgnoreCase("jump")) {
                    if (mf.isFriend(p.getName())) {
                        if (mf.getJump()) {
                            if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                                p.connect(ProxyServer.getInstance().getPlayer(name).getServer().getInfo());
                                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du bist §e" + name + " §7hinterher gesprungen auf dem Server §6" +
                                        ProxyServer.getInstance().getPlayer(name).getServer().getInfo().getName()));
                            } else {
                                p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§e" + name + " §cist gerade nicht Online"));
                            }
                        } else {
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu kannst nicht §e" + name + " §cnachspringen"));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu bist nicht mit §e" + name + " §cbefreundet"));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (mf.isFriend(p.getName())) {
                        mf.removeFriend(p.getName());
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§7Du hast die Freundschaft mit §e" + name + " §7beendet"));
                        if (ProxyServer.getInstance().getPlayer(name).isConnected()) {
                            pp.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§e" + p.getName() + " §7hat die Freundschaft beendet"));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix() + "§cDu bist nicht mit §e" + name + " §cbefreundet"));
                    }
                }
            } else if (args.length == 1) {
                MongoFriends mf2 = MongoFriends.getPlayer(this.plugin, this.plugin.uuidFetcher.getUUID(p.getName()), p.getName());
                if (args[0].equalsIgnoreCase("clear")) {
                    if (mf2.Friends.size() > 0) {
                        for (String d : mf2.Friends) {
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast §e"+d+" §7aus deiner Freundesliste entfernt"));
                            this.plugin.runnableManager.async(() -> {
                                mf2.removeFriend(d);
                            });
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§cDu hast keine Freunde in deiner Freundesliste"));
                    }
                } else  if (args[0].equalsIgnoreCase("requests")) {
                    if (mf2.Invites.size() > 0) {
                        if(mf2.Invites.size() > 10){
                            for(int i = 0; i> 10; i++)
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§8? §e"+mf2.Invites.get(i)));
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7... und §e"+(mf2.Invites.size() - 10)+" weitere §7Anfragen"));
                        } else {
                            for(int i = 0; i> mf2.Invites.size(); i++)
                                p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§8? §e"+mf2.Invites.get(i)));
                        }
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§cDu hast keine Freundesanfragen"));
                    }
                } else  if (args[0].equalsIgnoreCase("acceptall")) {
                    if (mf2.Invites.size() > 0) {
                        for (String d : mf2.Invites) {
                            p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast §ealle §7deine Freundschaftsanfragen angenommen"));
                            this.plugin.runnableManager.async(() -> {
                                mf2.addFriend(d);
                            });
                        }
                            
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§cDu hast keine Freundesanfragen"));
                    }
                } else  if (args[0].equalsIgnoreCase("denyall")) {
                    if (mf2.Invites.size() > 0) {
                        mf2.Invites.clear();
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast §ealle §7deine Freundschaftsanfragen abgelehnt"));
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§cDu hast keine Freundesanfragen"));
                    }
                }else  if (args[0].equalsIgnoreCase("toggleadds")) {
                    if(mf2.getInvites()){
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast die §eFreundschaftsanfragen §cdeaktivert"));
                        this.plugin.runnableManager.async(() -> {
                            mf2.setInvites(false);
                        });
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast die §eFreundschaftsanfragen §aaktivert"));
                        this.plugin.runnableManager.async(() -> {
                            mf2.setInvites(true);
                        });
                    }
                }else  if (args[0].equalsIgnoreCase("togglemessage")) {
                    if(mf2.getMessages()){
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast die §eprivaten Nachrichten §cdeaktivert"));
                        this.plugin.runnableManager.async(() -> {
                            mf2.setMessage(false);
                        });
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast die §eprivaten Nachrichten §aaktivert"));
                        this.plugin.runnableManager.async(() -> {
                            mf2.setMessage(true);
                        });
                    }
                }else  if (args[0].equalsIgnoreCase("togglejump")) {
                    if(mf2.getJump()){
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast das §eNachspringen §cdeaktivert"));
                        this.plugin.runnableManager.async(() -> {
                           mf2.setJump(false);
                        });                        
                    } else {
                        p.sendMessage(new TextComponent(this.plugin.getFriendprefix()+"§7Du hast das §eNachspringen §aaktiviert"));
                        this.plugin.runnableManager.async(() -> {
                            mf2.setJump(true);
                        });
                    }
                }
            }
        }
    }

}
