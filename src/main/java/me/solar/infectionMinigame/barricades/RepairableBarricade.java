package me.solar.infectionMinigame.barricades;

import lombok.Getter;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.apolloLibrary.utils.MathsUtils;
import me.solar.apolloLibrary.world.Region;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.events.BarricadeDestoryedEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RepairableBarricade extends Barricade {

    private final ItemStack repairItem;
    private int health = 5;
    private ItemDisplay itemDisplay;
    private List<Interaction> interactions;
    private List<Location> locations;
    private Region region;
    private double yaw;

    public RepairableBarricade(Region region) {
        this.region = region;
        this.repairItem =  setRepairItem();
        this.interactions = new ArrayList<>();
    }

    @Override
    public void spawn() {
        if (locations == null) {
            locations = region.getAllLocations();
        }

        updateYawToLongestSide();

        Location loc = locations.get(0);
        spawnItemDisplay(loc);

        for (Location location : locations) {
            if (location.getBlock().getType() != Material.BARRIER) {
                setBarrierBlock(location);
            }
            spawnInteraction(location);
        }
    }

    @Override
    public void destroy() {
        destroyDoor();
        for (Interaction interaction : interactions) {
            interaction.remove();
        }
    }

    public void damage(int amount) {
        health -= amount;
        changeDisplayModel();
    }

    public void repair(int amount) {
        health += amount;
        changeDisplayModel();
    }

    /**
     * Changes the display model of the barricade based on the health of the barricade
     */
    private void changeDisplayModel() {
        switch ((int) MathsUtils.round(health)) {
            case 5 -> changeDisplayModel("barricade_level5");
            case 4 -> changeDisplayModel("barricade_level4");
            case 3 -> changeDisplayModel("barricade_level3");
            case 2 -> changeDisplayModel("barricade_level2");
            case 1 -> changeDisplayModel("barricade_level1");
            default -> {
                if (itemDisplay == null) {
                    return; // No display to remove
                }

                destroyDoor();

                new BarricadeDestoryedEvent(this).callEvent();
            }
        }
    }

    private void destroyDoor() {
        for (Location location : locations) {
            location.getBlock().setType(Material.AIR);
        }

        if (itemDisplay != null) {
            itemDisplay.remove();
            itemDisplay = null;
        }
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
        changeDisplayModel();
    }

    /**
     * Changes the display model of the barricade to the specified model
     * @param model The model to change to
     */
    private void changeDisplayModel(String model) {
        Common.log("health: " + health + " model: " + model);
        if (itemDisplay == null) {
            spawnItemDisplay(locations.getFirst());

            for (Location location : locations) {
                if (location.getBlock().getType() != Material.BARRIER) {
                    setBarrierBlock(location);
                }
            }

        }

        NamespacedKey key = Common.namespacedKey("infected", model);

        ItemStack itemStack = itemDisplay.getItemStack();
        ItemStackUtils.setItemModel(itemStack, key);
        itemDisplay.setItemStack(itemStack);
    }

    /**
     * Checks if the barrier block exists, if not it will be created
     */
    private void checkIfBarrierExists() {
        for (Location location : locations) {
            if (location.getBlock().getType() != Material.BARRIER) {
                setBarrierBlock(location);
            }
        }
    }

    private ItemStack setRepairItem() {
        ItemStack item = ItemStackUtils.createItemStack(Material.OAK_PLANKS, "Repair Kit", "Repair the barricade with this item");
        ItemStackUtils.setItemModel(item, Common.namespacedKey("infected", "repair_kit" ));
        return item;
    }

    private void spawnItemDisplay(Location location) {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        NamespacedKey key = Common.namespacedKey("infected", "barricade_level5");
        ItemStackUtils.setItemModel(item, key);

        float yawRadians = location.getYaw();
        float mapped = (yawRadians + 180) % 360;
        if (mapped < 0) mapped += 360;

        float finalMapped = mapped;
        itemDisplay = location.getWorld().spawn(region.getCenter().add(0.5, .65, 0.5), ItemDisplay.class, display -> {
            display.setCustomNameVisible(false);
            display.setGravity(false);
            display.setDisplayHeight(2f);
            display.setTransformation(new Transformation(
                    new Vector3f(0, 0, 0),
                    new AxisAngle4f(0, 0, 1, 0),
                    new Vector3f((float) region.getWidth()+1, (float) region.getHeight()+1, 1),
                    new AxisAngle4f(0, 0, 0, 0)
            ));
            display.setRotation(finalMapped, 0);
            display.setItemStack(item);
            display.setInvisible(false);
        });
    }

    private void spawnInteraction(Location location) {
        Location interactionLocation = location.clone();
        interactionLocation.add(0.5, .05, 0.5);
        interactions.add(location.getWorld().spawn(interactionLocation, Interaction.class, interactor -> {
            interactor.setCustomNameVisible(false);
            interactor.setInteractionWidth(1.1f);
            interactor.setInteractionHeight(1.1f);
            interactor.setGravity(false);
            interactor.setPersistent(true);
            interactor.setMetadata("barricade", new FixedMetadataValue(InfectionMinigamePlugin.getInstance(), this));
        }));
    }

    private void updateYawToLongestSide() {
        double width = region.getWidth();
        double length = region.getLength();
        // If width is longer, face east-west (yaw = 90), else north-south (yaw = 0)
        this.yaw = width >= length ? 90.0 : 0.0;
    }

    private void setBarrierBlock(Location location) {
        location.getBlock().setType(Material.BARRIER);
    }

}
