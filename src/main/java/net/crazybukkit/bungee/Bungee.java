package net.crazybukkit.bungee;

import net.crazybukkit.bungee.commands.*;
import net.crazybukkit.bungee.database.MongoManager;
import net.crazybukkit.bungee.database.MongoPlayer;
import net.crazybukkit.bungee.listener.*;
import net.crazybukkit.bungee.manager.*;
import net.crazybukkit.bungee.utils.CrazyPlayer;
import net.crazybukkit.bungee.utils.UUIDFetcher;
import net.crazybukkit.bungee.utils.UUIDType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Bungee extends Plugin{


    public static Bungee plugin;
    public UUIDFetcher uuidFetcher;
    public UUIDType uuidType;
    public RunnableManager runnableManager;
    public CrazyPlayer crazyPlayer;
    public FileManager fileManager;
    public MongoManager mongoManager;
    public PermissionsManager permissionsManager;
    public WordsManager wordsManager;
    public DiscordBotManager discordBotManager;
    public ChatLogManager chatLogManager;


    public Map<UUID,String> cache = new HashMap<>();
    public HashMap<String,List<String>> permissions = new HashMap<>();


    public String prefix = "§7| §2NaturFront §8* ";
    public String reportprefix = "§7| §cReportSystem §8* ";
    public String teamchat = "§7| §cTeam §8* ";
    public String friendprefix = "§8│ §6Freunde §8» ";


    @Override
    public void onLoad() {
        plugin = this;
        uuidFetcher = new UUIDFetcher(this);
        uuidType = new UUIDType();
        runnableManager = new RunnableManager(this);
        crazyPlayer = new CrazyPlayer(this);
        fileManager = new FileManager(this);
        permissionsManager = new PermissionsManager(this);
        chatLogManager = new ChatLogManager(this);
        wordsManager = new WordsManager(this);
        discordBotManager = new DiscordBotManager(this);
        discordBotManager.init();
        mongoManager = new MongoManager("localhost", 27017);
        mongoManager.connect();

        super.onLoad();
    }

    @Override
    public void onEnable() {
        init();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    private void init() {
        getProxy().registerChannel("data");
        this.fileManager.loadConfig();
        initCommands();
        initListener();
    }


    private void initListener() {
        registerListener(new ChatListener(this));
        registerListener(new LoginListener(this));
        registerListener(new ProxyPingListener(this));
        registerListener(new ServerSwitchListener(this));
        registerListener(new PluginsMessageListener(this));
        registerListener(new PermissionsListener(this));
    }


    private void initCommands() {
        registerCommands(new ABanCMD(this, "aban"));
        registerCommands(new BanCMD(this, "ban"));
        registerCommands(new BroadcastCMD(this, "bc"));
        registerCommands(new ChatClearCMD(this, "chatclear"));
        registerCommands(new CoinsCMD(this, "coins"));
        registerCommands(new GlobalMuteCMD(this, "gmute"));
        registerCommands(new GotoCMD(this, "goto"));
        registerCommands(new HelpCMD(this, "help"));
        registerCommands(new KickCMD(this, "kick"));
        //registerCommands(new MuteCMD(this, "mute"));
        registerCommands(new FriendCMD(this, "friend"));
        registerCommands(new PingCMD(this, "ping"));
        registerCommands(new PlayerInfoCMD(this, "pinfo"));
        registerCommands(new RangBuyCMD(this, "premium"));
        registerCommands(new RangCMD(this, "rang"));
        registerCommands(new RangInfoCMD(this, "ranginfo"));
        registerCommands(new ReportAcceptCMD(this, "reporta"));
        registerCommands(new ReportCMD(this, "report"));
        registerCommands(new TeamChatCMD(this, "tc"));
        registerCommands(new TempRangCMD(this, "temprang"));
        registerCommands(new WartungCMD(this, "wartung"));
        registerCommands(new YoutubeCMD(this, "youtube"));
        registerCommands(new setMotdCMD(this, "setmotd"));
        registerCommands(new setSlotsCMD(this, "setslots"));
        registerCommands(new unbanCMD(this, "unban"));
        registerCommands(new unmuteCMD(this, "unmute"));
        registerCommands(new ReloadPermissions(this,"reloadperms"));
        registerCommands(new ChatLogCMD(this,"chatlog"));
        registerCommands(new PermissionsCMD(this,"group"));




    }


    private void registerListener(Listener listener) {
        this.getProxy().getPluginManager().registerListener(this, listener);


    }


    private void registerCommands(Command command) {
        this.getProxy().getPluginManager().registerCommand(this, command);
    }

    public void sendToServer(String channel, String message) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);
        try {
            dataOutputStream.writeUTF(channel);
            dataOutputStream.writeUTF(message);
        } catch(IOException e) {
            e.printStackTrace();
        }

        getProxy().getServers().values().stream().forEach((servers) -> {
            servers.sendData("data", arrayOutputStream.toByteArray());
        });
    }

    public static Bungee getPlugin() {
        return plugin;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public ProxyServer getProxy() {
        return super.getProxy();
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public Map<UUID, String> getCache() {
        return cache;
    }



    public MongoPlayer getPlayer(UUID uuid, ProxiedPlayer player) {
        return MongoPlayer.getPlayer(this, uuid, player.getName());
    }


    public DiscordBotManager getDiscordBotManager() {
        return discordBotManager;
    }




    public String getFriendprefix() {
        return friendprefix;
    }

    public UUID getUUID(String name){
        UUID uuid = plugin.uuidFetcher.getUUID(name);
        return uuid;

    }

    public RunnableManager getRunnableManager() {
        return runnableManager;
    }
}
