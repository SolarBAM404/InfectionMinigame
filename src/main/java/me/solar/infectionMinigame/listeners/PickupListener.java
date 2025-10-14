package me.solar.infectionMinigame.listeners;

import io.papermc.paper.persistence.PersistentDataContainerView;
import me.solar.apolloLibrary.utils.Common;
import me.solar.infectionMinigame.barricades.PickupItem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupListener implements Listener {

    public static NamespacedKey PICKUP_ITEM_KEY = Common.namespacedKey("pickup_item");

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        player.sendMessage("You picked up an item!");

        Item eventItem = event.getItem();
        ItemStack item = eventItem.getItemStack();

        Common.log("Item: " + item.getType().name());

        eventItem.remove();
        PickupItem pickupItem = PickupItem.get(eventItem.getUniqueId());
        if (pickupItem != null) {
            pickupItem.pickup(event);
            event.setCancelled(true);
        }
    }

}
