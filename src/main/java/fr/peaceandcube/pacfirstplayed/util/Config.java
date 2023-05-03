package fr.peaceandcube.pacfirstplayed.util;

import fr.peaceandcube.pacfirstplayed.PACFirstPlayed;

public class Config {
    public static String date_format = PACFirstPlayed.config.getString("date_format");
    public static String message_me = PACFirstPlayed.config.getString("message_me");
    public static String message_other = PACFirstPlayed.config.getString("message_other");
    public static String playersOnDate = PACFirstPlayed.config.getString("players_on_date");
    public static String noneOnDate = PACFirstPlayed.config.getString("none_on_date");
    public static boolean offline_players = PACFirstPlayed.config.getBoolean("offline_players");

    public static void reload() {
        date_format = PACFirstPlayed.config.getString("date_format");
        message_me = PACFirstPlayed.config.getString("message_me");
        message_other = PACFirstPlayed.config.getString("message_other");
        playersOnDate = PACFirstPlayed.config.getString("players_on_date");
        noneOnDate = PACFirstPlayed.config.getString("none_on_date");
        offline_players = PACFirstPlayed.config.getBoolean("offline_players");
    }
}
