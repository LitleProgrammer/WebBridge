package de.littleprogrammer.webbridge;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class JedisTerminal extends JedisPubSub {

    private String name;
    private ServiceUtils serviceUtils = new ServiceUtils();
    public JedisTerminal(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(String channel, String message) {

        if (channel.equals("test")){
            System.out.println("Got new message on channel test: " + message);
        }
        if (channel.equals("serviceStop")) {
            if (message != null) {
                System.out.println("Stopping service: " + message);
                serviceUtils.stopService(message);
            }
        }
        if (channel.equals("serviceStart")) {
            if (message != null) {
                serviceUtils.startService(message);
            }
        }
        if (channel.equals("serviceRestart")) {
            if (message != null) {
                serviceUtils.restartService(message);
            }
        }
        if (channel.equals("serviceAdd")) {
            if (message != null) {
                serviceUtils.addService(message);
            }
        }
        if (channel.equals("serviceKick")) {
            if (message != null) {
                serviceUtils.kickService(message);
            }
        }

    }

}
