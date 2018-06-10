package net.crazybukkit.bungee.commands;

import net.crazybukkit.bungee.Bungee;
import net.crazybukkit.bungee.database.MongoChatLog;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatLogCMD extends Command {

    private final Bungee plugin;

    public ChatLogCMD(Bungee plugin,String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if(args.length == 2) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
            if(args[0].equalsIgnoreCase("get")) {
                if(plugin.permissionsManager.getBan(p)) {
                    MongoChatLog cl = MongoChatLog.getPlayer(plugin,plugin.getUUID(target.getName()),target.getName());
                    p.sendMessage(new TextComponent(plugin.getPrefix()+"§7Der ChatLog von §c"+args[1]));
                    p.sendMessage(new TextComponent(plugin.getPrefix()+"§7ChatLogID §8» §c"+cl.getChatLogID()));
                    List<String> chatlog = new ArrayList<>();
                    if(plugin.chatLogManager.chatlog.containsKey(plugin.uuidFetcher.getUUID(target.getName()))) {
                        chatlog=plugin.chatLogManager.chatlog.get(plugin.uuidFetcher.getUUID(target.getName()));
                    }
                    if(plugin.chatLogManager.chatlog.size()>0)
                    {
                        p.sendMessage(new TextComponent("§7Chatlog von §c"+target.getName()));
                        for(String s : chatlog)
                        {
                            String[] ss = s.split(":");
                            String server = ss[0].toUpperCase(Locale.GERMANY);
                            String time = ss[1];
                            String m = "";
                            for(int i=2;i<ss.length;i++)
                            {
                                if(m.length()>0)m+=";";
                                m+=ss[i];
                            }
                            p.sendMessage(new TextComponent("§8[§e"+server+"§8]§8[§c"+time+"§8] §7"+m));
                        }
                    }else{
                        p.sendMessage(new TextComponent("§cDer Spieler hat noch nichts geschrieben!"));
                    }
                }
            }
        } else if(args.length == 1) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            List<String> chatlog = new ArrayList<>();
            if(plugin.chatLogManager.chatlog.containsKey(plugin.uuidFetcher.getUUID(target.getName()))) {
                chatlog=plugin.chatLogManager.chatlog.get(plugin.uuidFetcher.getUUID(target.getName()));
            }
            if(plugin.chatLogManager.chatlog.size()>0)
            {
                for(String s : chatlog)
                {
                    String[] ss = s.split(":");
                    String server = ss[0].toUpperCase(Locale.GERMANY);
                    String time = ss[1];
                    String m = "";
                    for(int i=2;i<ss.length;i++)
                    {
                        if(m.length()>0)m+=";";
                        m+=ss[i];
                    }
                    //p.sendMessage("§8[§e"+server+"§8]§8[§c"+time+"§8] §7"+m);
                    MongoChatLog mongoChatLog = MongoChatLog.getPlayer(plugin,plugin.uuidFetcher.getUUID(target.getName()),target.getName());
                    mongoChatLog.create(chatlog);
                    p.sendMessage(new TextComponent(plugin.getPrefix()+"§7Du hast einen ChatLog für§c "+target.getName()+"§7 erstellt!" ));
                }
            }else{
                p.sendMessage("§cDer Spieler hat noch nichts geschrieben!");
            }
        } else {
            p.sendMessage(new TextComponent(plugin.getPrefix()+"§7/chatlog <spielername>"));
        }

    }
}
