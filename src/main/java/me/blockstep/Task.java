package me.blockstep;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Task implements Runnable{
    private JavaPlugin plugin;
    private Player p;
    public Task(JavaPlugin plugin, Player p) {
        this.p = p;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        ItemStack item = new ItemStack(block.getType());
        p.getInventory().addItem(item);
    }
}
