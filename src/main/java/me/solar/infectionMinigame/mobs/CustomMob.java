package me.solar.infectionMinigame.mobs;

import lombok.Getter;
import me.solar.infectionMinigame.events.CustomMobSpawnEvent;
import me.solar.infectionMinigame.mobs.goals.AttackPlayerGoal;
import me.solar.infectionMinigame.mobs.goals.GetNearestBarricadeTarget;
import me.solar.infectionMinigame.mobs.goals.GetNearestPlayerTarget;
import me.solar.infectionMinigame.mobs.goals.MoveTowardsPlayerGoal;
import me.solar.infectionMinigame.mobs.pathfinding.InfectedNavigation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomMob extends Zombie {

    @Getter
    private static Map<String, CustomMob> mobs = new HashMap<>();

    @Getter
    protected double attackDamage;

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
        setupMob();

        new CustomMobSpawnEvent(this).callEvent();
    }

    protected void setupMob() {

    }

    protected abstract void setupNonModelMob();

    protected void setEquipment(EquipmentSlot slot, ItemStack item) {
        LivingEntity bukkitEntity = getBukkitLivingEntity();
        bukkitEntity.getEquipment().setItem(slot, item);
    }

    @Override
    public void registerGoals() {
        super.goalSelector.addGoal(1, new AttackPlayerGoal(this));
        super.goalSelector.addGoal(2, new MoveTowardsPlayerGoal(this));
        super.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 128.0F));
        super.goalSelector.addGoal(4, new GetNearestBarricadeTarget(this));
        this.addBehaviourGoals();
    }

    @Override
    public void addBehaviourGoals() {
        super.targetSelector.addGoal(1, new GetNearestPlayerTarget(this));
        super.goalSelector.addGoal(2, new ZombieAttackGoal(this, (double)1.0F, true));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new InfectedNavigation(this, level);
    }

    public void playAnimation(String animationName) {
        playAnimation(animationName, false, false);
    }

    public void playAnimation(String animationName, boolean loop) {
        playAnimation(animationName, false, loop);
    }

    public void playAnimation(String animationName, boolean blendAnimation, boolean loop) {

    }

}
