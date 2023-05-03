package fr.peaceandcube.pacfirstplayed.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class PlayerMessages {
    public static final TextComponent PLAYER_NOT_FOUND = error("Le joueur n'a pas été trouvé");

    public static TextComponent error(String msg) {
        return Component.text(msg, TextColor.color(0xFF5555));
    }
}
