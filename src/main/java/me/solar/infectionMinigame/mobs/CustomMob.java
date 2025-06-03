package me.solar.infectionMinigame.mobs;

import lombok.Getter;
import me.solar.apollo.apolloCore.utils.CommonKt;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomMob {

    private static Map<String, CustomMob> mobs = new HashMap<>();

    @Getter
    private String name;
    @Getter
    private EntityType type;

    public CustomMob(String name, EntityType type) {
        this.name = name;
        this.type = type;
    }


    public void spawn(Location loc) {
        LivingEntity entity = loc.getWorld().spawn(loc, LivingEntity.class);

        entity.customName(CommonKt.component(name));


    }
}
