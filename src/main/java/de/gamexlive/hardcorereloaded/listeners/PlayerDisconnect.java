package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if(Bukkit.getServer().getOnlinePlayers().size() < 1) {
            Bukkit.getServer().getScheduler().cancelTask(HardcoreReloaded.eventManager.ID);
            HardcoreReloaded.eventManager.ID = 0;

            Bukkit.getServer().getScheduler().cancelTask(HardcoreReloaded.graveyard.ID);
            HardcoreReloaded.graveyard.ID = 0;

        }
    }
}
