package fr.peaceandcube.pacfirstplayed.command;

import fr.peaceandcube.pacfirstplayed.PACFirstPlayed;
import fr.peaceandcube.pacfirstplayed.util.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FpDateCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_FPDATE = "peaceandcube.fpdate";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_FPDATE)) {
			if (args.length == 1) {

				PACFirstPlayed.getPlugin(PACFirstPlayed.class).runTaskAsynchronously(new Runnable() {

					@Override
					public void run() {
						StringBuilder playerNames = new StringBuilder();
						int playerCount = 0;

						for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
							long timestamp = player.getFirstPlayed();
							String joinDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(timestamp));

							if (joinDate.equals(args[0])) {
								playerNames.append(player.getName()).append(" ");
								playerCount++;
							}
						}

						if (playerNames.length() > 0) {
							String message = String.format(Config.playersOnDate, playerCount, playerNames);
							sender.sendMessage(Component.text(message, TextColor.color(0xFFFF55)));
						} else {
							sender.sendMessage(Component.text(Config.noneOnDate, TextColor.color(0xFF5555)));
						}
					}
				});

				return true;
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (sender.hasPermission(PERM_FPDATE)) {
			if (args.length == 1) {
				return List.of(getCurrentYearDay());
			}
		}
		return List.of();
	}

	public static String getCurrentYearDay() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(date);
	}
}
