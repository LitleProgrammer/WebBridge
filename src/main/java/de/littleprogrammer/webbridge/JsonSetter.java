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
    private String motd;
    private int onPlayers;
    private StringBuilder json = new StringBuilder();
    private int serverMax;

    public void setJson(){
        //Resets String builder
        json.setLength(0);

        //Resets Redis variable
        main.getJedis().jsonDel("servers");

        //Start building string
        json.append("[");

        //Setting servers to string builder
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            //Test if current server is fallback (all my homies hat fallback)
            if (!serverInfo.getName().equalsIgnoreCase("fallback")) {
                //Setting variables
                name = serverInfo.getName();
                motd = serverInfo.getMotd();
                onPlayers = serverInfo.getPlayers().size();
                serverMax = 32;
                //players = serverInfo.getPlayers().toArray();


                json.append("{\n" +
                        "    \"name\": " + '"' + name + '"' + ",\n" +
                        "    \"online\": true,\n" +
                        "    \"motd\": " + '"' + motd + '"' + ",\n" +
                        "    \"playersMax\": " + serverMax + ",\n" +
                        "    \"playersOnline\": " + onPlayers + "\n" +
                        "  },\n");
                }
            }


         //Setting proxy to string builder
         json.append("{\n" +
                 "    \"name\": " + '"' + ProxyServer.getInstance().getName() + '"' + ",\n" +
                 "    \"online\": true,\n" +
                 "    \"motd\": " + '"' + ProxyServer.getInstance().getConfigurationAdapter().getListeners(). iterator().next().getMotd() + '"' + ",\n" +
                 "    \"playersMax\": " + ProxyServer.getInstance().getConfigurationAdapter().getListeners().iterator().next().getMaxPlayers() + ",\n" +
                 "    \"playersOnline\": " + ProxyServer.getInstance().getPlayers().size()  + "\n" +
                 "  }");

         json.append("]");


            //Setting string builder to RedisJSON
            main.getJedis().jsonSet("servers", json.toString());
            }


            //Setting empty message on shutdown
            public void setEmpty() {
                main.getJedis().jsonSet("servers", "");
            }
}

