package de.littleprogrammer.webbridge;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import redis.clients.jedis.json.Path2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonSetter implements Listener {

    private Main main = Main.getInstance();

    private String name;
    private String uptime;
    private int onPlayers;
    private Object[] players;
    private StringBuilder json = new StringBuilder();
    private HashMap<String, String> maxPlayers = new HashMap<>();
    private int serverMax;

    private List<ServerInfo> servers = new ArrayList<>();


    public JsonSetter() {
        main.getJedis().jsonDel("servers");
        json.append("{");
        servers.clear();
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            servers.add(serverInfo);
        }

        for (int i = 0; i < servers.size(); i++){
            ServerInfo serverInfo = servers.get(i);
            name = serverInfo.getName();
            onPlayers = serverInfo.getPlayers().size();
            serverMax = Integer.parseInt(maxPlayers.get(name));
            players = serverInfo.getPlayers().toArray();

            if (i == servers.size() - 1){
                json.append("\"" + name + "\": {\n" +
                        "    \"online\": true,\n" +
                        "    \"playersMax\": " + serverMax + ",\n" +
                        "    \"playersOnline\": " + onPlayers + "\n" +
                        "  }");

            }else {
                json.append("\"" + name + "\": {\n" +
                        "    \"online\": true,\n" +
                        "    \"playersMax\": " + serverMax + ",\n" +
                        "    \"playersOnline\": " + onPlayers + "\n" +
                        "  },");
            }
        }
        json.append("}");

        main.getJedis().jsonSet("servers", json.toString());
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()){
            main.getJedis().jsonNumIncrBy("servers", Path2.of("$." + serverInfo.getName() + ".uptime"), 1);
        }
    }

    public void addMaxPlayers(String message){
        String[] parts = message.split(":", 2);
        String serverName = parts[0];
        String maxPlayerCount = parts[1];

        maxPlayers.put(serverName, maxPlayerCount);
        JsonSetter jsonSetter = new JsonSetter();
    }


}
