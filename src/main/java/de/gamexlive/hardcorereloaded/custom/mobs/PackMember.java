package de.gamexlive.hardcorereloaded.custom.mobs;

import de.gamexlive.hardcorereloaded.custom.events.netherAttack.PathfinderGoalFollowLeader;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;

public class PackMember extends Piglin {
    public PackMember(Location loc) {
        super(EntityType.PIGLIN, ((CraftWorld) loc.getWorld()).getHandle());
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setHealth(40);
        this.portalCooldown = 20;
        // Implement that PathfinderGoalFollowLeader accepts another param with
        this.goalSelector.addGoal(0, new PathfinderGoalFollowLeader(this, 1.0D, 1));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
