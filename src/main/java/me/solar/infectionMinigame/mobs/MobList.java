package me.solar.infectionMinigame.mobs;

import lombok.Getter;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.mobs.types.DemonKnight;
import me.solar.infectionMinigame.mobs.types.Speedy;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.EntityType;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public enum MobList {

    DEMON_KNIGHT("Demon Knight", DemonKnight.class, 40.0f, 10.0f, 0.4f),
    SPEEDY("Speedy", Speedy.class, 20.0f, 5.0f, 0.3f)
    ;


    @Getter
    private String name;
    private float health;
    private double damage;
    private float speed;
    private Class<? extends CustomMob> mobClazz;

//    private static List<CustomMob> customMob;

    MobList(String name, Class<? extends CustomMob> mobClazz, float health, double damage, float speed) {
        this.name = name;
        this.mobClazz = mobClazz;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
    }

    public void spawn(Location loc) {
        Level level = ((CraftWorld) loc.getWorld()).getHandle();

        try {
            Constructor<? extends CustomMob> constructor = mobClazz.getConstructor(Level.class);
            constructor.setAccessible(true);
            CustomMob customMob = constructor.newInstance(level);
            customMob.spawn(loc);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getMobNames() {
        String[] mobNames = new String[MobList.values().length];
        for (int i = 0; i < MobList.values().length; i++) {
            mobNames[i] = MobList.values()[i].getName();
        }
        return mobNames;
    }
}
