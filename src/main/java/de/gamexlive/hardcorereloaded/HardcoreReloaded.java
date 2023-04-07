package de.gamexlive.hardcorereloaded;

import de.gamexlive.hardcorereloaded.listeners.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardcoreReloaded
        extends JavaPlugin {
    public static HardcoreReloaded instance;

    public void onEnable() {
        this.saveDefaultConfig();
        System.out.println(this.getConfig().getInt("graveyard.X"));
        System.out.println(this.getConfig().getInt("graveyard.Y"));
        System.out.println(this.getConfig().getInt("graveyard.Z"));
        instance = this;
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerDeath(), (Plugin)this);
    }

    public void onDisable() {
    }
}