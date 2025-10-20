package me.solar.infectionMinigame.barricades;

import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.apolloLibrary.world.Region;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class LockedDoor extends Barricade {

    private final Region region;
    private List<Interaction> interactions;
    private ItemDisplay itemDisplay;
    private TextDisplay textDisplay;

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
        placeBarrierBlocks();
        spawnInteraction();
        spawnItemDisplay();
        spawnTextDisplay();
    }

    @Override
    public void destroy() {
        destroyBarrierBlocks();

        interactions.forEach(Interaction::remove);

        if (itemDisplay != null) {
            itemDisplay.remove();
        }

        if (textDisplay != null) {
            textDisplay.remove();
        }
    }

    private void placeBarrierBlocks() {
        for (Location location : region.getAllLocations()) {
            if (location.getBlock().getType() != Material.BARRIER && location.getBlock().getType() == Material.AIR) {
                location.getBlock().setType(org.bukkit.Material.BARRIER);
            }
        }
    }

    private void destroyBarrierBlocks() {
        for (Location location : region.getAllLocations()) {
            if (location.getBlock().getType() == Material.BARRIER) {
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    private void spawnItemDisplay() {
        Location loc = region.getCenter().getBlock().getLocation().add(0.5, 0.5, 0.5);
        itemDisplay = loc.getWorld().spawn(loc, ItemDisplay.class);
        itemDisplay.setItemStack(ItemStackUtils.createItemStack(Material.BARRIER, "Locked Door", "This door is locked"));
        itemDisplay.setCustomNameVisible(true);

        float width = (float) (region.getWidth() + 0.2f);
        float height = (float) (region.getHeight() + 0.2f);

        itemDisplay.setTransformation(new Transformation(
                new Vector3f(0f, 0f, 0f),
                new Quaternionf(0, 0, 0, 1),
                new Vector3f(width, height, width),
                new Quaternionf(0, 0, 0, 1)
        ));
    }

    private void spawnInteraction() {
        interactions = new ArrayList<>();
        for (Location loc : region.getAllLocations()) {
            Location blockLoc = loc.getBlock().getLocation();
            Location center = new Location(blockLoc.getWorld(),
                    blockLoc.getX() + 0.5,
                    blockLoc.getY(),
                    blockLoc.getZ() + 0.5);

            Interaction interaction = center.getWorld().spawn(center, Interaction.class);
            interaction.setMetadata("locked-door", new FixedMetadataValue(InfectionMinigamePlugin.getInstance(), this));
            interaction.setGravity(false);
            interaction.setPersistent(true);
            interaction.setCustomNameVisible(false);

            // keep it just inside the block
            interaction.setInteractionWidth(1.1f);
            interaction.setInteractionHeight(1.1f);

            interactions.add(interaction);
        }
    }

    private void spawnTextDisplay() {
        Location loc = region.getCenter().getBlock().getLocation().add(0.5, 1.5, 0.75);
        textDisplay = loc.getWorld().spawn(loc, TextDisplay.class);
        textDisplay.text(Common.component("Locked Door" + "\n" + "This door is locked, cost to open: " + cost + "."));
    }

}
