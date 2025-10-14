package me.solar.infectionMinigame.mobs.goals;

import me.solar.infectionMinigame.barricades.Barricade;
import me.solar.infectionMinigame.barricades.RepairableBarricade;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.EnumSet;

public class GetNearestBarricadeTarget extends Goal {
    private final Mob mob;
    private Interaction targetBarricade;
    private Location targetLocation;
    private int attackCooldown = 0;

    public GetNearestBarricadeTarget(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        double minDist = Double.MAX_VALUE;
        Interaction nearest = null;
        @NotNull Collection<Interaction> entities = mob.getBukkitEntity().getWorld().getEntitiesByClass(Interaction.class);
        for (Interaction entity : entities) {
            if (entity.hasMetadata("barricade")) {
                double dist = entity.getLocation().distance(mob.getBukkitEntity().getLocation());
                if (dist < minDist) {
                    minDist = dist;
                    nearest = entity;
                }

                Barricade barricade = (Barricade) entity.getMetadata("barricade").getFirst().value();
                if (!(barricade instanceof RepairableBarricade reBarricade)) {
                    return false;
                }

                if (reBarricade.getHealth() <= 0) {
                    return false;
                }

            }
        }
        if (nearest != null) {
            targetBarricade = nearest;
            targetLocation = nearest.getLocation();
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        if (targetLocation != null) {
            mob.getNavigation().moveTo(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ(), 1.0);
        }
    }

    @Override
    public void stop() {
        targetBarricade = null;
        targetLocation = null;
    }

    @Override
    public boolean canContinueToUse() {
        return targetBarricade != null && targetBarricade.isValid();
    }

    @Override
    public void tick() {
        if (targetLocation == null) return;

        double distance = mob.getBukkitEntity().getLocation().distance(targetLocation);

        if (!(mob instanceof Monster monster)) return;

        if (distance > 2.0) {
            monster.getNavigation().moveTo(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ(), 1.0);
        } else {
            if (attackCooldown-- <= 0) {
                if (targetBarricade.hasMetadata("barricade")) {
                    Object barricadeObj = targetBarricade.getMetadata("barricade").get(0).value();
                    if (barricadeObj instanceof RepairableBarricade barricade) {
                        barricade.damage(1);

                    }
                }
                attackCooldown = 20;
            }
        }
    }
}