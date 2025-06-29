package me.solar.infectionMinigame.barricades;

import lombok.Getter;
import me.solar.apollo.apolloCore.utils.ItemStackUtils;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;

@Getter
public class Barricade {
    private int health;
    private final int cost;
    private Location location;

    public Barricade(int health, int cost) {
        this.health = health;
        this.cost = cost;
    }

    public void damage(int amount) {
        if (health > 0) {
            health -= amount;
            if (health < 0) {
                health = 0;
            }
        }
    }

    public void spawn(Location location) {
        this.location = location;

        ItemStack item = new ItemStack(Material.COPPER_BULB);
        ItemStackUtils.setItemModel(item, "infected:barricade_level5");

        location.setPitch(0);
        location.setYaw(0);

        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class, display -> {
            display.setCustomNameVisible(false);
            display.setGravity(false);
            display.setItemStack(item);
        });


    }
}
