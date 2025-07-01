package me.solar.infectionMinigame.mobs.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class GetNearestPlayerTarget extends NearestAttackableTargetGoal<Player> {

    public GetNearestPlayerTarget(Mob mob) {
        super(mob, Player.class, true);
    }

    @Override
    public boolean canUse() {
        double minDist = 512.0D;
        Player nearestPlayer = this.mob.level().getNearestPlayer(this.mob, minDist);
        if (nearestPlayer != null) {
            Path path = mob.getNavigation().createPath(nearestPlayer, 0);
            if (path == null || !path.canReach()) {
                // If the path to the player is not reachable, we cannot use this target
                return false;
            }
            return super.canUse();
        }
        return false;
    }

    @Override
    protected void findTarget() {
        ServerLevel level = getServerLevel(this.mob);
        this.target = level.getNearestPlayer(this.mob, 512);
    }
}
