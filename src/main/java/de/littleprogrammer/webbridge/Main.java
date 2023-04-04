package de.littleprogrammer.webbridge;

import net.md_5.bungee.api.plugin.Plugin;
import redis.clients.jedis.JedisPooled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Main extends Plugin {

    private static Main instance;
    private JedisPooled jedis;
    private HeartBeat heartBeat;

    private JsonSetter jsonSetter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        heartBeat = new HeartBeat();



        //Jedis
        jedis = new JedisPooled("localhost", 6379);
        getProxy().getScheduler().schedule(this, () -> {

            try {
                jedis.set("ping", "why you here");
            }catch (Exception e) {
                e.printStackTrace();
            }

        }, 59, 57, TimeUnit.SECONDS);


        //Jedis Connect
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(() -> jedis.subscribe(new JedisTerminal("onlyOne"), "test", "serviceStop", "serviceStart", "serviceRestart", "serviceAdd", "serviceKick"));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        jsonSetter = new JsonSetter();
        jsonSetter.setEmpty();
    }

    public static Main getInstance() {
        return instance;
    }

    public JedisPooled getJedis() {
        return jedis;
    }
}
