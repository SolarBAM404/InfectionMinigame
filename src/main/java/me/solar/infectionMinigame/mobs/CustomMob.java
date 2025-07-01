package me.solar.infectionMinigame.mobs;

import lombok.Getter;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.events.CustomMobSpawnEvent;
import me.solar.infectionMinigame.mobs.goals.GetNearestBarricadeTarget;
import me.solar.infectionMinigame.mobs.goals.GetNearestPlayerTarget;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomMob extends Zombie {

    @Getter
    private static Map<String, CustomMob> mobs = new HashMap<>();

    protected CustomMob(Level level) {
        super(level);
    }

    public void spawn(Location location) {
        setPos(location.getX(), location.getY(), location.getZ());
        this.setYRot(location.getYaw());
        this.setXRot(location.getPitch());
        this.setNoGravity(false);
        this.setCustomNameVisible(false);
        this.setCustomName(getName());

        mobs.put(String.valueOf(getName()), this);
        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        craftWorld.getHandle().addFreshEntity(this, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CUSTOM);

        new CustomMobSpawnEvent(this).callEvent();
    }

    @Override
    public void registerGoals() {
        super.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 128.0F));
        super.goalSelector.addGoal(3, new GetNearestBarricadeTarget(this));
        this.addBehaviourGoals();
        super.registerGoals();
    }

    @Override
    public void addBehaviourGoals() {
        super.goalSelector.addGoal(2, new ZombieAttackGoal(this, (double)1.0F, true));
        super.targetSelector.addGoal(1, new GetNearestPlayerTarget(this));
    }

}
