package de.gamexlive.hardcorereloaded.custom.events.netherAttack;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import de.gamexlive.hardcorereloaded.custom.mobs.PackLeader;
import de.gamexlive.hardcorereloaded.custom.mobs.PackMember;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class NetherAttack {

    public NetherAttack() {
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.broadcastMessage("The Nether invades the overworld");
                ArrayList<Location> location = HardcoreReloaded.portalManager.getAllLocations();
                location.forEach(key -> spawnMobs(key));
            }
        }.runTaskLater(HardcoreReloaded.instance, 20);
    }


    // Only spawn mobs in already loaded chunks
    public void spawnMobs(Location loc) {
        PackLeader leader = new PackLeader(loc);
        ((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(leader);
        PackMember test = new PackMember(loc);
        for(int i = 0; i < 5; i++) {
            test.setImmuneToZombification(true);
            ((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(test);
            test = new PackMember(loc);

        }
    }

}
