package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if(!HardcoreReloaded.cManager.contains(event.getPlayer().getUniqueId())) {
            HardcoreReloaded.cManager.addNewPlayer(event.getPlayer().getUniqueId());
        }
        System.out.println(HardcoreReloaded.eventManager.ID);
        if(HardcoreReloaded.eventManager.ID == 0) {
            HardcoreReloaded.eventManager.activateTask();
        }
        if(HardcoreReloaded.graveyard.ID == 0) {
            HardcoreReloaded.graveyard.activateTask();
        }
    }
}
