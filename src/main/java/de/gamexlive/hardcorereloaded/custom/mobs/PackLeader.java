package de.gamexlive.hardcorereloaded.custom.mobs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;


public class PackLeader extends PiglinBrute {
    public PackLeader(Location loc) {
        super(EntityType.PIGLIN_BRUTE, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setHealth(100);
        this.setCustomNameVisible(true);
        this.setCustomName(Component.literal("Leader"));
        this.portalCooldown = 20;
        this.setImmuneToZombification(true);


        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1, 3));

    }
}
