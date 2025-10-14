package me.solar.infectionMinigame.barricades.items;

import lombok.Getter;
import me.solar.infectionMinigame.barricades.PickupItem;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public enum DroppableItems {

    SPEED_POWER_UP(SpeedPowerUp.class),
    ;

    @Getter
    private final Class<? extends PickupItem> itemClass;

    DroppableItems(Class<? extends PickupItem> itemClass) {
        this.itemClass = itemClass;
    }

    public <T extends PickupItem> T createDrop(Location location, int cost) {
        try {
            return (T) itemClass.getConstructor(Location.class, int.class).newInstance(location, cost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
