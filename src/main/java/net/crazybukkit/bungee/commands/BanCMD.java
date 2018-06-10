/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.utils.BanReasons;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Tebbe
 */
public class BanCMD extends Command{
    
    private Bungee plugin;

    public BanCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
        
    }

  @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
                if (this.plugin.permissionsManager.getBan(p)) {
                    if (args.length == 2) {
                if(plugin.permissionsManager.getTeamDatabase(this.plugin.uuidFetcher.getUUID(args[0]), args[0])) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDu darfst keine Teamitglieder bannen!"));
                    return;
                }
                CrazyPlayer cp = new CrazyPlayer(args[0]);
                if(cp.isBanned()) {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cDer Spieler ist bereits gebannt"));
                    return;
                }
                if(BanReasons.getAllReasons().getName().equals(args[1])) {
                    BanReasons reasons = BanReasons.getUnit(args[1]);
                    if(isOnline(args)) {
                        ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                        cp.ban(sender,reasons.getName(), reasons.getLänge());
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast den Spieler §c" + args[0] + " §7für §e"+reasons.getLängeAsString()+" §7vom Netzwerk gebannt\n" + "§7Grund: §b"+reasons.getName()));
                        plugin.getDiscordBotManager().sendMessage(sender.getName()+" hat den Spieler "+args[0]+"§7 vom Netzwerk gebannt! \n" +
                                "§7Grund §8» §e"+reasons.getName() + "\n" +
                                "§7Verbleibende Zeit §8» "+reasons.getLängeAsString());
                        plugin.permissionsManager.sendToTeam("§cBanManager §8» §7"+"\n"+"§7Name §8» §c"+args[1]+ "\n"+
                                "§7By §8» §a"+sender.getName() + "\n"+
                                "§7Grund §8» §e"+reasons.getName() + "\n" +
                                "§7Verbleibende Zeit §8» "+reasons.getLängeAsString());
                        this.plugin.runnableManager.schedule(() -> {
                            pp.disconnect(new TextComponent("§cDu wurdest vom MCSpookyDE Netzwerk gebannt!\n"+"\n" + "§cGrund: §b" + reasons.getName() + "\n"+"\n" + "§cVerbleibenezeit: " + cp.getBanEndTime()));
                        }, 500);
                    } else {
                        cp.ban(sender,reasons.getName(), reasons.getLänge());
                        sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§7Du hast den Spieler §c" + args[0] + " §7für §e"+reasons.getLängeAsString()+" §7vom Netzwerk gebannt\n" + "§7Grund: §b"+reasons.getName()));
                        plugin.permissionsManager.sendToTeam("§cBanManager §8» §7"+"\n"+"§7Name §8» §c"+args[1]+ "\n"+
                                "§7By §8» §a"+sender.getName() + "\n"+
                                "§7Grund §8» §e"+reasons.getName() + "\n" +
                                "§7Verbleibende Zeit §8» "+reasons.getLängeAsString());
                        plugin.getDiscordBotManager().sendMessage(sender.getName()+" hat den Spieler "+args[0]+"§7 vom Netzwerk gebannt! \n" +
                                "§7Grund §8» §e"+reasons.getName() + "\n" +
                                "§7Verbleibende Zeit §8» "+reasons.getLängeAsString());
                    }
                } else {
                    sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§cGründe: §e"+BanReasons.values()));
                }

            } else {
                sender.sendMessage(new TextComponent(this.plugin.getPrefix()+"§c/ban <name> <grund>"));

            }
        } else {
            sender.sendMessage(new TextComponent(this.plugin.getPrefix() + "§cDu hast keine Rechte für den Befehl"));
        }
    }

    public boolean isOnline(String[] args) {
        if(ProxyServer.getInstance().getPlayer(args[0]) != null) {
            ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
            return true;



        }
        return false;
    }


    }

