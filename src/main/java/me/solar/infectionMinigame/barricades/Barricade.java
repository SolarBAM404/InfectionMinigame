package me.solar.infectionMinigame.barricades;

import lombok.Getter;
import me.solar.apollo.apolloCore.utils.ItemStackUtils;
import me.solar.apollo.apolloCore.utils.MathsUtils;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.events.BarricadeDestoryedEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

@Getter
public class Barricade {
    private double health;
    private final int cost;
    private Location location;
    private Location interactionLocation;

    private ItemDisplay itemDisplay;
    private Interaction interaction;

    public Barricade(int health, int cost) {
        this.health = health;
        this.cost = cost;
    }

    public void damage(double amount) {
        if (health > 0) {
            health -= amount;
        }
        changeDisplayModel();
    }

    public void repair(double amount) {
        if (health < 5) {
            health += amount;
        }
        changeDisplayModel();
    }

    private void changeDisplayModel() {
        switch ((int) MathsUtils.round(health)) {
            case 5 -> changeDisplayModel("infected:barricade_level5");
            case 4 -> changeDisplayModel("infected:barricade_level4");
            case 3 -> changeDisplayModel("infected:barricade_level3");
            case 2 -> changeDisplayModel("infected:barricade_level2");
            case 1 -> changeDisplayModel("infected:barricade_level1");
            default -> {
                if (itemDisplay == null) {
                    return; // No display to remove
                }
                itemDisplay.remove();
                itemDisplay = null;
                location.getBlock().setType(Material.AIR);
                new BarricadeDestoryedEvent(this).callEvent();
            }
        }
    }

    private void changeDisplayModel(String model) {
        checkIfBarrierExists();
        if (itemDisplay == null) {
            if (location == null) {
                return; // Cannot change model without a location
            }
            spawnItemDisplay(location);
        }

        ItemStack item = itemDisplay.getItemStack();
        ItemStackUtils.setItemModel(item, model);
        itemDisplay.setItemStack(item);
    }

    private void checkIfBarrierExists() {
        if (location.getBlock().getType() != Material.BARRIER) {
            setBarrierBlock(location);
        }
    }

    private void spawnItemDisplay(Location location) {
        ItemStack item = new ItemStack(Material.COPPER_BULB);
        ItemStackUtils.setItemModel(item, "infected:barricade_level5");

        itemDisplay = location.getWorld().spawn(location.clone().add(0, .65, 0), ItemDisplay.class, display -> {
            display.setCustomNameVisible(false);
            display.setGravity(false);
            display.setDisplayHeight(2f);
            display.setTransformation(new Transformation(
                    new Vector3f(0, 0, 0),
                    new AxisAngle4f(0, 0, 0, 0),
                    new Vector3f(1f, 2f, 1f),
                    new AxisAngle4f(0, 0, 0, 0)
            ));
            display.setItemStack(item);
            display.setInvisible(false);
        });
    }

    private void spawnInteraction(Location location) {
        Location interactionLocation = location.clone();
        interactionLocation.add(location, 0, -.5, 0);
        interaction = location.getWorld().spawn(interactionLocation, Interaction.class, interactor -> {
            interactor.setCustomNameVisible(false);
            interactor.setInteractionWidth(1.1f);
            interactor.setInteractionHeight(2.1f);
            interactor.setGravity(false);
            interactor.setPersistent(true);
            interactor.setMetadata("barricade", new FixedMetadataValue(InfectionMinigamePlugin.getInstance(), this));
        });
    }

    private void setBarrierBlock(Location location) {
        location.getBlock().setType(Material.BARRIER);
    }

    public void spawn(Location location) {
        location.setPitch(0);
        location.setYaw(0);
        this.location = location;

        spawnInteraction(location);
        spawnItemDisplay(location);
        setBarrierBlock(location);
    }
}
