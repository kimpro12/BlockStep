package me.blockstep;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Command implements CommandExecutor, Listener {
    private final JavaPlugin plugin;
    private final HashMap<UUID, Integer> map = new HashMap<>();

    public Command(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("blockstep").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("blockstep")) {
            if (!(sender instanceof Player) || sender.hasPermission("blockstep")) {
                String t = plugin.getConfig().getString("Prefix");
                if (args.length == 0) {
                    sender.sendMessage(color.transalate(t + " " + "Plugin made by FIP"));
                    sender.sendMessage(color.transalate(t + " " + "/blockstep help to see how to use plugin"));
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        Sendmessage();
                    }
                    Player target = Bukkit.getPlayer(args[0]);
                    if (args[0].equalsIgnoreCase("all")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (map.containsKey(p.getUniqueId())) {
                                Bukkit.getScheduler().cancelTask(map.get(p.getUniqueId()));
                                map.remove(p.getUniqueId());
                            }
                            p.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Can-BlockStep")));
                            Dotask(p);
                        }
                    } else if (target != null) {
                        if (map.containsKey(target.getUniqueId())) {
                            Bukkit.getScheduler().cancelTask(map.get(target.getUniqueId()));
                            map.remove(target.getUniqueId());
                        }
                        target.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Can-BlockStep")));
                        Dotask(target);
                    } else if (!args[0].equalsIgnoreCase("help")){
                        sender.sendMessage(color.transalate(t + " " + plugin.getConfig().getString("Message-When-Player-Don't-Exist")));
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("stop")) {
                        Player target2 = Bukkit.getPlayer(args[1]);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (args[1].equalsIgnoreCase("all") && map.containsKey(p.getUniqueId())) {
                                Bukkit.getScheduler().cancelTask(map.get(p.getUniqueId()));
                                map.remove(p.getUniqueId());
                                p.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Cannot-BlockStep")));
                            } else if (target2 != null && map.containsKey(target2.getUniqueId())) {
                                Bukkit.getScheduler().cancelTask(map.get(target2.getUniqueId()));
                                map.remove(target2.getUniqueId());
                                target2.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Cannot-BlockStep")));
                            } else {
                                sender.sendMessage(color.transalate(t + " " + plugin.getConfig().getString("Message-When-Player-Don't-Exist")));
                            }
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(color.transalate(plugin.getConfig().getString("No-Permission")));
        }
        return true;
    }

    private void Dotask(Player p) {
        map.put(p.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                ItemStack item = new ItemStack(block.getType());
                p.getInventory().addItem(item);
            }
        }, 0, plugin.getConfig().getInt("Second") * 20L));
    }

    private void Sendmessage() {
        Bukkit.getConsoleSender().sendMessage("---------------------BlockStep--------------------");
        Bukkit.getConsoleSender().sendMessage("/blockstep all to make all player can blockstep");
        Bukkit.getConsoleSender().sendMessage("/blockstep <player> to make <player> can blockstep");
        Bukkit.getConsoleSender().sendMessage("/blockstep stop all player can blockstep");
        Bukkit.getConsoleSender().sendMessage("/blockstep stop <player> to stop <player> can blockstep");
    }

    @EventHandler
    public void out(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (map.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(map.get(p.getUniqueId()));
            map.remove(p.getUniqueId());
        }
    }
}
