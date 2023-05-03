package fr.peaceandcube.pacfirstplayed;

import fr.peaceandcube.pacfirstplayed.command.FirstPlayedCommand;
import fr.peaceandcube.pacfirstplayed.command.FpDateCommand;
import fr.peaceandcube.pacfirstplayed.command.PACFirstPlayedCommand;
import fr.peaceandcube.pacfirstplayed.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PACFirstPlayed extends JavaPlugin {
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        config = this.getConfig();

        this.getCommand("firstplayed").setExecutor(new FirstPlayedCommand());
        this.getCommand("fpdate").setExecutor(new FpDateCommand());
        this.getCommand("pacfirstplayed").setExecutor(new PACFirstPlayedCommand());

        saveDefaultConfig();
        reloadConfig();
    }

    public void runTaskAsynchronously(final Runnable run) {
        this.getServer().getScheduler().runTaskAsynchronously(this, run);
    }

    public static void reload() {
        PACFirstPlayed instance = PACFirstPlayed.getPlugin(PACFirstPlayed.class);
        instance.reloadConfig();
        instance.saveDefaultConfig();
        config = instance.getConfig();
        config.options().copyDefaults(true);
        instance.saveConfig();
        Config.reload();
    }
}
