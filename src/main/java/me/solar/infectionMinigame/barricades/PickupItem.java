package me.solar.infectionMinigame.barricades;

import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.PlayerUtils;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class PickupItem {

    public static final List<PickupItem> items = new ArrayList<>();

    protected final Location location;
    protected final ItemStack itemStack;
    protected final int cost;
    protected boolean isPickedUp = false;
    protected Item item;
    protected ArmorStand armorStand;
    protected UUID uuid;

    protected PickupItem(Location location, ItemStack itemStack, int cost) {
        this.location = location;
        this.itemStack = itemStack;
        this.cost = cost;
    }

    public void spawn() {
        new DropItemRunnable().runTaskTimer(InfectionMinigamePlugin.getInstance(), 0, 20 * 60);
    }

    private void spawnDisplayItem() {
        armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(true);
        armorStand.customName(item.getItemStack().getItemMeta().customName());
        armorStand.setCustomNameVisible(true);
    }

    private void dropItem() {
        item = location.getWorld().dropItem(location, itemStack);
        item.setCanMobPickup(false);
        item.setPickupDelay(0);
        item.setInvulnerable(true);
        item.setGravity(true);
        uuid = item.getUniqueId();
        items.add(this);
    }

    public void pickup(EntityPickupItemEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) {
            return;
        }

        isPickedUp = true;
        destroy();
        onPickup(event);
        event.setCancelled(true);

        Common.actionBar(player, getMessage());
    }

    public abstract void onPickup(EntityPickupItemEvent event);

    public void destroy() {
        destoryItem();
        items.remove(this);
    }

    public void destoryItem() {
        if (item != null) {
            item.remove();
        }

        if (armorStand != null) {
            armorStand.remove();
        }
    }

    public abstract String getMessage();

    public class DropItemRunnable extends BukkitRunnable {

        @Override
        public void run() {
            if (!isPickedUp) {
                destroy();
                dropItem();
                spawnDisplayItem();
            } else {
                this.cancel();
            }
        }
    }

    @Override
    public String toString() {
        return "PickupItem{" +
                "location=" + location +
                ", itemStack=" + itemStack +
                ", cost=" + cost +
                ", isPickedUp=" + isPickedUp +
                ", item=" + item +
                '}';
    }

    public static PickupItem get(UUID uuid) {
        for (PickupItem item : items) {
            if (item.item != null && item.item.getUniqueId().equals(uuid)) {
                return item;
            }
        }

        Common.log("Could not find pickup item with string value: " + uuid);
        return null;
    }
}
