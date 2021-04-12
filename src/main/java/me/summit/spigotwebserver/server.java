package me.summit.spigotwebserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import static io.undertow.Handlers.path;
import io.undertow.Handlers;
import io.undertow.server.RoutingHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Deque;

import me.summit.spigotwebserver.main;

public class server {
    private static boolean validkey(ArrayList<String> validkeys, String givenkey){
        int i=0;
        while (i < validkeys.size()) {
            //System.out.println(keys.get(i));
            if(givenkey.equals(validkeys.get(i))){
                return true;
            }

            i++;
        }
        return false;
    }


    public static String starts(int port, JavaPlugin jp) {
        try {
            Undertow server =  Undertow.builder().addHttpListener(port, "0.0.0.0")
                    .setHandler(Handlers.routing()
                            .get("/api/online/num", exchange -> {
                                //key
                                Map<String, Deque<String>> qparams = exchange.getQueryParameters();
                                String key="NULL";
                                try {key = qparams.get("key").getFirst(); }
                                catch(Exception e){ exchange.getResponseSender().send("no key"); }
                                if(key.equals(""))exchange.getResponseSender().send("no key");
                                jp.reloadConfig(); //reload plugin to ensure that new keys in config.yml are added
                                ArrayList<String> keys = (ArrayList<String>) jp.getConfig().getStringList("apikeys");
                                if(!validkey(keys,key))exchange.getResponseSender().send("invalid key");

                                //action!
                                List<Player> pl = new ArrayList<>(jp.getServer().getOnlinePlayers());
                                exchange.getResponseSender().send( String.valueOf(pl.size()) );
                            })

                            .get("/test", exchange -> {
                                exchange.getResponseSender().send("recieved!");
                            })

                            //404
                            .setFallbackHandler(exchange -> {
                                exchange.setStatusCode(404);
                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                                exchange.getResponseSender().send("<center> <h1> 404 </h1> <hr> Page Not Found <hr> This is running spigotwebserver. Link https://github.com/SummitStudiosDev/webserver-spigot-plugin </center> ");
                            })

                    ).build();
            server.start();
            return "Success";
        }
        catch(Exception e) {
            return "Err: "+e;
        }

    }

}
