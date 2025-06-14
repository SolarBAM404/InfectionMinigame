package me.solar.infectionMinigame.mobs.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class GetNearestPlayerTarget extends NearestAttackableTargetGoal<Player> {

    public GetNearestPlayerTarget(Mob mob) {
        super(mob, Player.class, true);
    }

    @Override
    protected void findTarget() {
        ServerLevel level = getServerLevel(this.mob);
        this.target = level.getNearestPlayer(this.mob, 512);
    }
}
