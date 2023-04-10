package de.gamexlive.hardcorereloaded.custom.mobs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;

import java.util.function.Predicate;

public class Goblin extends Zombie {

    /*
        Tasks:
        Add Goals if possible:
        1. Attacking Player -> 2. Steal X-amount of random items -> 3.Run away
        4. Been hit by Player -> 5. Drop random stolen item -> 6. return to 1.

        --> Check if has got any items, if not -> 1. else 3.
        --> EntityDamagedByEntityEvent
     */

    public Goblin(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle());
        this.setBaby(true);
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setCustomNameVisible(true);
        this.setCustomName(Component.literal("Kleiner Huso"));
        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void setCurrentGoal(int goal) {
        switch (goal) {
            case 1:
                this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
                break;
            case 2:
                this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 15, 1.0D, 1.0D));
                this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
                this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6D));
                this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
                break;
        }
    }

}
