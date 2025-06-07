package me.solar.infectionMinigame.mobs;

import kr.toxicity.model.api.data.renderer.ModelRenderer;
import kr.toxicity.model.api.tracker.DummyTracker;
import kr.toxicity.model.api.tracker.EntityTracker;
import lombok.Data;
import lombok.Getter;
import me.solar.apollo.apolloCore.utils.CommonKt;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Data
public class CustomMob {

    private static Map<String, CustomMob> mobs = new HashMap<>();

    private String name;

    private EntityType entityType;

    private LivingEntity entity;

    private double health = 20.0; // Default health
    private double damage = 5.0; // Default damage
    private double speed = 0.3; // Default speed

    public CustomMob(String name, EntityType entityType) {
        this.name = name;
        this.entityType = entityType;
    }

    public void spawn(Location location) {
        if (!entityType.isAlive()){
            CommonKt.tellConsole("<red>Entity type " + entityType + " is not a living entity!");
            return;
        }
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        AttributeInstance attribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (attribute == null) {
            CommonKt.tellConsole("<red>Entity " + entityType + " does not have a MAX_HEALTH attribute!");
            return;
        }
        attribute.setBaseValue(this.health);
        entity.setHealth(this.health);


        AttributeInstance movementSpeedAttr = entity.getAttribute(Attribute.MOVEMENT_SPEED);
        if (movementSpeedAttr == null) {
            CommonKt.tellConsole("<red>Entity " + entityType + " does not have a MOVEMENT_SPEED attribute!");
            return;
        }
        movementSpeedAttr.setBaseValue(this.speed);

        AttributeInstance attackDamageAttr = entity.getAttribute(Attribute.ATTACK_DAMAGE);
        if (attackDamageAttr == null) {
            CommonKt.tellConsole("<red>Entity " + entityType + " does not have an ATTACK_DAMAGE attribute!");
            return;
        }
        attackDamageAttr.setBaseValue(this.damage);

        ModelRenderer modelRenderer = InfectionMinigamePlugin.getModelManager().renderer(name.toLowerCase().replace(" ", "_"));
        if (modelRenderer != null) {
            EntityTracker tracker = modelRenderer.create(entity);
            for (Player player : location.getWorld().getPlayers()) {
                tracker.spawn(player);
            }
        } else {
            CommonKt.tellConsole("<red>Model for " + name + " not found!");
        }
    }
}
