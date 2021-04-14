package me.summit.spigotwebserver;

import me.summit.spigotwebserver.server;


public class jar_start {


    public static void main(final String[] args) {
        boolean canrun = false;
        if(!canrun) System.out.println("You may not directly run this jar!");
        else {
            server webserver = new server();
            int port = 80;
            //String result = webserver.starts(port);
        }
    }

}
