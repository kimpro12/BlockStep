package me.blockstep;

import org.bukkit.ChatColor;

public class color {
    public static String transalate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
