package me.solar.infectionMinigame.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import javax.swing.text.html.parser.Entity;

public enum MobList {

    ZOMBIE("Zombie", EntityType.ZOMBIE, 20.0, 5.0, 0.3),
    SLOW_ZOMBIE("Slow Zombie", EntityType.ZOMBIE, 15.0, 3.0, 0.2),
    FAST_ZOMBIE("Fast Zombie", EntityType.ZOMBIE, 25.0, 7.0, 0.5),
    SKELETON("Skeleton", EntityType.SKELETON, 20.0, 5.0, 0.3),
    DEMON_KNIGHT("Demon Knight", EntityType.ZOMBIE, 40.0, 10.0, 0.4)
    ;


    private String name;
    private EntityType type;
    private String modelName;

    private CustomMob customMob;

    MobList(String name, EntityType type, double health, double damage, double speed) {
        this.name = name;
        this.type = type;
        this.customMob = new CustomMob(name, type);
        this.customMob.setHealth(health);
        this.customMob.setDamage(damage);
        this.customMob.setSpeed(speed);
    }

    public void spawn(Location loc) {
        customMob.spawn(loc);
    }
}
