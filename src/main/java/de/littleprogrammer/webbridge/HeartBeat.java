package de.littleprogrammer.webbridge;

import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class HeartBeat {

    private Main main = Main.getInstance();
    private JsonSetter jsonSetter = new JsonSetter();

    public HeartBeat() {
        ProxyServer.getInstance().getScheduler().schedule(main, () -> {
            jsonSetter.setJson();
        }, 5, 60, TimeUnit.SECONDS);
    }

}
