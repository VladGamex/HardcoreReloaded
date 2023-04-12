package de.gamexlive.hardcorereloaded.custom.events.netherAttack;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPiglinBrute;
import org.bukkit.entity.PiglinBrute;

import java.util.EnumSet;

public class PathfinderGoalFollowLeader extends Goal {

    private final Mob a; // wolf member
    private Mob b; // leader

    private final double f; // following speed
    private final float stopDistance;

    private double c; // x
    private double d; // y
    private double e; // z


    public PathfinderGoalFollowLeader(Mob a, double speed, float stopDistance) {
        this.a = a;
        this.f = speed;
        this.stopDistance = stopDistance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if(this.b == null) {
            Location loc = new Location(Bukkit.getWorld("world"), this.a.getX(), this.a.getY(), this.a.getZ());
            for (org.bukkit.entity.Entity ent : loc.getWorld().getNearbyEntities(loc, 15, 15, 15)) {
                if (ent instanceof PiglinBrute) {
                    this.b = ((CraftPiglinBrute) ent).getHandle();
                }
            }
            return false;
        } else {
            this.c = this.b.getX(); // x
            this.d = this.b.getY(); // y
            this.e = this.b.getZ(); // z
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        //System.out.println(!this.a.getNavigation().isDone());
        return this.b != null && !this.a.getNavigation().isDone() && this.a.distanceToSqr(this.b) > (double)(this.stopDistance * this.stopDistance);
    }

    @Override
    public void tick() {
        this.a.getNavigation().moveTo(this.c,this.d,this.e,this.f);
    }

    @Override
    public void stop() {
        this.b = null;
        this.a.getNavigation().stop();
    }
}
