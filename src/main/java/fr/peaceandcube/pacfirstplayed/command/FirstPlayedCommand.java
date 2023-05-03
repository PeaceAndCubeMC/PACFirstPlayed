package fr.peaceandcube.pacfirstplayed.command;

import fr.peaceandcube.pacfirstplayed.util.Config;
import fr.peaceandcube.pacfirstplayed.util.PlayerMessages;
import fr.peaceandcube.pacfirstplayed.util.SuggestionProviders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FirstPlayedCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_FIRSTPLAYED_ME = "peaceandcube.firstplayed.me";
    private static final String PERM_FIRSTPLAYED_OTHER = "peaceandcube.firstplayed.other";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 && sender instanceof Player player) {
            if (sender.hasPermission(PERM_FIRSTPLAYED_ME)) {
                sendOwnDate(sender, player);
                return true;
            }
        } else if (args.length == 1) {
            if (sender.hasPermission(PERM_FIRSTPLAYED_OTHER)) {
                String target = args[0];
                if (Bukkit.getPlayer(target) != null && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))) {
                    sendOtherDate(sender, Bukkit.getPlayer(target));
                    return true;
                } else if (Config.offline_players) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                    if (offlinePlayer.hasPlayedBefore()) {
                        sendOtherDate(sender, Bukkit.getOfflinePlayer(target));
                        return true;
                    }
                }
                sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                return true;
            }
        }
        return false;
    }

    public void sendOwnDate(CommandSender sender, OfflinePlayer target) {
        long timestamp = target.getFirstPlayed();
        String date = new SimpleDateFormat(Config.date_format).format(new Date(timestamp));
        String message = String.format(Config.message_me, date);
        sender.sendMessage(Component.text(message, TextColor.color(0xFF55FF)));
    }

    public void sendOtherDate(CommandSender sender, OfflinePlayer target) {
        long timestamp = target.getFirstPlayed();
        String date = new SimpleDateFormat(Config.date_format).format(new Date(timestamp));
        String message = String.format(Config.message_other, target.getName(), date);
        sender.sendMessage(Component.text(message, TextColor.color(0xFF55FF)));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_FIRSTPLAYED_OTHER)) {
            if (args.length == 1) {
                return SuggestionProviders.getOnlinePlayers(args[0]);
            }
        }
        return List.of();
    }
}
