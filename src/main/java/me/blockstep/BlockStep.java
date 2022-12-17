package me.blockstep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockStep extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("[Blockstep] Plugin made by Kim");
        Bukkit.getConsoleSender().sendMessage("[BlockStep] Plugin enabled");
        if (getConfig().getBoolean("Auto-BlockStep-When-Player-Join")) {
            new AutoBlockStep(this);
        }
        new Command(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[BlockStep] Plugin disabled");
    }
}
