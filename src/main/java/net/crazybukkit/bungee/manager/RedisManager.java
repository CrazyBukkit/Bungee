package net.crazybukkit.bungee.manager;

import net.crazybukkit.bungee.Bungee;
import redis.clients.jedis.Jedis;

public class RedisManager {

    public Jedis jedis;

    private final Bungee plugin;

    public RedisManager(Bungee plugin) {
        this.plugin = plugin;
    }


    public void initRedis(String name,int port,String password) {
        jedis = new Jedis(name,port);
        jedis.auth(password);
        jedis.select(0);
    }
}
