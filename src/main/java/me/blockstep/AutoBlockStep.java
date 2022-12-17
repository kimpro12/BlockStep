package me.blockstep;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoBlockStep implements Listener {
    private JavaPlugin plugin;
    public AutoBlockStep(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void idk(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new Command(plugin).Dotask(p);
    }
}
