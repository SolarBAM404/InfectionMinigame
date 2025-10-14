package me.solar.infectionMinigame.barricades.items;

import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.infectionMinigame.barricades.PickupItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class SpeedPowerUp extends PickupItem {
    public SpeedPowerUp(Location location, int cost) {
        super(location, ItemStackUtils.createItemStack(Material.WIND_CHARGE, "Speed Upgrade"), cost);
    }

    public SpeedPowerUp(Location location, Item item, int cost) {
        super(location, item.getItemStack(), cost);
        this.item = item;
    }

    @Override
    public void onPickup(EntityPickupItemEvent event) {
        Common.log("Speed Power Up picked up!");
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        player.setWalkSpeed(0.35f);

        Common.runTaskLater(() -> {;
            player.setWalkSpeed(0.2f); // Reset to default speed
        }, 20 * 10); // 10-second duration
    }

    @Override
    public String getMessage() {
        return "<aqua>Speed <green>Power Activated";
    }
}
