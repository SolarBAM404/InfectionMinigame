package me.solar.infectionMinigame.data;

import lombok.Getter;
import lombok.Setter;
import me.solar.apolloLibrary.config.ConfigFormat;
import me.solar.apolloLibrary.config.ConfigKey;
import me.solar.apolloLibrary.config.FormatType;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.ConfigManager;
import me.solar.infectionMinigame.barricades.LockedDoor;
import me.solar.infectionMinigame.barricades.RepairableBarricade;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ConfigFormat(FormatType.YAML)
public class GameMap {

    @Getter
    private static final Map<UUID, GameMap> editorCache = new HashMap<>();

    @ConfigKey("region")
    private CuboidRegion region;
    @ConfigKey(value = "name", comment = "The name of the map")
    private String name = "example_name";
    @ConfigKey("min-players")
    private int minPlayers = 1;
    @ConfigKey("max-players")
    private int maxPlayers = 1;

    @ConfigKey("repairable-barricades-locations")
    private List<Location> repairableBarricadesLocations;
    private List<RepairableBarricade> repairableBarricades;

    @ConfigKey("spawn-locations")
    private List<Location> spawnLocations;

    @ConfigKey("locked-door-locations")
    private List<Location> lockedDoorLocations;
    private List<LockedDoor> lockedDoors;

    public void save() {
        for (GameMap map : StaticMapConfig.MAPS) {
            if (map.getName() != null && map.getName().equalsIgnoreCase(name)) {
                StaticMapConfig.MAPS.remove(map);
                break;
            }
        }
        StaticMapConfig.MAPS.add(this);
        ConfigManager.ARENAS.save();
    }

    @Override
    public String toString() {
        return "GameMap{" +
                "region=" + region +
                ", name='" + name + '\'' +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                ", repairableBarricadesLocations=" + repairableBarricadesLocations +
                ", repairableBarricades=" + repairableBarricades +
                ", spawnLocations=" + spawnLocations +
                ", lockedDoorLocations=" + lockedDoorLocations +
                ", lockedDoors=" + lockedDoors +
                '}';
    }
}
