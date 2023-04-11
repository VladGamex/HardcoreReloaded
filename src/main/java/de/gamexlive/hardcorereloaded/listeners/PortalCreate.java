package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreate implements Listener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        HardcoreReloaded.portalManager.addNewPortal(0, event.getBlocks());
       // Block b = Bukkit.getWorld("world").getBlockState()
    }
}
