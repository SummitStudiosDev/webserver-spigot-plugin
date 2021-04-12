package me.summit.spigotwebserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.summit.spigotwebserver.server;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;
import java.util.ArrayList;

public class main extends JavaPlugin {
    // economy functions
    private static Economy econ = null;
    JavaPlugin jp = this;

    //plugin
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        server webserver = new server();
        int port = Integer.parseInt (this.getConfig().getString("port"));
        String result = webserver.starts(port, this);
        if(result.equalsIgnoreCase ("Success"))getLogger().info("Successfully started webserver on port "+this.getConfig().getString("port"));
        else getLogger().severe("Err starting webserver on port "+this.getConfig().getString("port")+": "+result);
		
    }
    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

}
