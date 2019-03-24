package com.yanis48.firstplayed;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlayed extends JavaPlugin {
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        this.getCommand("firstplayed").setExecutor(new CommandFirstPlayed());

        saveDefaultConfig();
        reloadConfig();
}

    @Override
    public void onDisable() {

    }
}
