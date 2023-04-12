package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreate implements Listener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        System.out.println("TEST");
        HardcoreReloaded.portalManager.addNewPortal(0, event.getBlocks().get(0).getLocation());
       // Block b = Bukkit.getWorld("world").getBlockState()
    }
}
