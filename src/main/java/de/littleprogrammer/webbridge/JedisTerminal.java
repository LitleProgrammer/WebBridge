package de.littleprogrammer.webbridge;

import redis.clients.jedis.JedisPubSub;

public class JedisTerminal extends JedisPubSub {

    private String name;
    public JedisTerminal(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(String channel, String message) {

        if (channel.equals("test")){
            System.out.println("Got new message on channel test: " + message);
        }

    }

}
