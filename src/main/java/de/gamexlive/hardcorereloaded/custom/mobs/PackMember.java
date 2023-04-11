package de.gamexlive.hardcorereloaded.custom.mobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;

public class PackMember extends Piglin {
    public PackMember(Location loc, Mob leader) {
        super(EntityType.PIGLIN, ((CraftWorld) loc.getWorld()).getHandle());
        this.setHealth(40);
        this.goalSelector.addGoal(0, new FollowMobGoal(leader, 2, 2, 2));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }
}
