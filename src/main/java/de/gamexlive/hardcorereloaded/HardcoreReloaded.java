package de.gamexlive.hardcorereloaded;

import de.gamexlive.hardcorereloaded.death.Graveyard;
import de.gamexlive.hardcorereloaded.listeners.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardcoreReloaded extends JavaPlugin {

    public static HardcoreReloaded instance;
    public static Graveyard graveyard;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        System.out.println(getConfig().getInt("graveyard.X"));
        System.out.println(getConfig().getInt("graveyard.Y"));
        System.out.println(getConfig().getInt("graveyard.Z"));
        instance = this;
        graveyard = new Graveyard();

        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
