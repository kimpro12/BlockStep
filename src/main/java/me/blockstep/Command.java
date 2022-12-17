package me.blockstep;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
                        Sendmessage(sender);
                    }
                    Player target = Bukkit.getPlayer(args[0]);
                    if (args[0].equalsIgnoreCase("all")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            return CheckMap(p);
                        }
                    } else if (target != null) {
                        return CheckMap(target);
                    } else if (!args[0].equalsIgnoreCase("help")){
                        sender.sendMessage(color.transalate(t + " " + plugin.getConfig().getString("Message-When-Player-Don't-Exist")));
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("stop")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                             if (args[1].equalsIgnoreCase("all") && map.containsKey(p.getUniqueId())) {
                                 Bukkit.getScheduler().cancelTask(map.get(p.getUniqueId()));
                                 map.remove(p.getUniqueId());
                                 p.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Cannot-BlockStep")));
                                 return true;
                             }
                             Player target2 = Bukkit.getPlayer(args[1]);
                             if (target2 != null && map.containsKey(target2.getUniqueId()) && !(args[1].equalsIgnoreCase("all"))) {
                                 Bukkit.getScheduler().cancelTask(map.get(target2.getUniqueId()));
                                 map.remove(target2.getUniqueId());
                                 target2.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Cannot-BlockStep")));
                                 return true;
                             } else if (target2 == null && !(args[1].equalsIgnoreCase("all"))) {
                                 sender.sendMessage(color.transalate(t + " " + plugin.getConfig().getString("Message-When-Player-Don't-Exist")));
                                 return true;
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

    private boolean CheckMap(Player p) {
        if (map.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(map.get(p.getUniqueId()));
            map.remove(p.getUniqueId());
        }
        p.sendMessage(color.transalate(plugin.getConfig().getString("When-SomeOne-Can-BlockStep")));
        Dotask(p);
        return true;
    }

    public void Dotask(Player p) {
        map.put(p.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, new Task(plugin, p), 0, plugin.getConfig().getInt("Second") * 20L).getTaskId());
    }

    private void Sendmessage(CommandSender sender) {
        sender.sendMessage("---------------------BlockStep--------------------");
        sender.sendMessage("/blockstep all to make all player can blockstep");
        sender.sendMessage("/blockstep <player> to make <player> can blockstep");
        sender.sendMessage("/blockstep stop all player can blockstep");
        sender.sendMessage("/blockstep stop <player> to stop <player> can blockstep");
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
