package com.yanis48.firstplayed;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlayed extends JavaPlugin {
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        this.getCommand("firstplayed").setExecutor(new CommandFirstPlayed());

        config.addDefault("date_format", "MM/dd/yyyy HH:mm:ss");
        config.addDefault("message", "%1s joined the server on %2s");
        config.options().copyDefaults(true);
        config.options().header("This is the configuration file for the FirstPlayed plugin.\nDate format uses letter codes, like M for month or s for seconds.\nIf you change the message, make sure to keep %1s and %2s, otherwise the plugin will not return anything.");
    saveConfig();
}

    @Override
    public void onDisable() {

    }
}
