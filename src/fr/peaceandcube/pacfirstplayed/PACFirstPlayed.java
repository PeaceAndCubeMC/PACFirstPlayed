package fr.peaceandcube.pacfirstplayed;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.peaceandcube.pacfirstplayed.command.FirstPlayedCommand;
import fr.peaceandcube.pacfirstplayed.command.FpDateCommand;

public class PACFirstPlayed extends JavaPlugin {
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        this.getCommand("firstplayed").setExecutor(new FirstPlayedCommand());
        this.getCommand("fpdate").setExecutor(new FpDateCommand());

        saveDefaultConfig();
        reloadConfig();
}

    @Override
    public void onDisable() {

    }
}
