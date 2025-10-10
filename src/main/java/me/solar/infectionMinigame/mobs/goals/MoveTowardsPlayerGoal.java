package me.solar.infectionMinigame.mobs.goals;

import me.solar.apolloLibrary.utils.Common;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.Mob;

public class MoveTowardsPlayerGoal extends Goal {

    private final Mob mob;

    public MoveTowardsPlayerGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        double maxDist = 512.0D;
        Player nearestPlayer = this.mob.level().getNearestPlayer(this.mob, maxDist);
        Common.log( "Nearest player: " + nearestPlayer);
        Common.log("Distance: " + this.mob.distanceToSqr(nearestPlayer));
        if (nearestPlayer != null) {
            Path path = mob.getNavigation().createPath(nearestPlayer, 0);
            Common.log("Path: " + path);
            Common.log("Can reach: " + path.canReach());
//            if (path == null || !path.canReach()) {
//                // If the path to the player is not reachable, we cannot use this target
//                return false;
//            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        Player nearestPlayer = this.mob.level().getNearestPlayer(this.mob, 512.0D);
        if (nearestPlayer != null) {
            this.mob.getNavigation().moveTo(nearestPlayer, 1.0D);
        }
    }
}
