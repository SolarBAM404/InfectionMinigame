package me.solar.infectionMinigame.mobs.goals;

import me.solar.apolloLibrary.collection.expiringmap.ExpiringMap;
import me.solar.apolloLibrary.utils.Common;
import me.solar.infectionMinigame.mobs.CustomMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class AttackPlayerGoal extends Goal {

    private final CustomMob mob;
    private final static ExpiringMap<CustomMob, Long> lastAttackMap = ExpiringMap.builder()
            .expiration(200, TimeUnit.MILLISECONDS)
            .variableExpiration()
            .build();

    public AttackPlayerGoal(CustomMob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        Collection<Player> nearbyPlayers = this.mob.getBukkitEntity().getLocation().getNearbyPlayers(1);
        return !nearbyPlayers.isEmpty() && !lastAttackMap.containsKey(mob);
    }

    @Override
    public void tick() {
        if (lastAttackMap.containsKey(mob) && lastAttackMap.getExpectedExpiration(mob) > 0) {
            mob.setAggressive(false);
        }

        mob.setAggressive(true);
        Collection<Player> nearbyPlayers = this.mob.getBukkitEntity().getLocation().getNearbyPlayers(1.25);
        Player player = nearbyPlayers.toArray(new Player[0])[0];
        mob.playAnimation("attack", true, false);
        player.damage(mob.getAttackDamage());

        lastAttackMap.put(mob, System.currentTimeMillis());
    }
}
