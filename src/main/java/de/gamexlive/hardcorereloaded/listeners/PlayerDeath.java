package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerDeath implements Listener
{
    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        final Player p = ((Player)event.getEntity()).getPlayer();
        if (p.getHealth() - event.getDamage() <= 0.0) {
            event.setCancelled(true);
            p.setHealth(20.0);
            final int x = HardcoreReloaded.instance.getConfig().getInt("graveyard.X");
            final int y = HardcoreReloaded.instance.getConfig().getInt("graveyard.Y");
            final int z = HardcoreReloaded.instance.getConfig().getInt("graveyard.Z");
            final Date date = new Date(System.currentTimeMillis());
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(date));
            p.teleport(new Location(p.getWorld(), (double)x, (double)y, (double)z));
        }
    }
}
