package me.solar.infectionMinigame.barricades;

import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.apolloLibrary.world.Region;
import org.bukkit.Location;

public class LockedDoor extends Barricade {

    private final Region region;

    private int cost;
    private int tier;

    public LockedDoor(Region region) {
        this.region = region;
    }

    public void open() {

    }

    public void generateCost() {

    }

    @Override
    public void spawn() {
        
    }

    @Override
    public void destroy() {

    }


}
