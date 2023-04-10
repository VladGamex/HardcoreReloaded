package de.gamexlive.hardcorereloaded.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(event.getView().getTitle().equals("Bounties")) {
            event.setCancelled(true);
        }
    }
}
