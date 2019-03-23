package com.yanis48.firstplayed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandFirstPlayed implements CommandExecutor, TabExecutor {
    public Player player;
    public FileConfiguration config = Bukkit.getPluginManager().getPlugin("FirstPlayed").getConfig();
    public String date_format = config.getString("date_format");
    public String message = config.getString("message");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("firstplayed.me")) return false;
            player = (Player) sender;
            sendDate(sender, player);
            return true;
        } else if (args.length == 1) {
            if (!sender.hasPermission("firstplayed.other")) return false;
            String target = args[0];
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))) {
                sendDate(sender, Bukkit.getPlayerExact(target));
                return true;
            }
        }
        return false;
    }

    public void sendDate(CommandSender sender, Player target) {
        if (sender instanceof Player) {
            long timestamp = target.getFirstPlayed();
            String date = new SimpleDateFormat(date_format).format(new Date(timestamp));
            sender.sendMessage(String.format("§d" + message, target.getDisplayName(), date));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && sender.hasPermission("firstplayed.other")) {
            List<String> players = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    players.add(player.getName());
                }
            }
            return players;
        }
        return new ArrayList<>();
    }
}
