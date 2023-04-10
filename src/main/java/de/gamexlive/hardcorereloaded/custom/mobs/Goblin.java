package de.gamexlive.hardcorereloaded.custom.mobs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.inventory.Inventory;

public class Goblin extends Zombie {

    /*
        Tasks:
        Add Goals if possible:
        1. Attacking Player -> 2. Steal X-amount of random items -> 3.Run away
        4. Been hit by Player -> 5. Drop random stolen item -> 6. return to 1.

        --> Check if has got any items, if not -> 1. else 3.
        --> EntityDamagedByEntityEvent
     */

   private Inventory inv;


    public Goblin(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle());
        this.setBaby(true);
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setHealth(40);
        this.setCustomNameVisible(true);
        this.setCustomName(Component.literal("Kleiner Huso"));
        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        inv = Bukkit.createInventory(null, 9*4, "t");
    }

    public Inventory getInventory() {return inv;}

}
