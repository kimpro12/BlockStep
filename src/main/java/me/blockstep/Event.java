package me.blockstep;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Event {
    private JavaPlugin plugin;
    public Event(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                    ItemStack item = new ItemStack(block.getType());
                    p.getInventory().addItem(item);
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
