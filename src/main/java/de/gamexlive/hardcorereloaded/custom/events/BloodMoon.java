package de.gamexlive.hardcorereloaded.custom.events;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;


public class BloodMoon {


    // Add mobs, which shall be effected by Blood Moon Event
    public BloodMoon() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Blood Moon Event");
                Player p = Bukkit.getWorld("world").getPlayers().get(0);
                spawnMobs(p);
                Bukkit.getWorld("world").setTime(13000);
                Bukkit.getWorld("world").getLivingEntities().forEach(key -> {
                    if(key instanceof Monster) {
                        key.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(key.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2);
                        key.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(key.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() * 2);
                    }
                });
            }
        }.runTaskLater(HardcoreReloaded.instance, 20);
    }

    public void spawnMobs(Player player) {
            Location loc = player.getLocation();
            int radius = 8;
            for(double i=0; i < 2*Math.PI; i += Math.PI/4) {
                double x = radius * Math.sin(i);
                double z = radius * Math.cos(i);

                //System.out.println("lX: " + l.getX());
                Zombie zombie = (Zombie) Bukkit.getServer().getWorld("world").spawnEntity(new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ()+z), EntityType.ZOMBIE);
                zombie.setAI(false);
            }
    }

    private Location correctYLevel(Location loc) {
        Block block0 = Bukkit.getWorld("world").getBlockAt(loc);
        if(block0.isEmpty()) {
            return loc;
        }
        while(!block0.isEmpty()) {
            loc.add(0,1,0);
            block0 = Bukkit.getWorld("world").getBlockAt(loc);
        }
        return loc;
    }
}
