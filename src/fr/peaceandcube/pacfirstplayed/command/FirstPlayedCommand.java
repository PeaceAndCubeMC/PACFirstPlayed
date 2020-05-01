package fr.peaceandcube.pacfirstplayed.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FirstPlayedCommand implements CommandExecutor, TabExecutor {
    public Player player;
    public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACFirstPlayed").getConfig();
    public String date_format = config.getString("date_format");
    public String message_me = config.getString("message_me");
    public String message_other = config.getString("message_other");
    public String error_not_found = config.getString("error_not_found");
    public boolean offline_players = config.getBoolean("offline_players");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            if (!sender.hasPermission("firstplayed.me")) return false;
            player = (Player) sender;
            sendOwnDate(sender, player);
            return true;
        } else if (args.length == 1) {
            if (!sender.hasPermission("firstplayed.other")) return false;
            String target = args[0];
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))) {
                sendOtherDate(sender, Bukkit.getPlayer(target));
                return true;
            } else if (offline_players) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                if (offlinePlayer.hasPlayedBefore()) {
                    sendOtherDate(sender, Bukkit.getOfflinePlayer(target));
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + error_not_found);
            return true;
        }
        return false;
    }

    public void sendOwnDate(CommandSender sender, OfflinePlayer target) {
        long timestamp = target.getFirstPlayed();
        String date = new SimpleDateFormat(date_format).format(new Date(timestamp));
        sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + message_me, date));
    }

    public void sendOtherDate(CommandSender sender, OfflinePlayer target) {
        long timestamp = target.getFirstPlayed();
        String date = new SimpleDateFormat(date_format).format(new Date(timestamp));
        sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + message_other, target.getName(), date));
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
