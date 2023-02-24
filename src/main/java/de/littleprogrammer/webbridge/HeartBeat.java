package de.littleprogrammer.webbridge;

import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class HeartBeat {

    private Main main = Main.getInstance();
    private JsonSetter jsonSetter;

    public HeartBeat() {
        ProxyServer.getInstance().getScheduler().schedule(main, () -> {
            main.getJedis().publish("heartbeat", "maxPlayerCount"); //also put jsonSetter i here but before so the last data gets set and the new gets read
        }, 1, 1, TimeUnit.MINUTES);
    }

}
